package com.moviesdb.moviesdb.models;

import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name =  "directors")
public class Director extends HumanBaseEntity {

    @OneToMany(mappedBy = "director")
    private Set<Movie> movies;

    @OneToMany(mappedBy = "director")
    private Set<TVShow> tvShows;
}
