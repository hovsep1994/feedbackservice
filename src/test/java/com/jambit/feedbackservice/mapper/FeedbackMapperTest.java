package com.jambit.feedbackservice.mapper;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.model.feedback.FeedbackResponseModel;
import com.jambit.feedbackservice.repository.entity.FeedbackEntity;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import com.jambit.feedbackservice.repository.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FeedbackMapperTest {

    private FeedbackMapper feedbackMapper;

    private FeedbackEntity feedbackEntity;
    private UserEntity user;
    private PlaceEntity place;

    @BeforeEach
    void setUp() {
        feedbackMapper = new FeedbackMapper();

        user = UserEntity.builder()
                .id(1L)
                .email("user@example.com")
                .build();

        place = PlaceEntity.builder()
                .id(1L)
                .name("Pizza Palace")
                .placeType(PlaceType.RESTAURANT)
                .build();

        feedbackEntity = FeedbackEntity.builder()
                .id(1L)
                .user(user)
                .place(place)
                .title("Great place!")
                .comment("The food was amazing.")
                .score(9)
                .build();
    }

    @Test
    void shouldConvertFeedbackEntityToResponseModel() {
        FeedbackResponseModel model = feedbackMapper.convert(feedbackEntity);

        // Assertions
        assertNotNull(model);
        assertEquals("Great place!", model.getTitle());
        assertEquals("The food was amazing.", model.getComment());
        assertEquals(9, model.getScore());
        assertEquals(user.getId(), model.getUserId());
        assertEquals(place.getId(), model.getPlaceId());
        assertEquals(PlaceType.RESTAURANT, model.getPlaceType());
    }

    @Test
    void shouldConvertListOfFeedbackEntities() {
        List<FeedbackEntity> feedbackEntities = List.of(feedbackEntity);
        List<FeedbackResponseModel> responseModels = feedbackMapper.convert(feedbackEntities);

        // Assertions
        assertNotNull(responseModels);
        assertEquals(1, responseModels.size());
        assertEquals("Great place!", responseModels.get(0).getTitle());
    }
}
