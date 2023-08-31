package com.moviesdb.moviesdb.models.superclasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NonHumanBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank(message = "Description is mandatory")
    @Column(name = "description",length = 1000)
    private String description;
}
