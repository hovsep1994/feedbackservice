package com.jambit.feedbackservice.controller;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.service.place.PlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PlaceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private PlaceController placeController;

    private PlaceResponseModel placeResponse1;
    private PlaceResponseModel placeResponse2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(placeController).build();

        placeResponse1 = new PlaceResponseModel();
        placeResponse1.setId(1L);
        placeResponse1.setName("Pizza Palace");
        placeResponse1.setPlaceType(PlaceType.RESTAURANT);

        placeResponse2 = new PlaceResponseModel();
        placeResponse2.setId(2L);
        placeResponse2.setName("Tech Store");
        placeResponse2.setPlaceType(PlaceType.SHOP);
    }

    @Test
    void shouldReturnListOfPlaces() throws Exception {
        List<PlaceResponseModel> places = List.of(placeResponse1, placeResponse2);

        when(placeService.findAll()).thenReturn(places);

        mockMvc.perform(get("/api/places")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pizza Palace"))
                .andExpect(jsonPath("$[1].name").value("Tech Store"));

        verify(placeService, times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoPlacesExist() throws Exception {
        when(placeService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/places")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        verify(placeService, times(1)).findAll();
    }
}
