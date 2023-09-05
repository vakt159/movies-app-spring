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

    public void addActor(Actor actor)
    {
        this.actors.add(actor);
        actor.getTvShows().add(this);
    }
    public void addDistributor(Distributor distributor)
    {
        this.distributors.add(distributor);
        distributor.getTvShows().add(this);
    }
    public void removeActor(Actor actor)
    {
        this.actors.remove(actor);
        actor.getTvShows().remove(this);
    }
    public void removeDistributor(Distributor distributor)
    {
        this.distributors.remove(distributor);
        distributor.getTvShows().remove(this);
    }

    public void removeDirector()
    {
        setDirector(null);
        director.getTvShows().remove(this);
    }
    public void addDirector(Director director)
    {
        setDirector(director);
        director.getTvShows().add(this);
    }
    public boolean containsActor(Actor actor)
    {
        return getActors().contains(actor);
    }
    public boolean containsDistributor(Distributor distributor)
    {
        return getDistributors().contains(distributor);
    }

}