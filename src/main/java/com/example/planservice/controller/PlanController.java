package com.example.planservice.controller;

import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlanController {
    final PlanService planService;

    @Operation(summary = "Create a new project plan", description = "API to create a new project plan with the underlying initiatives")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the project plan id for creating a new project plan")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/plans")
    public String  create(@RequestBody PlanRequest planRequest){
        return planService.create(planRequest);
    }

    @Operation(summary = "Retrieve all project plans", description = "API to get all the project plans for existing products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return list of project plans based on the filter criteria")
    })
    @GetMapping("/v1/plans")
    public List<PlanResponse> getAll(@RequestParam(required = false) String productName,@RequestParam(required = false) String productOwner){
        return planService.getAll(productName,productOwner);
    }
}
