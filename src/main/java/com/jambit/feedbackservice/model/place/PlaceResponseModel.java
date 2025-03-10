package com.jambit.feedbackservice.model.place;

import com.jambit.feedbackservice.constant.PlaceType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceResponseModel {
    private Long id;
    private String name;
    private PlaceType placeType;
}
