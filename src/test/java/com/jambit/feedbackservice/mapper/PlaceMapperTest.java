package com.jambit.feedbackservice.mapper;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlaceMapperTest {

    private PlaceMapper placeMapper;
    private PlaceEntity placeEntity;

    @BeforeEach
    void setUp() {
        placeMapper = new PlaceMapper();

        placeEntity = PlaceEntity.builder()
                .id(1L)
                .name("Pizza Palace")
                .placeType(PlaceType.RESTAURANT)
                .build();
    }

    @Test
    void shouldConvertPlaceEntityToResponseModel() {
        PlaceResponseModel model = placeMapper.convert(placeEntity);

        // Assertions
        assertNotNull(model);
        assertEquals(1L, model.getId());
        assertEquals("Pizza Palace", model.getName());
        assertEquals(PlaceType.RESTAURANT, model.getPlaceType());
    }

    @Test
    void shouldConvertListOfPlaceEntities() {
        List<PlaceEntity> placeEntities = List.of(placeEntity);
        List<PlaceResponseModel> responseModels = placeMapper.convert(placeEntities);

        // Assertions
        assertNotNull(responseModels);
        assertEquals(1, responseModels.size());
        assertEquals("Pizza Palace", responseModels.get(0).getName());
        assertEquals(PlaceType.RESTAURANT, responseModels.get(0).getPlaceType());
    }
}
