package com.moviesdb.moviesdb.DTOs.converters;

import com.moviesdb.moviesdb.DTOs.dto.TVShowDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.TVShow;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class TVShowDTOConverter {
    public static TVShowDTO totvShowDTO(TVShow tvShow){
        Set<Long> actors = new HashSet<>();
        Set<Long> distributors = new HashSet<>();
        if( !tvShow.getActors().isEmpty())
        for (Actor actor : tvShow.getActors()){
            actors.add(actor.getId());
        }
        if( !tvShow.getDistributors().isEmpty())
        for (Distributor distributor : tvShow.getDistributors()){
            distributors.add(distributor.getId());
        }

        return new TVShowDTO(tvShow.getId(),tvShow.getName(),tvShow.getDescription(),tvShow.getAgeRestriction(),tvShow.getDuration(),tvShow.getRating(),actors,tvShow.getTotalEpisodes(), tvShow.getTotalSeasons(), tvShow.getDirector().getId(),distributors);
    }
}
