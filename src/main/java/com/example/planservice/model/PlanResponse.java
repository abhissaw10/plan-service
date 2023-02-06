package com.example.planservice.model;

import com.example.planservice.entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PlanResponse {
    private String planId;
    private List<GoalResponse> goals;
    private String productName;
    private String productOwner;
    private String financialYear;

    public static PlanResponse toPlanResponse(Plan p, List<GoalResponse> goalResponseList){
        return PlanResponse
                .builder()
                .planId(p.getId())
                .goals(goalResponseList)
                .productName(p.getProductName())
                .productOwner(p.getProductOwner())
                .financialYear(p.getFinancialYear())
                .build();
    }

}
