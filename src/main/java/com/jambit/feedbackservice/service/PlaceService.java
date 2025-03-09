package com.jambit.feedbackservice.service;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.entity.PlaceEntity;
import com.jambit.feedbackservice.error.EntityNotFoundException;
import com.jambit.feedbackservice.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceEntity getPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
    }

    public List<PlaceEntity> findAll() {
        return placeRepository.findAll();
    }
}
