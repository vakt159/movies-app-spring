package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;

import java.util.List;

public interface HumanService {
    HumanBaseEntity findById(Long id);
    List<HumanBaseEntity> findAll();
    HumanBaseEntity save(HumanBaseEntity human);
    void deleteById(Long id);
    HumanBaseEntity update(HumanBaseEntity human, Long id);
    void deleteTVShow(Long humanId,Long TvShowId);
    void deleteMovie(Long humanId,Long movieId);
    void addTVShow(Long humanId,Long TvShowId);
    void addMovie(Long humanId,Long movieId);

}
