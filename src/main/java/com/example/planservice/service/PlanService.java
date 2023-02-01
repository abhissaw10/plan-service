package com.example.planservice.service;

import com.example.planservice.entity.Plan;
import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;
import com.example.planservice.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.planservice.model.PlanRequest.toPlan;
import static com.example.planservice.model.PlanResponse.toPlanResponse;
@RequiredArgsConstructor
@Service
public class PlanService {

    final PlanRepository planRepository;

    public String create(PlanRequest planRequest) {
        String planId = planRepository.save(toPlan(planRequest)).getId();
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
        return allPlans.stream().map(p-> toPlanResponse(p)).collect(Collectors.toList());
    }
}
