package com.moviesdb.moviesdb.services.director;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Director;

import java.util.List;

public interface DirectorService {

    Director findById(Long id);
    List<Director> findAll();
    Director save(Director director);
    Director findDirectorByFirstNameAndLastName(String firstName, String lastName);
}
