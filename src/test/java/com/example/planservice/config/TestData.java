package com.example.planservice.config;

import com.example.planservice.entity.Plan;
import com.example.planservice.model.PlanRequest;
import com.example.planservice.model.PlanResponse;

import java.util.List;

public class TestData {
    private static final String TEST_PLAN_1 = "test_plan_1";
    public static String TEST_PLAN = "test_plan";

    public static String TEST_GOAL = "test_goal";
    public static String TEST_GOAL_2 = "test_goal_2";

    public static String TEST_PRODUCT_NAME = "test_product_name";
    public static String TEST_PRODUCT_NAME_2 = "test_product_name_2";
    public static String TEST_PRODUCT_OWNER = "test_product_owner";
    public static String TEST_PRODUCT_OWNER_2 = "test_product_owner_2";

    public static String TEST_FIN_YEAR = "29-01-2023";

    public static List<Plan> planList(){
        return List.of(Plan
                .builder()
                .id(TEST_PLAN)
                .goal(TEST_GOAL)
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build());
    }
    public static PlanRequest planRequest(){
        return PlanRequest
                .builder()
                .goal(TEST_GOAL)
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build();
    }

    public static List<PlanResponse> planResponse(){
        return List.of(
                PlanResponse
                .builder()
                .planId(TEST_PLAN)
                .goal(TEST_GOAL)
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build(),
                PlanResponse
                .builder()
                .planId(TEST_PLAN_1)
                .goal(TEST_GOAL_2)
                .productName(TEST_PRODUCT_NAME_2)
                .productOwner(TEST_PRODUCT_OWNER_2)
                .financialYear(TEST_FIN_YEAR)
                .build());
    }
    public static List<PlanResponse> planResponseByName(){
        return List.of(
                PlanResponse
                        .builder()
                        .planId(TEST_PLAN)
                        .goal(TEST_GOAL)
                        .productName(TEST_PRODUCT_NAME)
                        .productOwner(TEST_PRODUCT_OWNER)
                        .build()
                );
    }

    public static List<PlanResponse> planResponseByOwner(){
        return List.of(
                PlanResponse
                        .builder()
                        .planId(TEST_PLAN)
                        .goal(TEST_GOAL)
                        .productName(TEST_PRODUCT_NAME)
                        .productOwner(TEST_PRODUCT_OWNER)
                        .build()
        );
    }

}
