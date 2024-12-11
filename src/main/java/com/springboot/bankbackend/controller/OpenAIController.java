//package com.springboot.bankbackend.controller;
//
//import com.springboot.bankbackend.bo.ChatRequest;
//import com.springboot.bankbackend.service.OpenAIService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class OpenAIController {
//
//    private final OpenAIService openAIService;
//
//    public OpenAIController(OpenAIService openAIService) {
//        this.openAIService = openAIService;
//    }
//
//    @PostMapping("/api/v1/user/chat")
//    public String chat(@RequestBody ChatRequest request) {
//        return openAIService.getChatGPTResponse(request.getPrompt());
//    }
//}
