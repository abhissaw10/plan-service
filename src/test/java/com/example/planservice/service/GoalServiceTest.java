package com.example.planservice.service;

import com.example.planservice.config.TestData;
import com.example.planservice.entity.Goal;
import com.example.planservice.entity.InitiativeEntity;
import com.example.planservice.model.GoalRequest;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.repository.GoalRepository;
import com.example.planservice.repository.InitiativeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.planservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

    @Mock
    GoalRepository goalRepository;

    @Mock
    InitiativeClient initiativeClient;

    @Mock
    InitiativeRepository initiativeRepository;

    @InjectMocks
    GoalService goalService;

    @Test
    public void shouldCreateGoal(){
            when(goalRepository.save(Goal
                .builder()
                .name(TEST_GOAL)
                .build()))
                .thenReturn(Goal
                        .builder()
                        .goalId(TEST_GOAL_ID)
                        .name(TEST_GOAL)
                        .build());
        Long id = goalService.create(GoalRequest
                .builder()
                .name(TEST_GOAL)
                .build());
        assertThat(id).isNotNull();
    }

    @Test
    public void getAllGoals(){
        when(goalRepository.findAll()).thenReturn(
                List.of(
                        Goal
                                .builder()
                                .name(TEST_GOAL)
                                .initiatives(
                                        List.of(
                                                InitiativeEntity
                                                        .builder()
                                                        .initiative(1L)
                                                        .build(),
                                                InitiativeEntity
                                                        .builder()
                                                        .initiative(2L)
                                                        .build()))
                                .build()));
        when(initiativeClient.getInitiatives("1","2")).thenReturn(TestData.initiativeResponseMap);
        List<GoalResponse> goalList = goalService.getAll(null);
        assertThat(goalList.size()).isEqualTo(1);
        assertThat(goalList.get(0).getName()).isEqualTo(TEST_GOAL);
    }

    @Test
    public void getGoal(){
        when(goalRepository.findById(anyLong())).thenReturn(
                        Optional.of(Goal
                                .builder()
                                .name(TEST_GOAL)
                                .initiatives(
                                        List.of(
                                                InitiativeEntity
                                                        .builder()
                                                        .initiative(1L)
                                                        .build(),
                                                InitiativeEntity
                                                        .builder()
                                                        .initiative(2L)
                                                        .build()))
                                .build()));
        when(initiativeClient.getInitiatives("1","2")).thenReturn(TestData.initiativeResponseMap);
        when(initiativeRepository.save(initiativeResponse)).thenReturn(initiativeResponse);
        when(initiativeRepository.save(initiativeResponse2)).thenReturn(initiativeResponse2);
        GoalResponse goal = goalService.get(1L);
        assertThat(goal).isNotNull();
        assertThat(goal.getInitiatives().get(0).getId()).isEqualTo(1L);
    }
}
