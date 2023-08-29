package com.moviesdb.moviesdb.model.superclasses;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class NonHumanBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 1000)
    private String description;
}
