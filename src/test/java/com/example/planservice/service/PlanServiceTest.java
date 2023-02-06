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
import java.util.Optional;

import static com.example.planservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    PlanRepository planRepository;

    @Mock
    GoalService goalService;


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
        when(goalService.get("1")).thenReturn(dummyGoalResponse1);
        when(goalService.get("2")).thenReturn(dummyGoalResponse2);
        List<PlanResponse> planList = planService.getAll(null,null);
        assertThat(planList.size()).isEqualTo(1);
        assertThat(planList.get(0).getGoals().get(0).getName()).isEqualTo(TEST_GOAL);
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
        when(planRepository.findByProductOwner(TEST_PRODUCT_OWNER)).thenReturn(planList());
        List<PlanResponse> planList = planService.getAll(null,TEST_PRODUCT_OWNER);
        verify(planRepository,times(1)).findByProductOwner(TEST_PRODUCT_OWNER);
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

    @Test
    public void givenPlanId_shouldReturnMatchingPlanWithoutDependencies(){
        when(planRepository.findById(anyString())).thenReturn(Optional.of(plan()));
        PlanResponse response = planService.get(TEST_PLAN);
        assertThat(response).isNotNull();
        assertThat(response.getPlanId()).isEqualTo(TEST_PLAN);
        assertThat(response.getProductName()).isEqualTo(TEST_PRODUCT_NAME);
    }

    @Test
    public void givenPlanId_shouldReturnMatchingPlanWithDependentInitiatives(){
        when(planRepository.findById(anyString())).thenReturn(Optional.of(plan()));
        when(goalService.get("1")).thenReturn(goalResponse);
        PlanResponse response = planService.get(TEST_PLAN);
        assertThat(response.getGoals().get(0).getInitiatives().get(0).getTitle()).isEqualTo(TEST_TITLE_1);
        assertThat(response.getGoals().get(0).getInitiatives().get(1).getTitle()).isEqualTo(TEST_TITLE_2);
    }
}
