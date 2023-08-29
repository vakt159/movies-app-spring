package com.moviesdb.moviesdb.model.superclasses;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@MappedSuperclass
@Data
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
