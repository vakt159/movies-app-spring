package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.persistence.ActorDAO;
import com.moviesdb.moviesdb.models.Actor;

import com.moviesdb.moviesdb.services.watchable.TVShowServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ActorServiceImpl implements HumanService<Actor> {
    private final ActorDAO actorDAO;
    private final TVShowServiceImpl tvShowService;


    public ActorServiceImpl(ActorDAO actorDAO, TVShowServiceImpl tvShowService) {
        this.tvShowService = tvShowService;
        this.actorDAO = actorDAO;
    }

    @Override
    public Actor findById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id doesn't exist");
        }
        Optional<Actor> actorOptional = actorDAO.findById(id);
        return actorOptional.orElse(null);
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = actorDAO.findAll();
        if (actors.isEmpty() || actors == null)
            return null;
        return actors;
    }

    @Override
    public Actor save(Actor actor) {
        if (actor == null)
            throw new RuntimeException("Actor doesn't exist");
        return actorDAO.save(actor);
    }

    @Override
    public void deleteById(Long id) {
        Actor deleteActor = findById(id);
        if (deleteActor == null) {
            throw new NoSuchElementException("Actor with id = " + id + " does not exist");
        } else {
            for (Movie movie : deleteActor.getMovies()) {
                movie.getActors().remove(deleteActor);
            }
            for (TVShow tvShow : deleteActor.getTvShows()) {
                tvShow.getActors().remove(deleteActor);
            }
            ///??????
            deleteActor.getMovies().clear();
            deleteActor.getTvShows().clear();
            ///??????
            actorDAO.deleteById(id);
            actorDAO.flush();
        }
    }

    @Override
    public Actor update(Actor actor, Long id) {
        Actor origin_actor = findById(id);
        if (origin_actor == null){
            return null;
        } else {
            origin_actor.setBiography(actor.getBiography());
            origin_actor.setBirthday(actor.getBirthday());
            origin_actor.setFirstName(actor.getFirstName());
            origin_actor.setLastName(actor.getLastName());
            actorDAO.save(origin_actor);
            return origin_actor;
        }
    }


    public void deleteTVShowFromActor(Long actor_id, Long tvShow_id) {
        Actor origin_actor = findById(actor_id);
        TVShow tvShow = tvShowService.findById(tvShow_id);
        if (origin_actor == null){
            throw new NoSuchElementException("Actor with id = " + actor_id + " does not exist");
        } else if(tvShow == null){
            throw new NoSuchElementException("TV show with id = " + tvShow_id + " does not exist");
        } else if(!origin_actor.getTvShows().contains(tvShow)){
            throw new NoSuchElementException("Actor with id = " + actor_id + " have not played it TV show with id = " + tvShow_id);
        }
        else{
            origin_actor.getTvShows().remove(tvShow);
            tvShow.getActors().remove(origin_actor);
            actorDAO.save(origin_actor);
            tvShowService.save(tvShow);
        }
    }

    public Actor saveTVShowToActor(Long actor_id, Long tvShow_id) {
        Actor origin_actor = findById(actor_id);
        TVShow tvShow = tvShowService.findById(tvShow_id);
        if (origin_actor == null){
            throw new NoSuchElementException("Actor with id = " + actor_id + " does not exist");
        } else if(tvShow == null){
            throw new NoSuchElementException("TV show with id = " + tvShow_id + " does not exist");
        }else{
            origin_actor.getTvShows().add(tvShow);
            tvShow.getActors().add(origin_actor);
            actorDAO.save(origin_actor);
            tvShowService.save(tvShow);
            return origin_actor;
        }
    }

    public Actor findActorByFirstNameAndLastName(String firstName, String lastName) {
        Actor foundActor = actorDAO.findActorByFirstNameAndLastName(firstName, lastName);
        if (foundActor == null)
            throw new RuntimeException("Actor doesn't exist");
        return foundActor;
    }

}
