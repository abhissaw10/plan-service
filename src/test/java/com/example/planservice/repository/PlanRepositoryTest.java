package com.example.planservice.repository;

import com.example.planservice.entity.Plan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.example.planservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@DataMongoTest
@Testcontainers
public class PlanRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

    @DynamicPropertySource
    static void setProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> mongoDBContainer.getReplicaSetUrl());
    }

    @Autowired PlanRepository planRepository;

    @Test
    public void giveProductNameAndProductOwner_shouldFilterResults(){
        Plan response1 = planRepository.save(Plan.builder().productName(TEST_PRODUCT_NAME).productOwner(TEST_PRODUCT_OWNER).build());
        Plan response2 = planRepository.save(Plan.builder().productName(TEST_PRODUCT_NAME_2).productOwner(TEST_PRODUCT_OWNER_2).build());
        List<Plan> plans = planRepository.findByProductNameAndProductOwner(TEST_PRODUCT_NAME,TEST_PRODUCT_OWNER);
        assertThat(plans.size()).isEqualTo(1);
        assertThat(plans.get(0).getProductName()).isEqualTo(TEST_PRODUCT_NAME);
        assertThat(plans.get(0).getProductOwner()).isEqualTo(TEST_PRODUCT_OWNER);
    }

    @Test
    public void giveProductName_shouldFilterResults(){
        //planRepository.deleteAll();
        Plan response1 = planRepository.save(Plan.builder().productName(TEST_PRODUCT_NAME).productOwner(TEST_PRODUCT_OWNER_2).build());
        List<Plan> plans = planRepository.findByProductName(TEST_PRODUCT_NAME_2);
        assertThat(plans.size()).isEqualTo(0);
    }

    @AfterEach
    public void cleanup(){
        planRepository.deleteAll();
    }

}
