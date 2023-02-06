package com.example.planservice.config;

import com.example.planservice.entity.Goal;
import com.example.planservice.entity.Plan;
import com.example.planservice.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class TestData {
    public static final String TEST_PLAN_1 = "test_plan_1";
    public static String TEST_PLAN = "test_plan";

    public static String TEST_GOAL = "test_goal";
    public static String TEST_GOAL_ID = "test_goal_id";
    public static String TEST_GOAL_2 = "test_goal_2";

    public static String TEST_PRODUCT_NAME = "test_product_name";
    public static String TEST_PRODUCT_NAME_2 = "test_product_name_2";
    public static String TEST_PRODUCT_OWNER = "test_product_owner";
    public static String TEST_PRODUCT_OWNER_2 = "test_product_owner_2";

    public static String TEST_FIN_YEAR = "29-01-2023";

    public static String TEST_STATUS = "test_status";
    public static LocalDate TEST_START_DATE = LocalDate.of(2023,01,29);
    public static LocalDate TEST_END_DATE = LocalDate.of(2023,12,29);

    public static String TEST_TITLE_1 = "test_title_1";
    public static String TEST_TITLE_2 = "test_title_2";


    public static List<Plan> planList(){
        return List.of(Plan
                .builder()
                .id(TEST_PLAN)
                .goals(List.of("1","2"))
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build());
    }

    public static Plan plan(){
        return Plan
                .builder()
                .id(TEST_PLAN)
                .goals(List.of("1"))
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build();
    }
    public static PlanRequest planRequest(){
        return PlanRequest
                .builder()
                .goals(List.of("1","2"))
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build();
    }

    public static PlanResponse singlePlanResponse(){
        return
                PlanResponse
                        .builder()
                        .planId(TEST_PLAN)
                        .goals(List.of(GoalResponse.builder().name(TEST_GOAL).build()))
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
                .goals(List.of(GoalResponse.builder().name(TEST_GOAL).build()))
                .productName(TEST_PRODUCT_NAME)
                .productOwner(TEST_PRODUCT_OWNER)
                .financialYear(TEST_FIN_YEAR)
                .build(),
                PlanResponse
                .builder()
                .planId(TEST_PLAN_1)
                .goals(List.of(GoalResponse.builder().name(TEST_GOAL_2).build()))
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
                        .goals(List.of(GoalResponse.builder().name(TEST_GOAL).build()))
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
                        .goals(List.of(GoalResponse.builder().name(TEST_GOAL).build()))
                        .productName(TEST_PRODUCT_NAME)
                        .productOwner(TEST_PRODUCT_OWNER)
                        .build()
        );
    }



    public static Initiative initiativeResponse2 = Initiative
            .builder()
            .id(2L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_2)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();

    public static Initiative initiativeResponse = Initiative
            .builder()
            .id(1L)
            .status(TEST_STATUS)
            .title(TEST_TITLE_1)
            .startDate(TEST_START_DATE)
            .endDate(TEST_END_DATE)
            .build();


    public static Map<Long,Initiative> initiativeResponseMap = Map.of(1L,initiativeResponse,2L,initiativeResponse2);
    public static Map<Long,Initiative> initiativeResponseMapWiremock = Map.of(1L,initiativeResponse);

    public  static GoalRequest goalRequest = GoalRequest
            .builder()
            .name(TEST_GOAL)
            .initiativeIds(List.of(1L,2L))
            .build();

    public  static GoalResponse goalResponse = GoalResponse
            .builder()
            .name(TEST_GOAL)
            .id(TEST_GOAL_ID)
            .initiatives(List.of(initiativeResponse,initiativeResponse2))
            .build();

    public  static GoalRequest goalRequestIntegration = GoalRequest
            .builder()
            .name(TEST_GOAL)
            .initiativeIds(List.of(1L))
            .build();

    public  static GoalResponse dummyGoalResponse1 = GoalResponse
            .builder()
            .name(TEST_GOAL)
            .id("1")
            .build();
    public  static GoalResponse dummyGoalResponse2 = GoalResponse
            .builder()
            .name(TEST_GOAL_2)
            .id("2")
            .build();

    public  static Goal goalEntity = Goal
            .builder()
            .name(TEST_GOAL_2)
            .goalId("2")
            .initiatives(List.of(1L))
            .build();
}
