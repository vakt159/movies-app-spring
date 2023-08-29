package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "actors")
public class Actor extends HumanBaseEntity {

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
    @ManyToMany(mappedBy = "actors")
    private Set<TVShow> tvShows;
}
