package com.jambit.feedbackservice.controller;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.model.feedback.FeedbackRequestModel;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import com.jambit.feedbackservice.repository.entity.FeedbackEntity;
import com.jambit.feedbackservice.service.feedback.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private FeedbackRequestModel feedbackRequest;
    private FeedbackResponseModel feedbackResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();

        feedbackRequest = new FeedbackRequestModel();
        feedbackRequest.setTitle("Great Place!");
        feedbackRequest.setComment("Loved the food.");
        feedbackRequest.setScore(9);
        feedbackRequest.setPlaceId(1L);

        feedbackResponse = new FeedbackResponseModel();
        feedbackResponse.setTitle("Great Place!");
        feedbackResponse.setComment("Loved the food.");
        feedbackResponse.setScore(9);
        feedbackResponse.setUserId(1L);
        feedbackResponse.setPlaceId(1L);
        feedbackResponse.setPlaceType(PlaceType.RESTAURANT);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCreateFeedbackSuccessfully() throws Exception {
        when(feedbackService.create(any(FeedbackRequestModel.class))).thenReturn(new FeedbackEntity());

        mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(feedbackRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Feedback submitted successfully."));

        verify(feedbackService, times(1)).create(any(FeedbackRequestModel.class));
    }

    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        FeedbackRequestModel invalidRequest = new FeedbackRequestModel();
        invalidRequest.setScore(15); // Invalid score

        mockMvc.perform(post("/api/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(feedbackService, never()).create(any(FeedbackRequestModel.class));
    }

    @Test
    void shouldReturnFeedbackListForPlace() throws Exception {
        when(feedbackService.getFeedbackByPlace(1L)).thenReturn(List.of(feedbackResponse));

        mockMvc.perform(get("/api/places/1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Great Place!"));

        verify(feedbackService, times(1)).getFeedbackByPlace(1L);
    }
}
