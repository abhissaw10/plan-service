package com.example.planservice.service;

import com.example.planservice.controller.ResourceNotFoundException;
import com.example.planservice.entity.Goal;
import com.example.planservice.model.GoalRequest;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.model.Initiative;
import com.example.planservice.repository.GoalRepository;
import com.example.planservice.repository.InitiativeRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.planservice.constants.PlanConstants.*;
import static com.example.planservice.entity.Goal.getUniqueInitiativeIdPerGoal;
import static com.example.planservice.entity.Goal.getUniqueInitiveIdsForGoalSet;
import static com.example.planservice.model.GoalResponse.toGoalResponse;
import static com.example.planservice.model.GoalResponse.toGoalResponseList;

@RequiredArgsConstructor
@Service
public class GoalService {

    final GoalRepository goalRepository;
    final InitiativeClient initiativeClient;

    final InitiativeRepository initiativeRepository;

    public String create(GoalRequest goalRequest) {
        return goalRepository.save(GoalRequest.toGoal(goalRequest)).getGoalId();
    }

    public String update(GoalRequest goalRequest, String goalId) {
        Goal goal = GoalRequest.toGoal(goalRequest);
        get(goalId);
        goal.setGoalId(goalId);
        return goalRepository.save(goal).getGoalId();
    }

    public List<GoalResponse> getAll(String name) throws ResourceNotFoundException{
        List<Goal> goals = null;
        if(name==null){
            goals = goalRepository.findByName(name);
        }
        goals = goalRepository.findAll();
        if(goals!=null && goals.size()>0){
            Set<Long> uniqueIds = getUniqueInitiveIdsForGoalSet(goals);
            Map<Long, Initiative> initiativeMap = getInitiatives(uniqueIds);
            return toGoalResponseList(goals,initiativeMap);
        }
        throw new ResourceNotFoundException(GOALS_NOT_FOUND,GOALS_NOT_FOUND_MSG);
    }

    public Map<Long, Initiative> getInitiatives(Set<Long> uniqueInitiativeIds) {
        String[] ids = new String[uniqueInitiativeIds.size()];
        int index=0;
        for(Long id: uniqueInitiativeIds){
            ids[index++]=id.toString();
        }
        Map<Long, Initiative> initiativeResponse = initiativeClient.getInitiatives(ids);
        return initiativeResponse;
    }

    public GoalResponse get(String goalId) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(()->new ResourceNotFoundException(GOAL_NOT_FOUND,GOAL_NOT_FOUND_MSG));
        Set<Long> uniqueIds = getUniqueInitiativeIdPerGoal(goal);
        Map<Long, Initiative> initiativeMap = null;
        try {
            initiativeMap = getInitiatives(uniqueIds);
            for(Long id: uniqueIds){
                saveToRedis(initiativeMap.get(id));
            }
        }catch (FeignException e){
            initiativeMap = new HashMap<>();
            for(Long id: uniqueIds){
                readFromRedis(id);
            }
        }
        return toGoalResponse(goal,initiativeMap);
    }

    //@CachePut(value = "initiativeCache", key = "#initiative.id")
    public void saveToRedis(Initiative initiative){

        initiativeRepository.save(initiative);
    }

    //@Cacheable(value = "initiativeCache", key="#id")
    public Initiative readFromRedis(Long id){
        Optional<Initiative> initiativeOptional = initiativeRepository.findById(id);
        return initiativeOptional.isPresent()?initiativeOptional.get():null;

    }
}
