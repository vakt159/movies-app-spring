package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistributorDAO extends JpaRepository<Distributor, Long> {

    Optional<Distributor> findDistributorByName(String name);

  //  Distributor deleteById(Long id);

}
