package com.example.planservice.graphql;

import com.example.planservice.model.GoalRequest;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class GoalGraphQLController {
    private final GoalService goalService;
    @QueryMapping(value = "allGoals")
    public List<GoalResponse> getAll(){
        return goalService.getAll(null);
    }

    @QueryMapping(value = "findOne")
    public GoalResponse findOne(@Argument Integer id){
        return goalService.get(id);
    }

    @MutationMapping(value = "createGoal")
    public Integer createGoal(@Argument GoalRequest goalRequest){
        return goalService.create(goalRequest);
    }


}
