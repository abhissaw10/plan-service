package com.example.planservice.config;

import com.example.planservice.service.InitiativeClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PlanServiceConfig {

    @Value("${initiative.service.url}")
    private String initiativeServiceURL;

    @Bean
    WebClient webClient(){
        return WebClient.builder()
                .baseUrl(initiativeServiceURL)
                .build();
    }
    @SneakyThrows
    @Bean
    InitiativeClient postClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(InitiativeClient.class);
    }








}
