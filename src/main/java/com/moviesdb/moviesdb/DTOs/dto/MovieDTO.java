package com.moviesdb.moviesdb.DTOs.dto;

import com.moviesdb.moviesdb.DTOs.dto.superclasses.WatchableBaseEntityDTO;
import com.moviesdb.moviesdb.models.Director;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO extends WatchableBaseEntityDTO {

    private Set<Long> actors;

    private Long director_id;

    private Set<Long> distributors;

    public MovieDTO(Long id, String name, String description, Short ageRestriction, Integer duration, Byte rating, Set<Long> actors, Long director_id, Set<Long> distributors) {
        super(id, name, description, ageRestriction, duration, rating);
        this.actors = actors;
        this.director_id = director_id;
        this.distributors = distributors;
    }
}