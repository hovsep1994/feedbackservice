package com.jambit.feedbackservice.repository.entity;

import com.jambit.feedbackservice.constant.PlaceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

}
