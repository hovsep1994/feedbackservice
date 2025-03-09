package com.jambit.feedbackservice.model.feedback;

import com.jambit.feedbackservice.constant.PlaceType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponseModel {

    private Long userId;

    private Long placeId;

    private PlaceType placeType;

    private String title;

    private String comment;

    private Integer score;

}
