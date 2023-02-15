package com.example.planservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goal_sequence")
    Integer goalId;
    String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "initiatives")
    List<InitiativeEntity> initiatives;

    public static Set<Long> getUniqueInitiativeIdPerGoal(Goal goal) {
            Set<Long> uniqueInitiativeIds = new HashSet();
            if(goal.getInitiatives()!=null) {
                for (InitiativeEntity id : goal.getInitiatives()) {
                    uniqueInitiativeIds.add(id.getInitiative());
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
