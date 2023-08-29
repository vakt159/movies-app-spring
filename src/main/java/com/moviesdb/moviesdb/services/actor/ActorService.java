package com.moviesdb.moviesdb.services.actor;

import com.moviesdb.moviesdb.models.Actor;

import java.util.List;

public interface ActorService {

    Actor findById(Long id);
    List<Actor> findAll();
    Actor save(Actor actor);
    Actor findActorByFirstNameAndLastName(String firstName,String lastName);
}
