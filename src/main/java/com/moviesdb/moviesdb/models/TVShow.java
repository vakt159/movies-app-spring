package com.moviesdb.moviesdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tvShow")
public class TVShow extends WatchableBaseEntity {

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "tvShow_actor",
            joinColumns = @JoinColumn(name = "tvShow_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @Column(name = "totalEpisodes")
    private Integer totalEpisodes;

    @Column(name = "totalSeasons")
    private Integer totalSeasons;

    @ManyToOne
    @JoinColumn(name = "director_id",nullable = true)
    private Director director;

    @ManyToMany
    @JoinTable(name = "tvShow_distributor",
            joinColumns = @JoinColumn(name = "tvShow_id"),
            inverseJoinColumns = @JoinColumn(name = "distributor_id"))
    private Set<Distributor> distributors;

}