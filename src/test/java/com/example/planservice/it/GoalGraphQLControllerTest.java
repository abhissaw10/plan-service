package com.example.planservice.it;

import com.example.planservice.controller.GoalController;
import com.example.planservice.model.GoalResponse;
import com.example.planservice.service.GoalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import static com.example.planservice.config.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@GraphQlTest(GoalController.class)
public class GoalGraphQLControllerTest {

    @Autowired
    GraphQlTester graphQlTester;

    @MockBean
    GoalService goalService;


    @Test
    void should_returnAllGoals() {
        // language=GraphQL
        String document = """
    			query {
    			    allGoals {
                        id
                        name
                    }
    			}
				""";
        when(goalService.getAll(null)).thenReturn(goalResponses);
        graphQlTester.document(document)
                .execute()
                .path("allGoals")
                .entityList(GoalResponse.class)
                .hasSize(1);
    }

    @Test
    void should_returnAGoal() {
        // language=GraphQL
        String document = """
    			query findOne($id: ID){
    			    findOne(id: $id){
    			        id
    			        name
    			    }
    			}
				""";
        when(goalService.get(anyInt())).thenReturn(goalResponse);
        graphQlTester.document(document)
                .execute()
                .path("findOne")
                .entity(GoalResponse.class)
                .satisfies(goal->{
                    assertThat(goal.getId()).isEqualTo(TEST_GOAL_ID);
                });
    }
}
