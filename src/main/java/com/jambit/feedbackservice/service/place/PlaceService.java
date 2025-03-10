package com.jambit.feedbackservice.service.place;

import com.jambit.feedbackservice.mapper.PlaceMapper;
import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import com.jambit.feedbackservice.error.EntityNotFoundException;
import com.jambit.feedbackservice.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;

    public PlaceEntity getPlace(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place not found"));
    }

    public List<PlaceResponseModel> findAll() {
        return placeMapper.convert(placeRepository.findAll());
    }
}
