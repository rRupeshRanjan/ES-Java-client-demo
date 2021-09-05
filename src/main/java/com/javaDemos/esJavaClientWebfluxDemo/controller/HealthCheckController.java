package com.javaDemos.esJavaClientWebfluxDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public Mono<String> getHealthStatus() {
        return Mono.just("Up");
    }
}
