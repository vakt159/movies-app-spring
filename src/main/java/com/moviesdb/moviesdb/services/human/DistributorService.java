package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;

import java.util.List;

public interface DistributorService {

    List<Distributor> findAll();

   void deleteDistributorById(Long id);

    Distributor findDistributorById(Long id);

    Distributor findByName(String name);


    Distributor save(Distributor distributor);

    public Distributor update(Long id, Distributor distributor);

    public void deleteTVShow(Long distributorId,Long tvShowId);
    public void deleteMovie(Long distributorId,Long movieId);

    public void addTVShow(Long tvShowId, Long distributorId);
    public void addMovie(Long movieId, Long distributorId);
}
