package com.moviesdb.moviesdb.DTOs.dto;

import com.moviesdb.moviesdb.DTOs.dto.superclasses.WatchableBaseEntityDTO;
import com.moviesdb.moviesdb.models.Director;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TVShowDTO extends WatchableBaseEntityDTO {

    private Set<Long> actors;

    private Integer totalEpisodes;

    private Integer totalSeasons;

    private Long director_id;

    private Set<Long> distributors;

    public TVShowDTO(Long id, String name, String description, Short ageRestriction, Short duration, Byte rating,
                     Set<Long> actors, Integer totalEpisodes, Integer totalSeasons, Long director_id, Set<Long> distributors) {
        super(id, name, description, ageRestriction, duration, rating);
        this.actors = actors;
        this.totalEpisodes = totalEpisodes;
        this.totalSeasons = totalSeasons;
        this.director_id = director_id;
        this.distributors = distributors;
    }
}
