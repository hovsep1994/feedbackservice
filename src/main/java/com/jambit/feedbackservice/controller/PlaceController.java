package com.jambit.feedbackservice.controller;

import com.jambit.feedbackservice.constant.PlaceType;
import com.jambit.feedbackservice.entity.PlaceEntity;
import com.jambit.feedbackservice.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("{placeType}")
    public ResponseEntity<List<PlaceEntity>> get(@PathVariable PlaceType placeType) {
        return ResponseEntity.ok(placeService.findByPlaceType(placeType));
    }
}
