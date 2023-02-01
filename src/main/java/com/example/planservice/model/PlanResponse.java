package com.example.planservice.model;

import com.example.planservice.entity.Plan;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PlanResponse {
    private String planId;
    private String goal;
    private String productName;
    private String productOwner;
    private String financialYear;

    public static PlanResponse toPlanResponse(Plan p){
        return PlanResponse
                .builder()
                .planId(p.getId())
                .goal(p.getGoal())
                .productName(p.getProductName())
                .productOwner(p.getProductOwner())
                .financialYear(p.getFinancialYear())
                .build();
    }
}
