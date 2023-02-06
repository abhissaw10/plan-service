package com.example.planservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Goal {
    @Id
    String goalId;
    String name;
    List<Long> initiatives;

    public static Set<Long> getUniqueInitiativeIdPerGoal(Goal goal) {
            Set<Long> uniqueInitiativeIds = new HashSet();
            if(goal.getInitiatives()!=null) {
                for (Long id : goal.getInitiatives()) {
                    uniqueInitiativeIds.add(id);
                }
            }
            return uniqueInitiativeIds;
    }

    public static Set<Long> getUniqueInitiveIdsForGoalSet(List<Goal> goals){
        Set<Long> uniqueInitiativeIds = new HashSet();
        for(Goal goal: goals){
            uniqueInitiativeIds.addAll(getUniqueInitiativeIdPerGoal(goal));
        }
        return uniqueInitiativeIds;
    }

}
