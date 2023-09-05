package com.moviesdb.moviesdb.DTOs.converters;

import com.moviesdb.moviesdb.DTOs.dto.DirectorDTO;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DirectorDTOConverter {
    public static DirectorDTO todirectorDTO(Director director){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Long directorId;
        String birthday;

        if(director.getBirthday()!=null){
            birthday = formatter.format(director.getBirthday());
        }else birthday = null;
        Set<Long> movies = new HashSet<>();
        Set<Long> tvShows = new HashSet<>();
        if(!director.getMovies().isEmpty())
        for (Movie movie : director.getMovies()){
            movies.add(movie.getId());
        }
        if(!director.getTvShows().isEmpty())
        for (TVShow tvShow : director.getTvShows()){
            tvShows.add(tvShow.getId());
        }

        return new DirectorDTO(director.getId(),director.getFirstName(),director.getLastName(),director.getBiography(),birthday,movies,tvShows);
    }
}
