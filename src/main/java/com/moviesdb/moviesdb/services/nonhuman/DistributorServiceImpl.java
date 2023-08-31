package com.moviesdb.moviesdb.services.nonhuman;

import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Transactional
    public void deleteById(Long id) {
        if (distributorDAO.findById(id).isPresent()) {
            distributorDAO.deleteById(id);
        } else {
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }

    }

    @Override
    public Distributor findDistributorById(Long id) {
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if (dOptional.isPresent()) {
            return (Distributor) dOptional.get();
        } else {
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }
    }

    @Override
    public Distributor findByName(String name) {
        Optional<Distributor> dOptional = distributorDAO.findDistributorByName(name);
        if (dOptional.isPresent()) {
            return (Distributor) dOptional.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public NonHumanBaseEntity save(NonHumanBaseEntity distributor) {
        return distributorDAO.save((Distributor) distributor);
    }


    @Override
    public NonHumanBaseEntity update(Long id, NonHumanBaseEntity distributor) {
        Distributor distributorToUpdate;
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if (dOptional.isPresent()) {
            distributorToUpdate = dOptional.get();
        } else {
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }

        distributorToUpdate.setName(distributor.getName());
        distributorToUpdate.setDescription(distributor.getDescription());
        return distributorDAO.save(distributorToUpdate);
    }

    @Override
    public void deleteTVShow(Long distributorId, Long tvShowId) {
        Distributor distributor = this.findDistributorById(distributorId);
        TVShow tvShow = tvShowDAO.findById(tvShowId).get();

        Set<Distributor> distributorSet = tvShow.getDistributors().stream().
                filter(distributor1 -> distributor1.getId() != distributorId).collect(Collectors.toSet());
        tvShow.setDistributors(distributorSet);
        tvShowDAO.save(tvShow);
        distributor.getTvShows().remove(tvShow);
        distributorDAO.save(distributor);
    }

    @Override
    public void deleteMovie(Long distributorId, Long movieId) {
        Distributor distributor = this.findDistributorById(distributorId);
        Movie movie = movieDAO.findById(movieId).get();

        Set<Distributor> distributorSet = movie.getDistributors().stream().
                filter(distributor1 -> distributor1.getId() != distributorId).collect(Collectors.toSet());
        movie.setDistributors(distributorSet);
        movieDAO.save(movie);
        distributor.getMovies().remove(movie);
        distributorDAO.save(distributor);
    }

    @Override
    public void addTVShow(Long tvShowId, Long distributorId) {
        Distributor distributor = distributorDAO.findById(distributorId).get();
        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShowId);

        if (opTvShow.isPresent()) {
            distributor.getTvShows().add(opTvShow.get());
            distributorDAO.save(distributor);
            opTvShow.get().getDistributors().add(distributor);
            tvShowDAO.save(opTvShow.get());
        } else {
            throw new NoSuchElementException("TVShow with id = " + tvShowId + " was not found");
        }
    }

    @Override
    public void addMovie(Long movieId, Long distributorId) {
        Distributor distributor = distributorDAO.findById(distributorId).get();
        Optional<Movie> opMovie = movieDAO.findById(movieId);

        if (opMovie.isPresent()) {
            distributor.getMovies().add(opMovie.get());
            distributorDAO.save(distributor);
            opMovie.get().getDistributors().add(distributor);
            movieDAO.save(opMovie.get());
        } else {
            throw new NoSuchElementException("Movie with id = " + movieId + " was not found");
        }
    }

}
