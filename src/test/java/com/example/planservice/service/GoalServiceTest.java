package com.example.planservice.service;

import com.example.planservice.config.TestData;
import com.example.planservice.entity.Goal;
import com.example.planservice.model.GoalRequest;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.repository.GoalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.planservice.config.TestData.TEST_GOAL;
import static com.example.planservice.config.TestData.TEST_GOAL_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @Mock
    GoalRepository goalRepository;

    @Mock
    InitiativeClient initiativeClient;

    @InjectMocks
    GoalService goalService;

    @Test
    public void shouldCreateGoal(){
        when(goalRepository.save(Goal.builder().name(TEST_GOAL).build())).thenReturn(Goal.builder().goalId(TEST_GOAL_ID).name(TEST_GOAL).build());
        String id = goalService.create(GoalRequest.builder().name(TEST_GOAL).build());
        assertThat(id).isNotNull();
    }

    @Test
    public void getAllGoals(){
        when(goalRepository.findAll()).thenReturn(List.of(Goal.builder().name(TEST_GOAL).initiatives(List.of(1L,2L)).build()));
        when(initiativeClient.getInitiatives("1","2")).thenReturn(TestData.initiativeResponseMap);
        List<GoalResponse> goalList = goalService.getAll(null);
        assertThat(goalList.size()).isEqualTo(1);
        assertThat(goalList.get(0).getName()).isEqualTo(TEST_GOAL);
    }
}
