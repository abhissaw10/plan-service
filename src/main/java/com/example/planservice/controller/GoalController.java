package com.example.planservice.controller;

import com.example.planservice.model.GoalRequest;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GoalController {
    final GoalService goalService;

    @Operation(summary = "Create a new goal", description = "API to create a new goal with the underlying initiatives")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the goal id for created goal")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/goals")
    public Integer create(@Valid @RequestBody GoalRequest goalRequest){
        return goalService.create(goalRequest);
    }

    @PutMapping("/v1/goals/{goalId}")
    public Integer update(@Valid @RequestBody GoalRequest goalRequest, @PathVariable Integer goalId){
        return goalService.update(goalRequest,goalId);
    }

    @Operation(summary = "Retrieve all goals", description = "API to get all the goals. Can filter by goal name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return list of goals based on the filter criteria")
    })
    @GetMapping("/v1/goals")
    public List<GoalResponse> getAll(@RequestParam(required = false) String name) throws ResourceNotFoundException{
        return goalService.getAll(name);
    }

    @Operation(summary = "Retrieve goal for the give goal id", description = "API to get the goal details given a goal id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return goal details for the given goal id"),
            @ApiResponse(responseCode = "404", description = "No goal found for the given id")
    })
    @GetMapping("/v1/goals/{id}")
    public GoalResponse get(@PathVariable Integer id) throws ResourceNotFoundException{
        return goalService.get(id);
    }


}
