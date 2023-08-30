package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Distributor extends NonHumanBaseEntity {

    @ManyToMany(mappedBy = "distributors")
    private Set<Movie> movies;

    @ManyToMany(mappedBy = "distributors")
    private Set<TVShow> tvShows;

}
