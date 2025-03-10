package com.jambit.feedbackservice.service.place;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.error.EntityNotFoundException;
import com.jambit.feedbackservice.mapper.PlaceMapper;
import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.repository.PlaceRepository;
import com.jambit.feedbackservice.repository.entity.PlaceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceMapper placeMapper;

    @InjectMocks
    private PlaceService placeService;

    private PlaceEntity place;

    @BeforeEach
    void setUp() {
        place = PlaceEntity.builder()
                .id(1L)
                .name("Pizza Palace")
                .placeType(PlaceType.RESTAURANT)
                .build();
    }

    @Test
    void shouldReturnPlaceWhenExists() {
        // Mock repository behavior
        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));

        // Call service method
        PlaceEntity foundPlace = placeService.getPlace(1L);

        // Assertions
        assertNotNull(foundPlace);
        assertEquals(1L, foundPlace.getId());
        assertEquals("Pizza Palace", foundPlace.getName());
        assertEquals(PlaceType.RESTAURANT, foundPlace.getPlaceType());

        // Verify repository interaction
        verify(placeRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenPlaceNotFound() {
        // Mock repository behavior to return empty
        when(placeRepository.findById(2L)).thenReturn(Optional.empty());

        // Expect EntityNotFoundException
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                placeService.getPlace(2L)
        );

        assertEquals("Place not found", exception.getMessage());

        // Verify repository interaction
        verify(placeRepository, times(1)).findById(2L);
    }

    @Test
    void shouldReturnAllPlaces() {
        PlaceEntity place1 = new PlaceEntity(2L, "Burger Heaven", PlaceType.RESTAURANT);
        List<PlaceEntity> places = List.of(
                place,
                place1
        );
        List<PlaceResponseModel> responseModels = List.of(
                new PlaceResponseModel(place.getId(), place.getName(), place.getPlaceType()),
                new PlaceResponseModel(place1.getId(), place1.getName(), place1.getPlaceType())
        );

        // Mock repository behavior
        when(placeRepository.findAll()).thenReturn(places);
        when(placeMapper.convert(places)).thenReturn(responseModels);

        // Call service method
        List<PlaceResponseModel> result = placeService.findAll();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(responseModels, result);

        // Verify repository interaction
        verify(placeRepository, times(1)).findAll();
    }
}
