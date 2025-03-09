package com.jambit.feedbackservice.mapper;

import com.jambit.feedbackservice.entity.FeedbackEntity;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {

    public FeedbackResponseModel convert(FeedbackEntity feedbackEntity) {
        FeedbackResponseModel model = new FeedbackResponseModel();
        model.setComment(feedbackEntity.getComment());
        model.setScore(feedbackEntity.getScore());
        model.setTitle(feedbackEntity.getTitle());
        model.setUserId(feedbackEntity.getUser().getId());
        model.setPlaceId(feedbackEntity.getPlace().getId());
        model.setPlaceType(feedbackEntity.getPlace().getPlaceType());
        return model;
    }

    public List<FeedbackResponseModel> convert(List<FeedbackEntity> feedbackEntities) {
        return feedbackEntities.stream().map(this::convert).collect(Collectors.toList());
    }
}
