package com.moviesdb.moviesdb.models.superclasses;


import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class WatchableBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 1000)
    private String description;

    @Column(name = "ageRestriction")
    private short ageRestriction;

    @Column(name = "duration")
    private int duration;

    @Column(name = "rating")
    private byte rating;

}
