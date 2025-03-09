package com.jambit.feedbackservice.service;

import com.jambit.feedbackservice.entity.FeedbackEntity;
import com.jambit.feedbackservice.entity.UserEntity;
import com.jambit.feedbackservice.mapper.FeedbackMapper;
import com.jambit.feedbackservice.model.feedback.FeedbackRequestModel;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import com.jambit.feedbackservice.repository.FeedbackRepository;
import com.jambit.feedbackservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final PlaceService placeService;
    private final UserService userService;
    private final FeedbackMapper feedbackMapper;

    @Transactional
    public FeedbackEntity create(final FeedbackRequestModel feedback) {
        UserEntity user = userService.getCurrentUser();
        if (feedbackRepository.findByUserIdAndPlaceId(user.getId(), feedback.getPlaceId()).isPresent()) {
            throw new RuntimeException("User has already submitted feedback for this place.");
        }

        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .user(user)
                .place(placeService.getPlace(feedback.getPlaceId()))
                .title(feedback.getTitle())
                .comment(feedback.getComment())
                .score(feedback.getScore())
                .createTime(LocalDateTime.now())
                .build();

        return feedbackRepository.save(feedbackEntity);
    }

    @Transactional(readOnly = true)
    public List<FeedbackResponseModel> getFeedbackByPlace(Long placeId) {
        return feedbackMapper.convert(feedbackRepository.findByPlaceId(placeId));
    }
}
