package com.moviesdb.moviesdb.DTOs.converters;

import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class ActorDTOConverter {
    public static ActorDTO toactorDTO(Actor actor){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long actorId;
        String birthday;

        if(actor.getBirthday()!=null){
            birthday = formatter.format(actor.getBirthday());
        }else birthday = null;
        Set<Long> movies = new HashSet<>();
        Set<Long> tvShows = new HashSet<>();
        if(!actor.getMovies().isEmpty())
        for (Movie movie : actor.getMovies()){
            movies.add(movie.getId());
        }
        if(! actor.getTvShows().isEmpty())
        for (TVShow tvShow : actor.getTvShows()){
            tvShows.add(tvShow.getId());
        }

        return new ActorDTO(actor.getId(),actor.getFirstName(),actor.getLastName(),actor.getBiography(),birthday,movies,tvShows);
    }
}
