package com.biztechi.ldap.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Operation(summary = "TEST", description = "Hello, World! 출력")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
