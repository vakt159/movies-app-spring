package com.moviesdb.moviesdb.models.superclasses;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchableBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Min(value = 1)
    private Long id;

    @NotNull
    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    private String name;

    @NotNull
    @NotBlank(message = "Description is mandatory")
    @Column(name = "description",length = 1000)
    private String description;

    @NotNull
    @Min(value= 1,message = "AgeRestriction has to be >1")
    @Max(value = 99,message = "AgeRestriction has to be <99")
    @Column(name = "ageRestriction")
    private Short ageRestriction;

    @NotNull
    @Column(name = "duration")
    private Short duration;

    @NotNull
    @Max(value = 10)
    @Column(name = "rating")
    private Byte rating;

}
