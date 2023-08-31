package com.moviesdb.moviesdb.models.superclasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Firstname is mandatory")
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @NotBlank(message = "Lastname is mandatory")
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @NotBlank(message = "Biography is mandatory")
    @Column(name = "biography",length = 1000)
    private String biography;

    @NotNull
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

}
