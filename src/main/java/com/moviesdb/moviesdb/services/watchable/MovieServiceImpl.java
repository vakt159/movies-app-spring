package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.exceptions.*;
import com.moviesdb.moviesdb.models.*;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.persistence.ActorDAO;
import com.moviesdb.moviesdb.persistence.DirectorDAO;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieServiceImpl implements WatchableService {
    private final MovieDAO movieDAO;
    private final ActorDAO actorDAO;
    private final DistributorDAO distributorDAO;
    private final DirectorDAO directorDAO;


    public MovieServiceImpl(MovieDAO movieDAO, ActorDAO actorDAO, DistributorDAO distributorDAO, DirectorDAO directorDAO) {
        this.movieDAO = movieDAO;
        this.actorDAO = actorDAO;
        this.distributorDAO = distributorDAO;
        this.directorDAO = directorDAO;
    }

    @Override
    public Movie findById(Long id) throws WatchableNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id doesn't exist");
        }
        Optional<Movie> movieOptional = movieDAO.findById(id);
        if (movieOptional.isPresent())
            return movieOptional.get();
        else
            throw new WatchableNotFoundException("Movie with id =" + id + "doesn't exist");
    }

    @Override
    public List<WatchableBaseEntity> findAll() {
        List<Movie> movies = movieDAO.findAll();
        if (movies == null || movies.isEmpty()) {
            return new ArrayList<>();
        }
        List<WatchableBaseEntity> watchableMovies = new ArrayList<>(movies);
        return watchableMovies;
    }

    @Override
    public WatchableBaseEntity save(WatchableBaseEntity movie) throws WatchableNotFoundException {
        if (movie == null)
            throw new WatchableNotFoundException("Movie can't be null");
        return movieDAO.save((Movie) movie);
    }

    @Override
    public WatchableBaseEntity update(WatchableBaseEntity watchable, Long id) throws WatchableNotFoundException {
        Movie movie = (Movie) watchable;
        if (movie == null) {
            throw new WatchableNotFoundException("Passed movie can't be null");
        } else if (id == null) {
            throw new IllegalArgumentException("Passed id of movie is null");
        }

        Movie movieToUpdate = movieDAO.findById(id).orElse(null);
        if (movieToUpdate == null)
            throw new WatchableNotFoundException("Movie to update can't be found");
        movieToUpdate.setName(movie.getName());
        movieToUpdate.setAgeRestriction(movie.getAgeRestriction());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setDirector(movie.getDirector());
        movieDAO.save(movieToUpdate);
        return movieToUpdate;
    }

    @Override
    public void deleteDistributor(Long movieId, Long distributorID) throws NonHumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if (distributorID == null || distributorID <= 0)
            throw new NonHumanNotFoundException("Passed distributor's id is not correct");
        else if (movieId == null || movieId <= 0) {
            throw new WatchableNotFoundException("Passed movie's id is not correct");
        } else {
            Distributor distributor = distributorDAO.findById(distributorID).orElse(null);
            Movie movie = movieDAO.findById(movieId).orElse(null);
            if (distributor == null)
                throw new NonHumanNotFoundException("Distributor not found");
            if (movie == null)
                throw new WatchableNotFoundException("Movie not found");

            if (!movie.containsDistributor(distributor))
                throw new HasNotValueException("This movie doesn't have this distributor");


            movie.removeDistributor(distributor);
            movieDAO.save(movie);
            distributorDAO.save(distributor);
        }
    }

    @Override
    public List<WatchableBaseEntity> findByName(String name) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name can't null");
        List<Movie> movies = movieDAO.findByName(name);
        return new ArrayList<>(movies);
    }

    @Override
    public void deleteActor(Long movieId, Long actorId) throws HumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if (movieId == null || movieId <= 0) {
            throw new IllegalArgumentException("MovieId is not correct");
        } else if (actorId == null || actorId <= 0) {
            throw new IllegalArgumentException("ActorId is not correct");
        } else {
            Actor actor = actorDAO.findById(actorId).orElse(null);
            Movie movie = movieDAO.findById(movieId).orElse(null);
            if (actor == null)
                throw new HumanNotFoundException("Actor with id= "+actorId+"not found");
            if (movie == null)
                throw new WatchableNotFoundException("Watchable with id= "+movieId+"not found");
            if (!movie.containsActor(actor))
                throw new HasNotValueException("This movie doesn't have this actor");
            movie.removeActor(actor);
            movieDAO.save(movie);
            actorDAO.save(actor);
        }
    }

    @Override
    public void addDirector(Long watchId, Director director) throws AlreadyHasValueException, WatchableNotFoundException, HumanNotFoundException {
        Movie movie = movieDAO.findById(watchId).orElse(null);
        if (director == null)
            throw new HumanNotFoundException("Director is null");
        if (movie == null)
            throw new WatchableNotFoundException("Movie can`t be null or it doesn't have director");
        if (movie.getDirector() != null)
            throw new AlreadyHasValueException("Movie already has director");
        movie.addDirector(director);
        movieDAO.save(movie);
        directorDAO.save(director);
    }

    @Override
    public void removeDirector(Long watchId) throws HasNotValueException, WatchableNotFoundException {
        Movie movie = movieDAO.findById(watchId).orElse(null);
        if (movie == null)
            throw new WatchableNotFoundException("Movie can`t be null");
        if (movie.getDirector() == null)
            throw new HasNotValueException("Movie doesn't have director");
        Director director = movie.getDirector();
        movie.removeDirector();
        directorDAO.save(director);
        movieDAO.save(movie);

    }

    @Override
    public void deleteById(Long id) throws WatchableNotFoundException {
        Movie deleteMovie = findById(id);
        if (deleteMovie == null) {
            throw new WatchableNotFoundException("Movie with id = " + id + " does not exist");
        } else {
            for (Actor actor : deleteMovie.getActors()) {
                actor.getMovies().remove(deleteMovie);
            }
            for (Distributor distributor : deleteMovie.getDistributors()) {
                distributor.getMovies().remove(deleteMovie);
            }
            deleteMovie.getActors().clear();
            deleteMovie.getDistributors().clear();

            deleteMovie.removeDirector();
            movieDAO.deleteById(id);
            movieDAO.flush();
        }
    }

    @Override
    public void addActor(Long watchId, Long actorId) throws WatchableNotFoundException, HumanNotFoundException {
        Movie origin_movie = findById(watchId);
        Actor actor = actorDAO.findById(actorId).orElse(null);
        if (origin_movie == null) {
            throw new WatchableNotFoundException("Movie with id = " + watchId + " does not exist");
        } else if (actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actorId + " does not exist");
        } else {
            origin_movie.addActor(actor);
            movieDAO.save(origin_movie);
            actorDAO.save(actor);
        }
    }

    @Override
    public void addDistributor(Long watchId, Long distributorId) throws WatchableNotFoundException, HumanNotFoundException {
        Movie origin_movie = findById(watchId);
        Distributor distributor = distributorDAO.findById(distributorId).orElse(null);
        if (origin_movie == null) {
            throw new WatchableNotFoundException("Movie with id = " + watchId + " does not exist");
        } else if (distributor == null) {
            throw new HumanNotFoundException("Distributor with id = " + distributorId + " does not exist");
        } else {
            origin_movie.addDistributor(distributor);
            movieDAO.save(origin_movie);
            distributorDAO.save(distributor);
        }

    }
}
