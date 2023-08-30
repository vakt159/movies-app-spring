package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "tvShow")
public class TVShow extends WatchableBaseEntity {

    @ManyToMany
    @JoinTable(name = "tvShow_actor",
            joinColumns = @JoinColumn(name = "tvShow_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @Column(name = "totalEpisodes")
    private int totalEpisodes;

    @Column(name = "totalSeasons")
    private int totalSeasons;

    @ManyToOne
    @JoinColumn(name = "director_id",nullable = true)
    private Director director;

    @ManyToMany
    @JoinTable(name = "tvShow_distributor",
            joinColumns = @JoinColumn(name = "tvShow_id"),
            inverseJoinColumns = @JoinColumn(name = "distributor_id"))
    private Set<Distributor> distributors;

}