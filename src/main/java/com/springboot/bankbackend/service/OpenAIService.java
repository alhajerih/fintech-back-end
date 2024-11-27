package com.springboot.bankbackend.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

  private final String API_URL = "https://api.openai.com/v1/chat/completions";

  private final String sampleResponse = "";

  @Value("${key:default}")
  private String API_KEY;

  public String getChatGPTResponse(String prompt) {

    System.out.println("API KEY : " + API_KEY);

    RestTemplate restTemplate = new RestTemplate();

    // Prepare headers
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + API_KEY);
    headers.set("Content-Type", "application/json");

    // Prepare request body
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", "o1-mini");
    requestBody.put("messages", new Object[] {Map.of("role", "user", "content", prompt)});

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    // Make API call
    ResponseEntity<String> response =
        restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

    return response.getBody();
  }

}
