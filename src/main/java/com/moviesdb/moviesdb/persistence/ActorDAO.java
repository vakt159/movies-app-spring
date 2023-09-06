package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface ActorDAO extends JpaRepository<Actor,Long> {
    // Not working

    @Query("from Actor a where a.firstName = :firstName AND a.lastName = :lastName")
    Optional<Actor> findActorByFirstNameAndLastName(@Param("firstName") String firstName,
                                                    @Param("lastName") String lastName);
}
