package com.moviesdb.moviesdb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actors")
public class Actor extends HumanBaseEntity {
   @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies;
//    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    private Set<TVShow> tvShows;
    public void addMovie(Movie movie)
    {
        this.movies.add(movie);
        movie.getActors().add(this);
    }
    public void addTvShow(TVShow tvShow)
    {
        this.tvShows.add(tvShow);
        tvShow.getActors().add(this);
    }
    public void removeMovie(Movie movie)
    {
        this.movies.remove(movie);
        movie.getActors().remove(this);
    }
    public void removeTvShow(TVShow tvShow)
    {
        this.tvShows.remove(tvShow);
        tvShow.getActors().remove(this);
    }

}
