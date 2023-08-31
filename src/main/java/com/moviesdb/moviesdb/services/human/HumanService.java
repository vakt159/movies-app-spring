package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;

import java.util.List;

public interface HumanService<T extends HumanBaseEntity> {
    T findById(Long id);
    List<T> findAll();
    T save(T human);
    void deleteById(Long id);
    T update(T human, Long id);

    void deleteTVShow(Long humanId, Long tvShowId);

    void deleteMovie(Long humanId, Long movieId);

    void addTVShow(Long tvShowId, Long humanId);

    void addMovie(Long movieId, Long humanId);
}
