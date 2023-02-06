package com.example.planservice.service;

import com.example.planservice.controller.ResourceNotFoundException;
import com.example.planservice.entity.Plan;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.planservice.constants.PlanConstants.*;
import static com.example.planservice.model.PlanRequest.toPlan;
import static com.example.planservice.model.PlanResponse.toPlanResponse;
@RequiredArgsConstructor
@Service
public class PlanService {

    final PlanRepository planRepository;
    final GoalService goalService;

    public String create(PlanRequest planRequest) {
        String planId = planRepository.save(toPlan(planRequest)).getId();
        return planId;
    }

    public String update(PlanRequest planRequest, String planId) {
        get(planId);//Check if the plan id is valid. Throw ResourceNotFoundException otherwise
        Plan planEntity = toPlan(planRequest);
        planEntity.setId(planId);
        planId = planRepository.save(planEntity).getId();
        return planId;
    }

    public List<PlanResponse> getAll(String productName, String productOwner) {
        List<Plan> allPlans = null;
        if(StringUtils.hasLength(productName) && StringUtils.hasLength(productOwner)){
            allPlans = planRepository.findByProductNameAndProductOwner(productName,productOwner);
        }else if(StringUtils.hasLength(productName) && !StringUtils.hasLength(productOwner)) {
            allPlans = planRepository.findByProductName(productName);
        }else if(!StringUtils.hasLength(productName) && StringUtils.hasLength(productOwner)){
            allPlans = planRepository.findByProductOwner(productOwner);
        }else{
            allPlans = planRepository.findAll();
        }
        if(allPlans==null || allPlans.size()==0){
            throw new ResourceNotFoundException(PLANS_NOT_FOUND,PLANS_NOT_FOUND_MSG);
        }
        return allPlans
                .stream()
                .map(p->{
                        List<GoalResponse> goalResponseList = getGoals(p);
                        return toPlanResponse(p,goalResponseList);
                })
                .collect(Collectors.toList());
    }

    public PlanResponse get(String planId) {
        Optional<Plan> plan = planRepository.findById(planId);
        return toPlanResponse(plan.orElseThrow(()->new ResourceNotFoundException(PLAN_NOT_FOUND, PLAN_NOT_FOUND_MSG)),getGoals(plan.get()));
    }



    private List<GoalResponse> getGoals(Plan plan){
        List<GoalResponse> goalResponseList = new ArrayList<>();
        if(plan.getGoals()!=null && plan.getGoals().size()>0){
            for(String goalId: plan.getGoals()){
                GoalResponse goalResponse = goalService.get(goalId);
                goalResponseList.add(goalResponse);
            }
        }
        return goalResponseList;
    }

}
