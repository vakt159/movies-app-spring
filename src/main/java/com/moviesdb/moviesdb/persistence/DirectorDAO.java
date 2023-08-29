package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorDAO extends JpaRepository<Director,Long> {
    Director findDirectorByFirstNameAndLastName(String firstName, String lastName);

}
