package com.example.planservice.model;

import com.example.planservice.entity.Goal;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GoalRequest {
    private String name;
    private List<Long> initiativeIds;

    public static Goal toGoal(GoalRequest goalRequest){
        return Goal
                .builder()
                .name(goalRequest.getName())
                .initiatives(goalRequest.getInitiativeIds())
                .build();
    }

}
