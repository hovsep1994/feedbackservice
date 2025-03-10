package com.jambit.feedbackservice.controller;

import com.jambit.feedbackservice.model.place.PlaceResponseModel;
import com.jambit.feedbackservice.service.place.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceResponseModel>> getAllPlaces() {
        return ResponseEntity.ok(placeService.findAll());
    }
}
