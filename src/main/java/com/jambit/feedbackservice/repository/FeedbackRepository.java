package com.jambit.feedbackservice.repository;

import com.jambit.feedbackservice.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    Optional<FeedbackEntity> findByUserIdAndPlaceId(Long userId, Long restaurantId);

    List<FeedbackEntity> findByPlaceId(Long placeId);
}
