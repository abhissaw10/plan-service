package com.example.planservice.graphql;

import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PlanGraphQLController {
    private final PlanService planService;

    @QueryMapping(value = "allPlans")
    public List<PlanResponse> getAll(){
        return planService.getAll(null,null);
    }

    @MutationMapping(value = "createPlan")
    public Long createPlan(@Argument PlanRequest planRequest){
        return planService.create(planRequest);
    }

}
