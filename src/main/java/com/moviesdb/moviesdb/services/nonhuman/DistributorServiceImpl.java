package com.moviesdb.moviesdb.services.nonhuman;

import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.NonHumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class DistributorServiceImpl implements NonHumanService {

    private DistributorDAO distributorDAO;
    private MovieDAO movieDAO;
    private TVShowDAO tvShowDAO;

    public DistributorServiceImpl(DistributorDAO distributorDAO, MovieDAO movieDAO, TVShowDAO tvShowDAO) {
        this.distributorDAO = distributorDAO;
        this.movieDAO = movieDAO;
        this.tvShowDAO = tvShowDAO;
    }

    @Override
    public List<NonHumanBaseEntity> findAll() {
        List<Distributor> distributors = distributorDAO.findAll();
        if (distributors == null || distributors.isEmpty())
            return new ArrayList<>();
        List<NonHumanBaseEntity> nonHumanDistributor = new ArrayList<>(distributors);
        return nonHumanDistributor;
    }

    @Override
    public void deleteById(Long id) throws NonHumanNotFoundException {
        Distributor distributor = findDistributorById(id);
        if (distributor == null) {
            throw new NonHumanNotFoundException("Distributor with id = " + id + " does not exist");
        } else {
            for (Movie movie : distributor.getMovies()) {
                movie.getDistributors().remove(distributor);
            }
            for (TVShow tvShow : distributor.getTvShows()) {
                tvShow.getDistributors().remove(distributor);
            }
            distributor.getMovies().clear();
            distributor.getTvShows().clear();
            distributorDAO.deleteById(id);
            distributorDAO.flush();
        }
    }

    @Override
    public Distributor findDistributorById(Long id) throws NonHumanNotFoundException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id doesn't exist");
        }
        Optional<Distributor> distributorOptional = distributorDAO.findById(id);
        if (distributorOptional.isPresent())
            return distributorOptional.get();
        else
            throw new NonHumanNotFoundException("Distributor with id =" + id + "doesn't exist");

    }

    @Override
    public List<NonHumanBaseEntity> findByName(String name) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name can't be null");
        List<Distributor> distributors = distributorDAO.findDistributorByName(name);
        return new ArrayList<>(distributors);
        }


    @Override
    public NonHumanBaseEntity save(NonHumanBaseEntity distributor) throws NonHumanNotFoundException {
        if(distributor==null)
            throw new IllegalArgumentException("Distributor can't be null");
        return distributorDAO.save((Distributor) distributor);
    }


    @Override
    public NonHumanBaseEntity update(Long id, NonHumanBaseEntity distributor) throws NonHumanNotFoundException {
        Distributor distributorToUpdate;
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if (dOptional.isPresent()) {
            distributorToUpdate = dOptional.get();
        } else {
            throw new NonHumanNotFoundException("Distributor with id = " + id + " is not found");
        }

        distributorToUpdate.setName(distributor.getName());
        distributorToUpdate.setDescription(distributor.getDescription());
        return distributorDAO.save(distributorToUpdate);
    }

    @Override
    public void deleteTVShow(Long distributorId, Long tvShowId) throws NonHumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if(distributorId<=0||distributorId==null)
            throw new IllegalArgumentException("Distributor's id can't be null or <0");
        if(tvShowId<=0||tvShowId==null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Distributor distributor = findDistributorById(distributorId);
        if(distributor==null)
            throw new NonHumanNotFoundException("Distributor with id= "+distributorId+" doesn't exist");

        TVShow tvShow = tvShowDAO.findById(tvShowId).get();
        if(tvShow==null)
            throw new WatchableNotFoundException("TvShow with id= "+tvShowId+" doesn't exist");
        if(!distributor.containsTVShow(tvShow))
            throw new HasNotValueException("This distributor don't have this tvShow");
        distributor.removeTvShow(tvShow);
        tvShowDAO.save(tvShow);
        distributorDAO.save(distributor);
    }

    @Override
    public void deleteMovie(Long distributorId, Long movieId) throws NonHumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if(distributorId<=0||distributorId==null)
            throw new IllegalArgumentException("Distributor's id can't be null or <0");
        if(movieId<=0||movieId==null)
            throw new IllegalArgumentException("Movie's id can't be null or <0");

        Distributor distributor = findDistributorById(distributorId);
        if(distributor==null)
            throw new NonHumanNotFoundException("Distributor with id= "+distributorId+" doesn't exist");

        Movie movie = movieDAO.findById(movieId).get();
        if(movie==null)
            throw new WatchableNotFoundException("Movie with id= "+movieId+" doesn't exist");
        if(!distributor.containsMovie(movie))
            throw new HasNotValueException("This distributor don't have this movie");
        distributor.removeMovie(movie);
        movieDAO.save(movie);
        distributorDAO.save(distributor);
    }

    @Override
    public void addTVShow(Long tvShowId, Long distributorId) throws NonHumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException {
        if(distributorId<=0||distributorId==null)
            throw new IllegalArgumentException("Distributor's id can't be null or <0");
        if(tvShowId<=0||tvShowId==null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Distributor distributor = findDistributorById(distributorId);
        if(distributor==null)
            throw new NonHumanNotFoundException("Distributor with id= "+distributorId+" doesn't exist");
        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShowId);
        if (opTvShow.isPresent()) {
            if(distributor.containsTVShow(opTvShow.get()))
                throw new AlreadyHasValueException("This distributor already has this tvShow");
            distributor.addTvShow(opTvShow.get());
            distributorDAO.save(distributor);
            tvShowDAO.save(opTvShow.get());
        } else {
            throw new WatchableNotFoundException("TVShow with id = " + tvShowId + " was not found");
        }
    }

    @Override
    public void addMovie(Long movieId, Long distributorId) throws NonHumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException {

        if(distributorId<=0||distributorId==null)
            throw new IllegalArgumentException("Distributor's id can't be null or <0");
        if(movieId<=0||movieId==null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Distributor distributor = findDistributorById(distributorId);
        if(distributor==null)
            throw new NonHumanNotFoundException("Distributor with id= "+distributorId+" doesn't exist");
        Optional<Movie> opMovie = movieDAO.findById(movieId);
        if (opMovie.isPresent()) {
            if(distributor.containsMovie(opMovie.get()))
                throw new AlreadyHasValueException("This distributor already has this movie");
            distributor.addMovie(opMovie.get());
            distributorDAO.save(distributor);
            movieDAO.save(opMovie.get());
        } else {
            throw new WatchableNotFoundException("Movie with id = " + movieId + " was not found");
        }
    }

}
