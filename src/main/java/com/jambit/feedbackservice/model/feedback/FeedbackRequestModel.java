package com.jambit.feedbackservice.model.feedback;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackRequestModel {

    @NotNull
    private Long placeId;

    @Size(max = 50)
    private String title;

    @Size(max = 1000)
    private String comment;

    @Min(0)
    @Max(10)
    private Integer score;

}
