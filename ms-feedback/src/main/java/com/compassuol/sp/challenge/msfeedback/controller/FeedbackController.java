package com.compassuol.sp.challenge.msfeedback.controller;

import com.compassuol.sp.challenge.msfeedback.dto.FeedbackRequestDTO;
import com.compassuol.sp.challenge.msfeedback.model.FeedbackModel;
import com.compassuol.sp.challenge.msfeedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public List<FeedbackModel> getAllFeedbacks() {
        return feedbackService.getAllFeedbacksService();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFeedbackById(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.getFeedbackById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createFeedback(@RequestBody @Valid FeedbackRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feedbackService.createFeedbackService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") Long id,
                                                 @RequestBody @Valid FeedbackRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.updateFeedbackService(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedbackService(id);
        return ResponseEntity.noContent().build();
    }
}
