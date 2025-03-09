package com.jambit.feedbackservice.controller;

import com.jambit.feedbackservice.model.feedback.FeedbackRequestModel;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import com.jambit.feedbackservice.service.FeedbackService;
import com.jambit.feedbackservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("feedbacks")
    public ResponseEntity<String> create(@Valid @RequestBody FeedbackRequestModel request) {
        feedbackService.create(request);
        return ResponseEntity.ok("Feedback submitted successfully.");
    }

    @GetMapping("/places/{placeId}/feedbacks")
    public ResponseEntity<List<FeedbackResponseModel>> getFeedbacks(@PathVariable Long placeId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByPlace(placeId));
    }
}
