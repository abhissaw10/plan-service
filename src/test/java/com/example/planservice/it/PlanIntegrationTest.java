package com.example.planservice.it;

import com.example.planservice.repository.PlanRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.example.planservice.config.TestData.planRequest;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanIntegrationTest {

    @Container
    static PostgreSQLContainer container = new PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("plan");

    @DynamicPropertySource
    public static void setup(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> container.getJdbcUrl());
        registry.add("spring.datasource.username", () -> container.getUsername());
        registry.add("spring.datasource.password", () -> container.getPassword());
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    PlanRepository planRepository;

    @Test
    public void shouldCreatePlan(){
        HttpEntity entity = new HttpEntity(planRequest(),new HttpHeaders());
        ResponseEntity<String> exchange = testRestTemplate
                .exchange("/v1/plans",
                        HttpMethod.POST, entity, String.class);
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    /*@Test
    public void shouldGetAllPlans(){
        shouldCreatePlan();
        ResponseEntity<List<PlanResponse>> response = testRestTemplate.exchange("/v1/plans", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        List<PlanResponse> plans = response.getBody();
        assertThat(plans.size()).isEqualTo(1);
        assertThat(plans.get(0).getProductName()).isEqualTo(TEST_PRODUCT_NAME);
        assertThat(plans.get(0).getProductOwner()).isEqualTo(TEST_PRODUCT_OWNER);
        assertThat(plans.get(0).getGoals().get(0).getName()).isEqualTo(TEST_GOAL);
        assertThat(plans.get(0).getFinancialYear()).isEqualTo(TEST_FIN_YEAR);
    }*/

    @AfterEach
    public void cleanup(){
        planRepository.deleteAll();
    }

}
