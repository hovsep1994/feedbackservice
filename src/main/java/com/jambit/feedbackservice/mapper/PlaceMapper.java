package com.jambit.feedbackservice.mapper;

import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceMapper {

    public PlaceResponseModel convert(PlaceEntity placeEntity) {
        PlaceResponseModel placeResponseModel = new PlaceResponseModel();
        placeResponseModel.setId(placeEntity.getId());
        placeResponseModel.setName(placeEntity.getName());
        placeResponseModel.setPlaceType(placeEntity.getPlaceType());
        return placeResponseModel;
    }

    public List<PlaceResponseModel> convert(List<PlaceEntity> entities) {
        return entities.stream().map(this::convert).toList();
    }
}
