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

    public void addMovie(Movie movie)
    {
        this.movies.add(movie);
        movie.getDistributors().add(this);
    }
    public void addTvShow(TVShow tvShow)
    {
        this.tvShows.add(tvShow);
        tvShow.getDistributors().add(this);
    }
    public void removeMovie(Movie movie)
    {
        this.movies.remove(movie);
        movie.getDistributors().remove(this);
    }
    public void removeTvShow(TVShow tvShow)
    {
        this.tvShows.remove(tvShow);
        tvShow.getDistributors().remove(this);
    }
}
