package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor
@Table(name =  "directors")
public class Director extends HumanBaseEntity {

    @OneToMany(mappedBy = "director")
    private Set<Movie> movies;

    @OneToMany(mappedBy = "director")
    private Set<TVShow> tvShows;

    public void addMovie(Movie movie)
    {
        this.movies.add(movie);
        movie.setDirector(this);
    }
    public void addTvShow(TVShow tvShow)
    {
        this.tvShows.add(tvShow);
        tvShow.setDirector(this);
    }
    public void removeMovie(Movie movie)
    {
        this.movies.remove(movie);
        movie.setDirector(null);
    }
    public void removeTvShow(TVShow tvShow)
    {
        this.tvShows.remove(tvShow);
        tvShow.setDirector(null);
    }

}
