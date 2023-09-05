package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie,Long> {
    @Query("SELECT t FROM Movie t where t.name LIKE %?1%")
    List<Movie> findByName(String name);
}
