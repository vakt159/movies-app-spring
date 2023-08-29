package com.moviesdb.moviesdb.model;

import com.moviesdb.moviesdb.model.superclasses.WatchableBaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "movies")
public class Movie extends WatchableBaseEntity {

    @ManyToMany
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @ManyToOne
    @JoinColumn(name = "director_id",nullable = false)
    private Director director;


    @ManyToMany
    @JoinTable(name = "movie_distributor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "distributor_id"))
    private Set<Distributor> distributors;
}
