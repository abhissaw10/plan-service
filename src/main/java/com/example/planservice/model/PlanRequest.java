package com.example.planservice.model;

import com.example.planservice.entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlanRequest {
    private List<String> goals;
    private String productName;
    private String productOwner;
    private String financialYear;

    public static Plan toPlan(PlanRequest planRequest){
        return Plan
                .builder()
                .goals(planRequest.goals)
                .productName(planRequest.productName)
                .productOwner(planRequest.productOwner)
                .financialYear(planRequest.financialYear)
                .build();
    }
}
