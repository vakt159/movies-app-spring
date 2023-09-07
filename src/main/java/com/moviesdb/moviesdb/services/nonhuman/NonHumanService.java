package com.moviesdb.moviesdb.services.nonhuman;

import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.NonHumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;

import java.util.List;

public interface NonHumanService {

    List<NonHumanBaseEntity> findAll();
    void deleteById(Long id) throws NonHumanNotFoundException;
    NonHumanBaseEntity findDistributorById(Long id) throws NonHumanNotFoundException;

    List<NonHumanBaseEntity> findByName(String name);


    NonHumanBaseEntity save(NonHumanBaseEntity nonHuman) throws NonHumanNotFoundException;

    public NonHumanBaseEntity update(Long id, NonHumanBaseEntity distributor) throws NonHumanNotFoundException;

    public void deleteTVShow(Long nonHumanId,Long tvShowId) throws NonHumanNotFoundException, WatchableNotFoundException, HasNotValueException;
    public void deleteMovie(Long nonHumanId,Long movieId) throws NonHumanNotFoundException, WatchableNotFoundException, HasNotValueException;

    public void addTVShow(Long tvShowId, Long nonHumanId) throws NonHumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException;
    public void addMovie(Long movieId, Long nonHumanId) throws NonHumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException;
}
