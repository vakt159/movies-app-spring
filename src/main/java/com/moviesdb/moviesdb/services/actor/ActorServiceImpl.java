package com.moviesdb.moviesdb.services.actor;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.persistence.ActorDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorDAO actorDAO;

    public ActorServiceImpl(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    @Override
    public Actor findById(Long id) {
        if(id==null || id<=0) {
            throw new RuntimeException("Id doesn't exist");
        }
            Optional<Actor> actorOptional=actorDAO.findById(id);
        return actorOptional.orElse(null);
    }
    @Override
    public List<Actor> findAll() {
        List<Actor> actors=actorDAO.findAll();
        if(actors.isEmpty()||actors==null)
            return null;
        return actors;
    }

    @Override
    public Actor save(Actor actor) {
        if(actor==null)
            throw new RuntimeException("Actor doesn't exist");
        return actorDAO.save(actor);
    }

    @Override
    public Actor findActorByFirstNameAndLastName(String firstName, String lastName)
    {
        Actor foundActor = actorDAO.findActorByFirstNameAndLastName(firstName, lastName);
        if(foundActor==null)
            throw new RuntimeException("Actor doesn't exist");
        return foundActor;
    }

}
