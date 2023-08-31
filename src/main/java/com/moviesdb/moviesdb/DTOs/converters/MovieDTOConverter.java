package com.moviesdb.moviesdb.DTOs.converters;

import com.moviesdb.moviesdb.DTOs.dto.MovieDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class MovieDTOConverter {
    public static MovieDTO tomovieDTO(Movie movie){
        Set<Long> actors = new HashSet<>();
        Set<Long> distributors = new HashSet<>();
        for (Actor actor : movie.getActors()){
            actors.add(actor.getId());
        }
        for (Distributor distributor : movie.getDistributors()){
            distributors.add(distributor.getId());
        }

        return new MovieDTO(movie.getId(),movie.getName(),movie.getDescription(),movie.getAgeRestriction(),movie.getDuration(),movie.getRating(),actors,movie.getDirector().getId(),distributors);
    }
}
