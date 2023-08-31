package com.moviesdb.moviesdb.DTOs.dto;

import com.moviesdb.moviesdb.DTOs.dto.superclasses.NonHumanBaseEntityDTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistributorDTO extends NonHumanBaseEntityDTO {


    private Set<Long> movies;

    private Set<Long> tvShows;

    public DistributorDTO(Long id, String name, String description, Set<Long> movies, Set<Long> tvShows) {
        super(id, name, description);
        this.movies = movies;
        this.tvShows = tvShows;
    }
}
