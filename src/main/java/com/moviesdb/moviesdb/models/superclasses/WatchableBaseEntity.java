package com.moviesdb.moviesdb.models.superclasses;


import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchableBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 1000)
    private String description;

    @Column(name = "ageRestriction")
    private Short ageRestriction;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "rating")
    private Byte rating;

}
