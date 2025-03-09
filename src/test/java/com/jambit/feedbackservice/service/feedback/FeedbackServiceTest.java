package com.jambit.feedbackservice.service.feedback;

import com.jambit.feedbackservice.repository.entity.FeedbackEntity;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import com.jambit.feedbackservice.repository.entity.UserEntity;
import com.jambit.feedbackservice.mapper.FeedbackMapper;
import com.jambit.feedbackservice.model.feedback.FeedbackRequestModel;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import com.jambit.feedbackservice.repository.FeedbackRepository;
import com.jambit.feedbackservice.service.place.PlaceService;
import com.jambit.feedbackservice.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private PlaceService placeService;

    @Mock
    private UserService userService;

    @Mock
    private FeedbackMapper feedbackMapper;

    @InjectMocks
    private FeedbackService feedbackService;

    private UserEntity user;
    private PlaceEntity place;
    private FeedbackRequestModel feedbackRequest;
    private FeedbackEntity feedbackEntity;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(1L)
                .email("user@example.com")
                .build();

        place = PlaceEntity.builder()
                .id(1L)
                .name("Pizza Palace")
                .build();

        feedbackRequest = new FeedbackRequestModel();
        feedbackRequest.setTitle("Great place!");
        feedbackRequest.setComment("Amazing food and service.");
        feedbackRequest.setScore(9);
        feedbackRequest.setPlaceId(1L);

        feedbackEntity = FeedbackEntity.builder()
                .id(1L)
                .user(user)
                .place(place)
                .title(feedbackRequest.getTitle())
                .comment(feedbackRequest.getComment())
                .score(feedbackRequest.getScore())
                .createTime(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateFeedbackWhenNotExisting() {
        // Mock user retrieval
        when(userService.getCurrentUser()).thenReturn(user);
        when(placeService.getPlace(feedbackRequest.getPlaceId())).thenReturn(place);
        when(feedbackRepository.findByUserIdAndPlaceId(user.getId(), feedbackRequest.getPlaceId()))
                .thenReturn(Optional.empty()); // No existing feedback
        when(feedbackRepository.save(any(FeedbackEntity.class))).thenReturn(feedbackEntity);

        // Call service method
        FeedbackEntity createdFeedback = feedbackService.create(feedbackRequest);

        // Assertions
        assertNotNull(createdFeedback);
        assertEquals("Great place!", createdFeedback.getTitle());
        assertEquals(user, createdFeedback.getUser());
        assertEquals(place, createdFeedback.getPlace());
        assertEquals(9, createdFeedback.getScore());

        // Verify interactions
        verify(feedbackRepository, times(1)).save(any(FeedbackEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenFeedbackAlreadyExists() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(feedbackRepository.findByUserIdAndPlaceId(user.getId(), feedbackRequest.getPlaceId()))
                .thenReturn(Optional.of(feedbackEntity)); // Feedback already exists

        // Expect an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                feedbackService.create(feedbackRequest)
        );

        assertEquals("User has already submitted feedback for this place.", exception.getMessage());

        // Verify `save` is never called
        verify(feedbackRepository, never()).save(any(FeedbackEntity.class));
    }

    @Test
    void shouldReturnFeedbackByPlace() {
        List<FeedbackEntity> feedbackEntities = List.of(feedbackEntity);
        List<FeedbackResponseModel> feedbackResponseModels = List.of(new FeedbackResponseModel());

        when(feedbackRepository.findByPlaceId(place.getId())).thenReturn(feedbackEntities);
        when(feedbackMapper.convert(feedbackEntities)).thenReturn(feedbackResponseModels);

        List<FeedbackResponseModel> result = feedbackService.getFeedbackByPlace(place.getId());

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(feedbackRepository, times(1)).findByPlaceId(place.getId());
        verify(feedbackMapper, times(1)).convert(feedbackEntities);
    }
}
