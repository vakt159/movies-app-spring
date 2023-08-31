package com.moviesdb.moviesdb.services.nonhuman;

import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;

import java.util.List;

public interface NonHumanService {

    List<NonHumanBaseEntity> findAll();

   void deleteDistributorById(Long id);

    NonHumanBaseEntity findDistributorById(Long id);

    NonHumanBaseEntity findByName(String name);


    NonHumanBaseEntity save(NonHumanBaseEntity nonHuman);

    public NonHumanBaseEntity update(Long id, NonHumanBaseEntity distributor);

    public void deleteTVShow(Long nonHumanId,Long tvShowId);
    public void deleteMovie(Long nonHumanId,Long movieId);

    public void addTVShow(Long tvShowId, Long nonHumanId);
    public void addMovie(Long movieId, Long nonHumanId);
}
