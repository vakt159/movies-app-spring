package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.HumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;

import java.util.List;
import java.util.Optional;

public interface HumanService {
    HumanBaseEntity findById(Long id) throws HumanNotFoundException;
    List<HumanBaseEntity> findAll();
    HumanBaseEntity save(HumanBaseEntity human) throws HumanNotFoundException;
    void deleteById(Long id) throws HumanNotFoundException;
    HumanBaseEntity update(HumanBaseEntity human, Long id) throws HumanNotFoundException;
    void deleteTVShow(Long humanId,Long TvShowId) throws WatchableNotFoundException, HumanNotFoundException, HasNotValueException;
    void deleteMovie(Long humanId,Long movieId) throws WatchableNotFoundException, HumanNotFoundException, HasNotValueException;
    void addTVShow(Long humanId,Long TvShowId) throws WatchableNotFoundException, HumanNotFoundException, AlreadyHasValueException;
    void addMovie(Long humanId,Long movieId) throws WatchableNotFoundException, HumanNotFoundException, AlreadyHasValueException;
    HumanBaseEntity findByFirstNameAndLastName(String firstName, String Lastname) throws HumanNotFoundException;

}
