package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;

import java.util.List;
import java.util.Optional;

public interface HumanService {
    HumanBaseEntity findById(Long id);
    List<HumanBaseEntity> findAll();
    HumanBaseEntity save(HumanBaseEntity human);
    void deleteById(Long id);
    HumanBaseEntity update(HumanBaseEntity human, Long id);
    void deleteTVShow(Long humanId,Long TvShowId) throws WatchableNotFoundException;
    void deleteMovie(Long humanId,Long movieId) throws WatchableNotFoundException;
    void addTVShow(Long humanId,Long TvShowId) throws WatchableNotFoundException;
    void addMovie(Long humanId,Long movieId) throws WatchableNotFoundException;
    HumanBaseEntity findByFirstNameAndLastName(String firstName, String Lastname);

}
