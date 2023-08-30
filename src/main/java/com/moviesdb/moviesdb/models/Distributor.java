package com.moviesdb.moviesdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Distributor extends NonHumanBaseEntity {

    @ManyToMany(mappedBy = "distributors")
    private Set<Movie> movies;
    @ManyToMany(mappedBy = "distributors")
    private Set<TVShow> tvShows;

}
