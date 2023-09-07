package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.HumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.persistence.ActorDAO;
import com.moviesdb.moviesdb.models.Actor;

import com.moviesdb.moviesdb.services.watchable.MovieServiceImpl;
import com.moviesdb.moviesdb.services.watchable.TVShowServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ActorServiceImpl implements HumanService {
    private final ActorDAO actorDAO;
    private final TVShowServiceImpl tvShowService;
    private final MovieServiceImpl movieService;


    public ActorServiceImpl(ActorDAO actorDAO, TVShowServiceImpl tvShowService, MovieServiceImpl movieService) {
        this.tvShowService = tvShowService;
        this.actorDAO = actorDAO;
        this.movieService = movieService;
    }

    @Override
    public Actor findById(Long id) throws HumanNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id doesn't exist");
        }
        Optional<Actor> actorOptional = actorDAO.findById(id);
        if (actorOptional.isPresent())
            return actorOptional.get();
        else throw new HumanNotFoundException("Actor with id= " + id + " doesn't exist");
    }

    @Override
    public List<HumanBaseEntity> findAll() {
        List<Actor> actors = actorDAO.findAll();
        if (actors == null || actors.isEmpty()) {
            return new ArrayList<>();
        }
        List<HumanBaseEntity> humanActors = new ArrayList<>(actors);
        return humanActors;
    }

    @Override
    public HumanBaseEntity save(HumanBaseEntity actor) {
        if (actor == null)
            throw new IllegalArgumentException("Actor can't be null");
        return actorDAO.save((Actor) actor);
    }

    @Override
    public void deleteById(Long id) throws HumanNotFoundException {
        Actor deleteActor = findById(id);
        if (deleteActor == null) {
            throw new HumanNotFoundException("Actor with id = " + id + " does not exist");
        } else {
            for (Movie movie : deleteActor.getMovies()) {
                deleteActor.removeMovie(movie);
            }
            for (TVShow tvShow : deleteActor.getTvShows()) {
                deleteActor.removeTvShow(tvShow);
            }
            actorDAO.deleteById(id);
            actorDAO.flush();
        }
    }

    @Override
    public HumanBaseEntity update(HumanBaseEntity actor, Long id) throws HumanNotFoundException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("Id can't be null or <0");
        Actor origin_actor = findById(id);
        if (origin_actor == null) {
            throw new HumanNotFoundException("Actor with id = " + id + " does not exist");
        } else {
            origin_actor.setBiography(actor.getBiography());
            origin_actor.setBirthday(actor.getBirthday());
            origin_actor.setFirstName(actor.getFirstName());
            origin_actor.setLastName(actor.getLastName());
            actorDAO.save(origin_actor);
            return origin_actor;
        }
    }

    @Override
    public void deleteTVShow(Long actor_id, Long tvShow_id) throws WatchableNotFoundException, HumanNotFoundException, HasNotValueException {
        if (actor_id <= 0 || actor_id == null)
            throw new IllegalArgumentException("Actor's id can't be null or <0");
        if (tvShow_id <= 0 || tvShow_id == null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");


        Actor origin_actor = findById(actor_id);
        TVShow tvShow = tvShowService.findById(tvShow_id);
        if (origin_actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actor_id + " does not exist");
        } else if (tvShow == null) {
            throw new WatchableNotFoundException("TV show with id = " + tvShow_id + " does not exist");
        } else if (!origin_actor.containsTVShow(tvShow)) {
            throw new HasNotValueException("Actor with id = " + actor_id + " have not played it TV show with id = " + tvShow_id);
        } else {
            origin_actor.removeTvShow(tvShow);
            actorDAO.save(origin_actor);
            tvShowService.save(tvShow);
        }
    }

    @Override
    public void deleteMovie(Long actor_id, Long movie_id) throws WatchableNotFoundException, HumanNotFoundException, HasNotValueException {
        if (actor_id <= 0 || actor_id == null)
            throw new IllegalArgumentException("Actor's id can't be null or <0");
        if (movie_id <= 0 || movie_id == null)
            throw new IllegalArgumentException("Movie's id can't be null or <0");


        Actor origin_actor = findById(actor_id);
        Movie movie = movieService.findById(movie_id);
        if (origin_actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actor_id + " does not exist");
        } else if (movie == null) {
            throw new WatchableNotFoundException("Movie with id = " + movie_id + " does not exist");
        } else if (!origin_actor.containsMovie(movie)) {
            throw new HasNotValueException("Actor with id = " + actor_id + " have not played it Movie with id = " + movie_id);
        } else {
            origin_actor.removeMovie(movie);
            actorDAO.save(origin_actor);
            movieService.save(movie);
        }
    }

    @Override
    public void addTVShow(Long actor_id, Long tvShow_id) throws WatchableNotFoundException, HumanNotFoundException, AlreadyHasValueException {
        if (actor_id <= 0 || actor_id == null)
            throw new IllegalArgumentException("Actor's id can't be null or <0");
        if (tvShow_id <= 0 || tvShow_id == null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Actor origin_actor = findById(actor_id);
        TVShow tvShow = tvShowService.findById(tvShow_id);
        if (origin_actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actor_id + " does not exist");
        } else if (tvShow == null) {
            throw new WatchableNotFoundException("TV show with id = " + tvShow_id + " does not exist");
        } else if (origin_actor.containsTVShow(tvShow))
            throw new AlreadyHasValueException("This tvShow already added to actor");
        else {
            origin_actor.addTvShow(tvShow);
            actorDAO.save(origin_actor);
            tvShowService.save(tvShow);
        }
    }

    @Override
    public void addMovie(Long actor_id, Long movie_id) throws WatchableNotFoundException, HumanNotFoundException, AlreadyHasValueException {
        if (actor_id <= 0 || actor_id == null)
            throw new IllegalArgumentException("Actor's id can't be null or <0");
        if (movie_id <= 0 || movie_id == null)
            throw new IllegalArgumentException("Movie's id can't be null or <0");


        Actor origin_actor = findById(actor_id);
        Movie movie = movieService.findById(movie_id);
        if (origin_actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actor_id + " does not exist");
        } else if (movie == null) {
            throw new WatchableNotFoundException("Movie with id = " + movie_id + " does not exist");
        } else if (origin_actor.containsMovie(movie))
            throw new AlreadyHasValueException("This tvShow already added to actor");
        else {
            origin_actor.addMovie(movie);
            actorDAO.save(origin_actor);
            movieService.save(movie);
        }
    }

    public Actor findByFirstNameAndLastName(String firstName, String lastName) throws HumanNotFoundException {
        Optional<Actor> foundActor = actorDAO.findActorByFirstNameAndLastName(firstName, lastName);
        if (foundActor.isEmpty())
            throw new HumanNotFoundException("Actor with first name = " + firstName +
                    "and last name = " + lastName + " does not exist");
        return foundActor.get();
    }

}
