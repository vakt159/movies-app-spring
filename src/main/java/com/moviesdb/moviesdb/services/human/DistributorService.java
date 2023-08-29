package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Distributor;

import java.util.List;

public interface DistributorService {

    List<Distributor> findAll();

   void deleteDistributorById(Long id);

    Distributor findById(Long id);

    Distributor findByName(String name);


    Distributor save(Distributor distributor);



}
