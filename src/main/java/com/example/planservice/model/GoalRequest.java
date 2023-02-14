package com.example.planservice.model;

import com.example.planservice.entity.Goal;
import com.example.planservice.entity.InitiativeEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class GoalRequest {
    @NotBlank(message = "Please specify the goal name")
    private String name;
    private List<Long> initiativeIds;

    public static Goal toGoal(GoalRequest goalRequest){
        return Goal
                .builder()
                .name(goalRequest.getName())
                .initiatives(toInitiativeEntityList(goalRequest.getInitiativeIds()))
                .build();
    }

    private static List<InitiativeEntity> toInitiativeEntityList(List<Long> ids){
        if(ids!=null && ids.size()>0){
            List<InitiativeEntity> initiativeEntityList = new ArrayList<>();
            for(Long id: ids) {
                initiativeEntityList.add(InitiativeEntity.builder().initiative(id).build());
            }
            return initiativeEntityList;
        }
       return null;
    }

}
