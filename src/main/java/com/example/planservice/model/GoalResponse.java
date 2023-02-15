package com.example.planservice.model;

import com.example.planservice.entity.Goal;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Data
public class GoalResponse {
    private Integer id;
    private String name;
    private List<Initiative> initiatives;

    public static List<GoalResponse> toGoalResponseList(List<Goal> goals, Map<Long,Initiative> initiativeMap){
        List<GoalResponse> goalResponses = new ArrayList();
        for(Goal goal: goals){
           goalResponses.add(toGoalResponse(goal,initiativeMap));
        }
        return goalResponses;
    }

    public static GoalResponse toGoalResponse(Goal goal, Map<Long,Initiative> initiativeMap){
        return GoalResponse
                .builder()
                .id(goal.getGoalId())
                .name(goal.getName())
                .initiatives((goal.getInitiatives()!=null && goal.getInitiatives().size()>0)?getGoalInitiatives(goal,initiativeMap):null)
                .build();
    }

    public static List<Initiative> getGoalInitiatives(Goal goal,Map<Long,Initiative> initiativeMap){
        return goal.getInitiatives().stream().map(i->initiativeMap.get(i.getInitiative())).collect(Collectors.toList());
    }
}
