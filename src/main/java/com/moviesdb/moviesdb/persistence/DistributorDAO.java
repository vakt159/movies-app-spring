package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DistributorDAO extends JpaRepository<Distributor, Long> {

    @Query("SELECT t FROM Distributor t where t.name LIKE %?1%")
    List<Distributor> findDistributorByName(String name);

}
