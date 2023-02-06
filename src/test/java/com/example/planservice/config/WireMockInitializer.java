package com.example.planservice.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import lombok.SneakyThrows;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.http.MediaType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.planservice.config.TestData.initiativeResponseMapWiremock;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;


@Profile("integration-test")

public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {


    @SneakyThrows
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        WireMockServer server = new WireMockServer(new WireMockConfiguration().extensions(new ResponseTemplateTransformer(false)).dynamicPort());
        String json = new ObjectMapper().writeValueAsString(initiativeResponseMapWiremock);
        server.stubFor(WireMock
                .get("/v1/initiatives/byIds")
                .withQueryParam("ids", matching("^[0-9]+(,[0-9]+)*$"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        server.start();
        applicationContext.getBeanFactory().registerSingleton("wireMockServer",server);
        applicationContext.addApplicationListener(event -> {
            if(event instanceof ContextClosedEvent){
                server.stop();
            }
        });
        TestPropertyValues.of("initiative.service.url=http://localhost:"+server.port()).applyTo(applicationContext);
    }
}
