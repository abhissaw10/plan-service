package com.example.planservice.it;

import com.example.planservice.config.WireMockInitializer;
import com.example.planservice.repository.GoalRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.example.planservice.config.TestData.goalRequestIntegration;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@ActiveProfiles("integration-test")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = WireMockInitializer.class)
public class GoalIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

    @DynamicPropertySource
    static void setProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl());
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    GoalRepository goalRepository;

    @Autowired
    WireMockServer wireMockServer;


    @Test
    public void shouldCreateGoal(){
        ResponseEntity<String> exchange = createGoal();
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(goalRepository.findById(exchange.getBody()).get().getInitiatives().size()).isEqualTo(1);
    }

    private ResponseEntity<String> createGoal() {
        HttpEntity entity = new HttpEntity(goalRequestIntegration,new HttpHeaders());
        ResponseEntity<String> exchange = testRestTemplate
                .exchange("/v1/goals",
                        HttpMethod.POST, entity, String.class);
        return exchange;
    }

    /*@Test
    public void shouldGetAllGoals() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String goalId = createGoal().getBody();
        System.out.println("Goal Id "+goalId);
        Goal goalDB = goalRepository.findById(goalId).get();
        System.out.println("DB "+goalDB);
        String response = testRestTemplate.getForObject("/v1/goals/"+goalId, String.class);
        System.out.println(response);
    }*/

    @AfterEach
    public void cleanup(){
        goalRepository.deleteAll();
    }
}
