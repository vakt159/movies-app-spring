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
}
