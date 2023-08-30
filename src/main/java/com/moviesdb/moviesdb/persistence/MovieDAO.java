package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDAO extends JpaRepository<Movie,Long> {
}
