package com.example.newsapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class ArticleSummarizerService {

    @Value("${google.gemini.api.url}")
    private String apiUrl;

    @Value("${google.gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ArticleSummarizerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String summarizeArticle(String articleText) throws IOException {
        // Construct the URL with the API key as a query parameter
        String apiUrlWithKey = apiUrl + "?key=" + apiKey;

        // Request body for the API
        String requestBody = "{ \"instances\": [{ \"content\": \"" + articleText + "\" }] }";

        // Set headers for the API request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(apiUrlWithKey, HttpMethod.POST, entity, String.class);

        // Parse the response body (assuming it's a JSON response)
        String responseBody = response.getBody();

        // Parse the response body to extract the summary (adjust based on response structure)
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);

        // Extract the summary (this key might be different, check your API documentation)
        String summary = (String) responseMap.get("summary");

        return summary;
    }
}
