package com.platzi_play.web.controller;

import com.platzi_play.domain.service.PlatziPlayAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final String appName;
    private final PlatziPlayAiService platziPlayAiService;

    public HelloController(@Value("${spring.application.name}") String appName, PlatziPlayAiService platziPlayAiService) {
        this.appName = appName;
        this.platziPlayAiService = platziPlayAiService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return this.platziPlayAiService.generateGreeting(appName);
    }
}
