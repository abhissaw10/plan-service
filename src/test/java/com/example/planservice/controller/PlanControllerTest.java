package com.example.planservice.controller;

import com.example.planservice.service.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.planservice.config.TestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
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
                .andExpect(MockMvcResultMatchers.content().string(TEST_PLAN));
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

}
