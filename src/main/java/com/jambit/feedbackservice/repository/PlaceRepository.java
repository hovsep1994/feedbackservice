package com.jambit.feedbackservice.repository;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {
    List<PlaceEntity> findByPlaceType(PlaceType placeType);
}
