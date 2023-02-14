package com.example.planservice.service;


import com.example.planservice.model.Initiative;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange(url = "${initiative.service.url}", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface InitiativeClient {
    @GetExchange(value = "/v1/initiatives/byIds")
    Map<Long,Initiative> getInitiatives(@RequestParam("ids") String... ids);
}
