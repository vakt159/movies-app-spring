package com.moviesdb.moviesdb.services.watchable;

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
    public Movie findById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id doesn't exist");
        }
        Optional<Movie> movieOptional = movieDAO.findById(id);
        return movieOptional.orElse(null);
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
    public WatchableBaseEntity save(WatchableBaseEntity movie) {
        if (movie == null)
            throw new RuntimeException("Movie doesn't exist");
        return movieDAO.save((Movie) movie);
    }

    @Override
    public WatchableBaseEntity update(WatchableBaseEntity watchable, Long id) {
        Movie movie = (Movie) watchable;
        if (movie == null || id == null)
            throw new RuntimeException("Movie or id can't be null");
        if (movie.getDirector() == null)
            throw new RuntimeException("Director can't be null");
        if (movie.getName() == null || movie.getName().equals(""))
            throw new RuntimeException("Name can't be null");
        if (movie.getAgeRestriction() <= 0)
            throw new RuntimeException("Age restriction can't be less than 1");
        if (movie.getDescription() == null || movie.getDescription().equals(""))
            throw new RuntimeException("Description can't be null");

        Movie movieToUpdate = movieDAO.findById(id).orElse(null);

        if (movieToUpdate == null)
            throw new RuntimeException("Movie ot update can't be found");
        movieToUpdate.setName(movie.getName());
        movieToUpdate.setAgeRestriction(movie.getAgeRestriction());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setDirector(movie.getDirector());
        movieDAO.save(movieToUpdate);
        return movieToUpdate;
    }

    @Override
    public void deleteDistributor(Long movieId, Long distributorID) {
        if (distributorID == null || distributorID <= 0 || movieId == null || movieId <= 0)
            throw new RuntimeException("Id is not correct");
        else {
            Distributor distributor = distributorDAO.findById(distributorID).orElse(null);
            Movie movie = movieDAO.findById(movieId).orElse(null);
            if (distributor == null || movie == null)
                throw new RuntimeException("Distributor or movie not found");
            movie.removeDistributor(distributor);
            movieDAO.save(movie);
            distributorDAO.save(distributor);
        }
    }

    @Override
    public List<WatchableBaseEntity> findByName(String name) {
        if (name == null || name.equals(""))
            throw new RuntimeException("Name can't null");
        List<Movie> movies = movieDAO.findByName(name);
        return new ArrayList<>(movies);
    }

    @Override
    public void deleteActor(Long movieId, Long actorId) {
        if (actorId == null || actorId <= 0 || movieId == null || movieId <= 0)
            throw new RuntimeException("Id is not correct");
        else {
            Actor actor = actorDAO.findById(actorId).orElse(null);
            Movie movie = movieDAO.findById(movieId).orElse(null);
            if (actor == null || movie == null)
                throw new RuntimeException("Actor or movie not found");
            movie.removeActor(actor);
            movieDAO.save(movie);
            actorDAO.save(actor);
        }
    }

    @Override
    public void addDirector(Long watchId, Director director) {
        Movie movie = movieDAO.findById(watchId).orElse(null);
        if (director == null)
            throw new RuntimeException("Director is null");
        if (movie == null)
            throw new RuntimeException("Movie can`t be null or it doesn't have director");
        movie.addDirector(director);
        movieDAO.save(movie);
        directorDAO.save(director);

    }

    @Override
    public void removeDirector(Long watchId) {

        Movie movie = movieDAO.findById(watchId).orElse(null);
        if (movie == null || movie.getDirector() == null)
            throw new RuntimeException("Movie can`t be null or it doesn't have director");
        Director director = movie.getDirector();
        movie.removeDirector();
        directorDAO.save(director);
        movieDAO.save(movie);

    }

    @Override
    public void deleteById(Long id) {
        Movie deleteMovie = findById(id);
        if (deleteMovie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            for (Actor actor : deleteMovie.getActors()) {
                actor.getMovies().remove(deleteMovie);
            }

            for (Distributor distributor : deleteMovie.getDistributors()) {
                distributor.getMovies().remove(deleteMovie);
            }
            deleteMovie.getActors().clear();
            deleteMovie.getActors().clear();
            deleteMovie.getDistributors().clear();

            deleteMovie.removeDirector();
            movieDAO.deleteById(id);
            movieDAO.flush();
        }
    }

    @Override
    public void addActor(Long watchId, Long actorId) {
        Movie origin_movie = findById(watchId);
        Actor actor = actorDAO.findById(actorId).orElse(null);
        if (origin_movie == null) {
            throw new NoSuchElementException("Movie with id = " + watchId + " does not exist");
        } else if (actor == null) {
            throw new NoSuchElementException("Actor with id = " + actorId + " does not exist");
        } else {
            origin_movie.addActor(actor);
            System.out.println(origin_movie.getActors());
            System.out.println(origin_movie.getId());
            movieDAO.save(origin_movie);
            actorDAO.save(actor);
        }
    }

    @Override
    public void addDistributor(Long watchId, Long distributorId) {
        Movie origin_movie = findById(watchId);
        Distributor distributor = distributorDAO.findById(distributorId).orElse(null);
        if (origin_movie == null) {
            throw new NoSuchElementException("Movie with id = " + watchId + " does not exist");
        } else if (distributor == null) {
            throw new NoSuchElementException("Distributor with id = " + distributorId + " does not exist");
        } else {
            origin_movie.addDistributor(distributor);
            movieDAO.save(origin_movie);
            distributorDAO.save(distributor);
        }

    }
}
