package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.service.OpenAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    private final OpenAIService openAIService;

    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/api/v1/auth/chat")
    public String chat(@RequestParam String prompt) {
        return openAIService.getChatGPTResponse(prompt);
    }
}
