package com.jambit.feedbackservice.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "place_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private PlaceEntity place;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String comment;

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 10)
    private Integer score;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
}
