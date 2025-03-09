package com.jambit.feedbackservice.repository;

import com.jambit.feedbackservice.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<PlaceEntity, Long> {

}
