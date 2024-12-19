package com.example.newsapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summarize")
public class ArticleSummarizerController {

    private final ArticleSummarizerService summarizerService;

    public ArticleSummarizerController(ArticleSummarizerService summarizerService) {
        this.summarizerService = summarizerService;
    }

    @PostMapping
    public ResponseEntity<String> summarize(@RequestBody String articleText) {
        try {
            // Get the summarized article from the service
            String summary = summarizerService.summarizeArticle(articleText);

            // Return the summary as the response body with status OK
            return ResponseEntity.status(HttpStatus.OK).body(summary);
        } catch (Exception e) {
            // In case of any error (e.g., API failure), return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error summarizing the article: " + e.getMessage());
        }
    }
}
