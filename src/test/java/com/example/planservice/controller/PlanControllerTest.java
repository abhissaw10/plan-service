package com.example.planservice.controller;

import com.example.planservice.model.ErrorResponse;
import com.example.planservice.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.example.planservice.config.TestData.*;
import static com.example.planservice.constants.PlanConstants.BAD_REQUEST_001;
import static com.example.planservice.constants.PlanConstants.ERROR_MESSAGE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlanService planService;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void createPlanWithRequestBody_shouldReturn200OK() throws Exception {
        when(planService.create(planRequest())).thenReturn(TEST_PLAN);
        mockMvc.perform(post("/v1/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(planRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().string(TEST_PLAN.toString()));
    }

    @Test
    public void givenInvalidRequestBody_shouldThrowError() throws Exception {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .errorCode(BAD_REQUEST_001)
                .errorMessage(ERROR_MESSAGE)
                .errorFieldsList(List.of("Please specify product name","Please specify product owner"))
                .build();
        when(planService.create(invalid_planRequest())).thenReturn(TEST_PLAN);
        mockMvc.perform(post("/v1/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalid_planRequest())))
                .andExpect(status().isBadRequest());
                //.andExpect(content().string(mapper.writeValueAsString(errorResponse)));
    }
    @Test
    public void updatePlanWithRequestBody_shouldReturn200OK() throws Exception {
        when(planService.update(planRequest(),TEST_PLAN)).thenReturn(TEST_PLAN);
        mockMvc.perform(put("/v1/plans/"+TEST_PLAN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(planRequest())))
                .andExpect(status().isOk())
                .andExpect(content().string(TEST_PLAN.toString()));
    }

    @Test
    public void getAllPlans_shouldReturn200OK() throws Exception {
        when(planService.getAll(null,null)).thenReturn(planResponse());
        mockMvc.perform(get("/v1/plans")
                        )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(planResponse())));
    }

    @Test
    public void getAllPlansByProductName_shouldReturn200OK() throws Exception {
        when(planService.getAll(TEST_PRODUCT_NAME,null)).thenReturn(planResponseByName());
        mockMvc.perform(get("/v1/plans")
                        .queryParam("productName",TEST_PRODUCT_NAME)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(planResponseByName())));
    }

    @Test
    public void getAllPlansByProductOwner_shouldReturn200OK() throws Exception {
        when(planService.getAll(null,TEST_PRODUCT_OWNER)).thenReturn(planResponseByOwner());
        mockMvc.perform(get("/v1/plans")
                        .queryParam("productOwner",TEST_PRODUCT_OWNER)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(planResponseByOwner())));
    }

    @Test
    public void getAllPlansByProductNameNOwner_shouldReturn200OK() throws Exception {
        when(planService.getAll(TEST_PRODUCT_NAME,TEST_PRODUCT_OWNER)).thenReturn(planResponseByOwner());
        mockMvc.perform(get("/v1/plans")
                        .queryParam("productName",TEST_PRODUCT_NAME)
                        .queryParam("productOwner",TEST_PRODUCT_OWNER)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(planResponseByOwner())));
    }

    @Test
    public void givenPlanId_shouldReturnMatchingPlan() throws Exception{
        when(planService.get(TEST_PLAN)).thenReturn(singlePlanResponse());
        mockMvc.perform((get("/v1/plans/"+TEST_PLAN)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(singlePlanResponse())));
    }

}
