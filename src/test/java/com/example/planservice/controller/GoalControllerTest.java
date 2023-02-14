package com.example.planservice.controller;

import com.example.planservice.model.ErrorResponse;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.service.GoalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.planservice.config.TestData.*;
import static com.example.planservice.constants.PlanConstants.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoalController.class)
//@ExtendWith(SpringExtension.class)
public class GoalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoalService goalService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void givenRequestBody_shouldCreateGoal() throws Exception {
        when(goalService.create(goalRequest)).thenReturn(TEST_GOAL_ID);
        mockMvc
                .perform(post("/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(goalRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidRequestBody_shouldThrowError() throws Exception {
        when(goalService.create(invalid_goalRequest)).thenReturn(TEST_GOAL_ID);
        mockMvc
                .perform(post("/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid_goalRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenRequestBody_shouldUpdateGoal() throws Exception {
        when(goalService.update(goalRequest, TEST_GOAL_ID))
        .thenReturn(TEST_GOAL_ID);
        mockMvc
                .perform(put("/v1/goals/"+TEST_GOAL_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(goalRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAllGoals() throws Exception {
        List<GoalResponse> response = List.of(GoalResponse
                .builder()
                .id(TEST_GOAL_ID)
                .name(TEST_GOAL)
                .build());
        when(goalService.getAll(null)).thenReturn(response);
        mockMvc
                .perform(get("/v1/goals"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(response)));
    }

    @Test
    public void givenGoalId_shouldReturnGoalMatchingGoalId() throws Exception {

        when(goalService.get(TEST_GOAL_ID)).thenReturn(goalResponse);
        mockMvc
                .perform(get("/v1/goals/"+TEST_GOAL_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(goalResponse)));
    }

    @Test
    public void givenGoalId_shouldThrowExceptionWhenNoMatchingGoalId() throws Exception {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .errorCode(GOAL_NOT_FOUND)
                .errorMessage(GOAL_NOT_FOUND_MSG)
                .build();
        when(goalService.get(TEST_GOAL_ID)).thenThrow(new ResourceNotFoundException(GOAL_NOT_FOUND,GOAL_NOT_FOUND_MSG));
        mockMvc
                .perform(get("/v1/goals/"+TEST_GOAL_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().string(mapper.writeValueAsString(errorResponse)));
    }

    @Test
    public void givenGoalName_shouldReturnAllGoalsMatchingName() throws Exception {
        List<GoalResponse> response = List.of( goalResponse);
        when(goalService.getAll(TEST_GOAL)).thenReturn(response);
        mockMvc
                .perform(get("/v1/goals").queryParam("name",TEST_GOAL))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(response)));
    }

    @Test
    public void whenNoGoalsFound_shouldReturnErrorMessage() throws Exception {
        when(goalService.getAll(null)).thenThrow(new ResourceNotFoundException(GOALS_NOT_FOUND,GOALS_NOT_FOUND_MSG));
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .errorCode(GOALS_NOT_FOUND)
                .errorMessage(GOALS_NOT_FOUND_MSG)
                .build();
        mockMvc
                .perform(get("/v1/goals"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(mapper.writeValueAsString(errorResponse)));
    }
}
