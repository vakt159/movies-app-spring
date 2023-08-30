package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVShowDAO extends JpaRepository<TVShow,Long> {
}
