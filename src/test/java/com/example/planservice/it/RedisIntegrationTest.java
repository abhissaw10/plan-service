package com.example.planservice.it;

import com.example.planservice.repository.GoalRepository;
import com.example.planservice.service.GoalService;
import com.example.planservice.service.InitiativeClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class RedisIntegrationTest {

    @MockBean
    private InitiativeClient initiativeClient;

    @MockBean
    private GoalRepository goalRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    CacheManager cacheManager;

    /*@Test
    void givenRedisCache_OnRestCallFailure_shouldReturnFromCache(){
        when(goalRepository.findById(TEST_GOAL_ID)).thenReturn(Optional.of(goalEntity));

        goalService.get(TEST_GOAL_ID);
    }*/
}
