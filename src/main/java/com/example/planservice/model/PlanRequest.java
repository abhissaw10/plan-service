package com.example.planservice.model;

import com.example.planservice.entity.Plan;
import com.example.planservice.entity.PlanGoal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PlanRequest {
    private List<Integer> goals;
    //@NotBlank(message = "Please specify product name")
    @NotNull(message = "Please specify product name")
    private String productName;
    @NotBlank(message = "Please specify product owner")
    private String productOwner;
    @Pattern(regexp = "^[0-9]{4}", message = "Invalid Financial Year. Expected format YYYY")
    private String financialYear;

    public static Plan toPlan(PlanRequest planRequest){
        return Plan
                .builder()
                .goals(toPlanGoalEntity(planRequest.goals))
                .productName(planRequest.productName)
                .productOwner(planRequest.productOwner)
                .financialYear(planRequest.financialYear)
                .build();
    }

    private static List<PlanGoal> toPlanGoalEntity(List<Integer> ids){
        if(ids!=null && ids.size()>0){
            List<PlanGoal> planGoals = new ArrayList<>();
            for(Integer id: ids){
                planGoals.add(PlanGoal.builder().goalId(id).build());
            }
            return planGoals;
        }
        return null;
    }
}
