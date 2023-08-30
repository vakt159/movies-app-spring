package com.moviesdb.moviesdb.DTOs.converters;

import com.moviesdb.moviesdb.DTOs.dto.DistributorDTO;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DistributorDTOConverter {
    public static DistributorDTO todistributorDTO(Distributor distributor){
        Long distributorId;
        String birthday;

        Set<Long> movies = new HashSet<>();
        Set<Long> tvShows = new HashSet<>();
        for (Movie movie : distributor.getMovies()){
            movies.add(movie.getId());
        }
        for (TVShow tvShow : distributor.getTvShows()){
            tvShows.add(tvShow.getId());
        }

        return new DistributorDTO(distributor.getId(),distributor.getName(),distributor.getDescription(),movies,tvShows);
    }
}
