package com.example.planservice.service;


import com.example.planservice.model.Initiative;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "initiativeservice", url = "${initiative.service.url}")
public interface InitiativeClient {
    @GetMapping(value = "/v1/initiatives/byIds")
    @Headers({"Accept: " + MediaType.APPLICATION_JSON_VALUE, "Content-Type: " + MediaType.APPLICATION_JSON_VALUE})
    Map<Long,Initiative> getInitiatives(@RequestParam("ids") String... ids);
}
