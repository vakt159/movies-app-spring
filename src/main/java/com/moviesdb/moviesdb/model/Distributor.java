package com.moviesdb.moviesdb.model;

import com.moviesdb.moviesdb.model.superclasses.NonHumanBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Distributor extends NonHumanBaseEntity {

    @ManyToMany(mappedBy = "distributors")
    private Set<Movie> movie;

    @ManyToMany(mappedBy = "distributors")
    private Set<TVShow> tvShows;

}
