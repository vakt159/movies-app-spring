package com.moviesdb.moviesdb.DTOs.dto;


import com.moviesdb.moviesdb.DTOs.dto.superclasses.HumanBaseEntityDTO;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorDTO extends HumanBaseEntityDTO {
    private Set<Long> movies;
    private Set<Long> tvShows;

    public ActorDTO(Long id, String firstName, String lastName, String biography, String birthday, Set<Long> movies, Set<Long> tvShows) {
        super(id, firstName, lastName, biography, birthday);
        this.movies = movies;
        this.tvShows = tvShows;
    }
}

