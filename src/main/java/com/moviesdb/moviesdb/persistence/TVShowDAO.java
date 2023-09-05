package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TVShowDAO extends JpaRepository<TVShow,Long> {
    @Query("SELECT t FROM TVShow t where t.name LIKE %?1%")
    List<TVShow> findByName(String name);

}
