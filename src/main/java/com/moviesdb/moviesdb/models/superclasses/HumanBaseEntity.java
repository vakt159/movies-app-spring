package com.moviesdb.moviesdb.models.superclasses;

import jakarta.persistence.*;
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

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "biography",length = 1000)
    private String biography;

    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

}
