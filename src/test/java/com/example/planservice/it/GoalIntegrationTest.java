package com.example.planservice.it;

import com.example.planservice.config.TestData;
import com.example.planservice.config.TestRedisConfiguration;
import com.example.planservice.entity.Goal;
import com.example.planservice.model.GoalRequest;
import com.example.planservice.repository.GoalRepository;
import com.example.planservice.repository.InitiativeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.example.planservice.config.TestData.goalRequestIntegration;
import static com.example.planservice.config.TestData.initiativeResponseMapWiremock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ActiveProfiles("integration-test")
@Import(TestRedisConfiguration.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(initializers = WireMockInitializer.class)
public class GoalIntegrationTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("plan");

    public static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-" + MockServerClient.class.getPackage().getImplementationVersion());
    static MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE);

    /*static GenericContainer redis =
            new GenericContainer(DockerImageName.parse("redis:6-alpine")).withExposedPorts(6379);*/

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",()->container.getJdbcUrl());
        registry.add("spring.datasource.username",()->container.getUsername());
        registry.add("spring.datasource.password",()->container.getPassword());
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    GoalRepository goalRepository;

   /* @Autowired
    WireMockServer wireMockServer;*/

    @Autowired
    InitiativeRepository initiativeRepository;

    @BeforeAll
    public static void setup(){
        mockServer.start();
       // redis.start();
    }

    @Test
    public void givenValidGoalRequest_shouldCreateGoal(){
        ResponseEntity<Long> exchange = createGoal(goalRequestIntegration);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(goalRepository.findById(exchange.getBody()).get().getInitiatives().size()).isEqualTo(1);
    }

    private ResponseEntity<Long> createGoal(GoalRequest requestBody) {
        HttpEntity entity = new HttpEntity(requestBody,new HttpHeaders());
        ResponseEntity<Long> exchange = testRestTemplate
                .exchange("/v1/goals",
                        HttpMethod.POST, entity, Long.class);
        return exchange;
    }

    @Test
    public void shouldGetAllGoalsAndReadInitiativesFromRedisOnFeignException() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(initiativeResponseMapWiremock);
        try (
                MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort())
        ) {
            String[] p = {"1"};
            mockServerClient
                    .when(request().withPath("/v1/initiatives/byIds").withQueryStringParameter("ids", p))
                    .respond(response().withBody(json));
        }

        Long goalId = createGoal(goalRequestIntegration).getBody();
        initiativeRepository.save(TestData.initiativeResponse);
        Goal goalDB = goalRepository.findById(goalId).get();
        String response = testRestTemplate.getForObject("/v1/goals/"+goalId, String.class);
        System.out.println(response);
    }

    @AfterEach
    public void cleanup(){
        goalRepository.deleteAll();
    }
}
