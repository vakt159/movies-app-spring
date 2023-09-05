package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie extends WatchableBaseEntity {

    @ManyToMany
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;

    @ManyToOne
    @JoinColumn(name = "director_id",nullable = true)
    private Director director;

    @ManyToMany
    @JoinTable(name = "movie_distributor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "distributor_id"))
    private Set<Distributor> distributors;


    public void addActor(Actor actor)
    {
        this.actors.add(actor);
        actor.getMovies().add(this);
    }
    public void addDistributor(Distributor distributor)
    {
        this.distributors.add(distributor);
        distributor.getMovies().add(this);
    }
    public void removeActor(Actor actor)
    {
        this.actors.remove(actor);
        actor.getMovies().remove(this);
    }
    public void removeDistributor(Distributor distributor)
    {
        this.distributors.remove(distributor);
        distributor.getMovies().remove(this);
    }
    public void removeDirector()
    {
        Director director=getDirector();
        director.getMovies().remove(this);
        setDirector(null);
    }
    public void addDirector(Director director)
    {
        setDirector(director);
        director.getMovies().add(this);
    }
}
