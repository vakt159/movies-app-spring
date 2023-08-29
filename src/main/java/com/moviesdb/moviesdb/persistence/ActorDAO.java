package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActorDAO extends JpaRepository<Actor,Long> {
    // Not working
    @Query(value = "SELECT actors.* FROM actors WHERE actors.firstname like %?1% and actors.lastname like %?2%")
    Actor findActorByFirstNameAndLastName(String firstName,String lastName);
}
