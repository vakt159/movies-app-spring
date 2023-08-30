package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.persistence.ActorDAO;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements WatchableService<Movie> {
    private final MovieDAO movieDAO;
    private final ActorDAO actorDAO;
    private final DistributorDAO distributorDAO;

    public MovieServiceImpl(MovieDAO movieDAO, ActorDAO actorDAO, DistributorDAO distributorDAO) {
        this.movieDAO = movieDAO;
        this.actorDAO = actorDAO;
        this.distributorDAO = distributorDAO;
    }
    @Override
    public Movie findById(Long id) {
        if(id==null || id<=0) {
            throw new RuntimeException("Id doesn't exist");
        }
        Optional<Movie> movieOptional= movieDAO.findById(id);
        return movieOptional.orElse(null);
    }
    @Override
    public List<Movie> findAll() {
        List<Movie> movies= movieDAO.findAll();
        if(movies.isEmpty()||movies==null)
            return null;
        return movies;
    }

    @Override
    public Movie save(Movie movie) {
        if(movie==null)
            throw new RuntimeException("Movie doesn't exist");
        return movieDAO.save(movie);
    }


    @Override
    public Movie update(Movie movie, Long id) {
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

        Optional<Movie> movieToUpdateOptional = movieDAO.findById(id);
        Movie movieToUpdate = movieToUpdateOptional.orElse(null);

        if (movieToUpdate == null)
            throw new RuntimeException("Movie ot update can't be found");
        movieToUpdate.setDirector(movie.getDirector());
        movieToUpdate.setName(movie.getName());
        movieToUpdate.setAgeRestriction(movie.getAgeRestriction());
        movieToUpdate.setDescription(movie.getDescription());
        return null;
    }

    @Override
    public void deleteDistributor(Long movieId, Long distributorID) {
        if (distributorID == null || distributorID <= 0||movieId == null || movieId <= 0)
            throw new RuntimeException("Id is not correct");
        else {
            Long distributorIdToDelete = distributorDAO.findById(distributorID).get().getId();
            Movie movieToUpdate = movieDAO.findById(movieId).orElse(null);
            Distributor distributorToDelete = null;

            if (movieToUpdate == null)
                throw new RuntimeException("Movie can't be null");

            Set<Distributor> distributors = movieToUpdate.getDistributors();
            for (Distributor distributor : distributors)
                if (distributor.getId() == distributorIdToDelete) {
                    distributorToDelete = distributor;
                    distributors.remove(distributorToDelete);
                    break;
                }
            movieToUpdate.setDistributors(distributors);

            Movie movieToDelete = null;
            Set<Movie> movies = distributorToDelete.getMovie();
            for (Movie movie : movies)
                if (movie.getId() == movieId) {
                    movieToDelete = movie;
                    distributorToDelete.getMovie().remove(movieToDelete);
                }
            distributorToDelete.setMovie(movies);
            movieDAO.save(movieToUpdate);
            distributorDAO.save(distributorToDelete);
        }
    }

    @Override
    public void deleteActor(Long movieId,Long actorId) {
        if (actorId == null || actorId <= 0||movieId == null || movieId <= 0)
            throw new RuntimeException("Id is not correct");
        else {
            Long actorIdToDelete = actorDAO.findById(actorId).get().getId();
            Movie movieToUpdate = movieDAO.findById(movieId).orElse(null);
            Actor actorToDelete = null;

            if (movieToUpdate == null)
                throw new RuntimeException("Movie can't be null");

            Set<Actor> actors = movieToUpdate.getActors();
            for (Actor actor : actors)
                if (actor.getId() == actorIdToDelete) {
                    actorToDelete = actor;
                    actors.remove(actorToDelete);
                    break;
                }
            movieToUpdate.setActors(actors);

            Movie movieToDelete = null;
            Set<Movie> movies = actorToDelete.getMovies();
            for (Movie movie : movies)
                if (movie.getId() == movieId) {
                    movieToDelete = movie;
                    actorToDelete.getMovies().remove(movieToDelete);
                }
            actorToDelete.setMovies(movies);
            movieDAO.save(movieToUpdate);
            actorDAO.save(actorToDelete);
        }
    }

    @Override
    public void deleteById(Long id) {

    }

}
