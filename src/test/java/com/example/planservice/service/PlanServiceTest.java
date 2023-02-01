package com.example.planservice.service;

import com.example.planservice.entity.Plan;
import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.planservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    PlanRepository planRepository;

    @InjectMocks
    PlanService planService;

    @Test
    public void givenPlanRequest_shouldCreatePlan(){
        when(planRepository.save(any(Plan.class))).thenReturn(Plan.builder().id(TEST_PLAN).build());
        String planId = planService.create(PlanRequest.builder().build());
        assertThat(planId).isEqualTo(TEST_PLAN);
    }

    @Test
    public void shouldReturnAllPlans(){
        when(planRepository.findAll()).thenReturn(planList());
        List<PlanResponse> planList = planService.getAll(null,null);
        assertThat(planList.size()).isEqualTo(1);
        assertThat(planList.get(0).getGoal()).isEqualTo(TEST_GOAL);
    }

    @Test
    public void givenProductName_shouldReturnAllMatchingPlans(){
        when(planRepository.findByProductName(TEST_PRODUCT_NAME)).thenReturn(planList());
        List<PlanResponse> planList = planService.getAll(TEST_PRODUCT_NAME,null);
        verify(planRepository,times(1)).findByProductName(any());
        assertThat(planList.size()).isEqualTo(1);
        assertThat(planList.get(0).getProductName()).isEqualTo(TEST_PRODUCT_NAME);
    }
    @Test
    public void givenProductOwner_shouldReturnAllMatchingPlans(){
        when(planRepository.findByProductOwner(TEST_PRODUCT_NAME)).thenReturn(planList());
        List<PlanResponse> planList = planService.getAll(null,TEST_PRODUCT_OWNER);
        verify(planRepository,times(1)).findByProductOwner(any());
        assertThat(planList.size()).isEqualTo(1);
        assertThat(planList.get(0).getProductName()).isEqualTo(TEST_PRODUCT_NAME);
    }
    @Test
    public void givenProductNameAndOwner_shouldReturnAllMatchingPlans(){
        when(planRepository.findByProductNameAndProductOwner(TEST_PRODUCT_NAME,TEST_PRODUCT_OWNER)).thenReturn(planList());
        List<PlanResponse> planList = planService.getAll(TEST_PRODUCT_NAME,TEST_PRODUCT_OWNER);
        verify(planRepository,times(1)).findByProductNameAndProductOwner(TEST_PRODUCT_NAME,TEST_PRODUCT_OWNER);
        assertThat(planList.size()).isEqualTo(1);
        assertThat(planList.get(0).getProductName()).isEqualTo(TEST_PRODUCT_NAME);
    }
}
