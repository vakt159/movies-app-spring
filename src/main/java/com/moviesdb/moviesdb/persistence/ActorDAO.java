package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActorDAO extends JpaRepository<Actor,Long> {
    Actor findActorByFirstNameAndLastName(String firstName,String lastName);
}
