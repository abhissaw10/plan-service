package com.example.planservice.controller;

import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class PlanController {
    final PlanService planService;

    @Operation(summary = "Create a new project plan", description = "API to create a new project plan with the underlying initiatives")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the project plan id for tje created project plan")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/plans")
    public Long  create(@Valid @RequestBody PlanRequest planRequest){
        return planService.create(planRequest);
    }

    @Operation(summary = "Create a new project plan", description = "API to create a new project plan with the underlying initiatives")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the project plan id for tje created project plan")
    })
    @PutMapping("/v1/plans/{planId}")
    public Long  update(@Valid @RequestBody PlanRequest planRequest, @PathVariable Long planId){
        return planService.update(planRequest,planId);
    }

    @Operation(summary = "Retrieve all project plans", description = "API to get all the project plans for existing products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return list of project plans based on the filter criteria"),
            @ApiResponse(responseCode = "404", description = "No product plans found for the given filter criteria")
    })
    @GetMapping("/v1/plans")
    public List<PlanResponse> getAll(@RequestParam(required = false) String productName,@RequestParam(required = false) String productOwner){
        return planService.getAll(productName,productOwner);
    }

    @Operation(summary = "Retrieve plan for the  given plan id", description = "API to get the product plan details for an existing product id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return the product details of the given product id"),
            @ApiResponse(responseCode = "404", description = "Product plan not found for the given plan id")
    })
    @GetMapping("/v1/plans/{planId}")
    public PlanResponse get(@PathVariable Long planId){
        return planService.get(planId);
    }
}
