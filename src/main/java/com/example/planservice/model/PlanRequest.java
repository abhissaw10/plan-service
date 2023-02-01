package com.example.planservice.model;

import com.example.planservice.entity.Plan;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanRequest {
    private String goal;
    private String productName;
    private String productOwner;
    private String financialYear;

    public static Plan toPlan(PlanRequest planRequest){
        return Plan
                .builder()
                .goal(planRequest.goal)
                .productName(planRequest.productName)
                .productOwner(planRequest.productOwner)
                .financialYear(planRequest.financialYear)
                .build();
    }
}
