package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.persistence.DirectorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class DirectorServiceImpl implements HumanService {
    private final DirectorDAO directorDAO;
    private final TVShowDAO tvShowDAO;
    private final MovieDAO movieDAO;

    public DirectorServiceImpl(DirectorDAO directorDAO, TVShowDAO tvShowDAO, MovieDAO movieDAO) {
        this.directorDAO = directorDAO;
        this.tvShowDAO = tvShowDAO;
        this.movieDAO = movieDAO;
    }
    @Override
    public Director findById(Long id) {
        if(id==null || id<=0) {
            throw new RuntimeException("Id doesn't exist");
        }
            Optional<Director> directorOptional=directorDAO.findById(id);
        return directorOptional.orElse(null);
    }
    @Override
    public List<HumanBaseEntity> findAll() {
        List<Director> directors = directorDAO.findAll();
        if (directors == null || directors.isEmpty()) {
            return new ArrayList<>();
        }
        List<HumanBaseEntity> humanDirectors = new ArrayList<>(directors);
        return humanDirectors;
    }

    @Override
    public HumanBaseEntity save(HumanBaseEntity director) {
        if(director==null)
            throw new RuntimeException("Director doesn't exist");
        return directorDAO.save((Director) director);
    }

    @Override
    public void deleteById(Long id) {
        if(directorDAO.findById(id).isPresent()){
            directorDAO.deleteById(id);
        }else{
            throw new NoSuchElementException("Director with id = " + id + " is not found");
        }
    }

    @Override
    public HumanBaseEntity update(HumanBaseEntity human, Long id) {
        return null;
    }


    public HumanBaseEntity findDirectorByFirstNameAndLastName(String firstName, String lastName)
    {
        Director foundDirector = directorDAO.findDirectorByFirstNameAndLastName(firstName, lastName);
        if(foundDirector==null)
            throw new RuntimeException("Director doesn't exist");
        return foundDirector;
    }

    @Override
    public void deleteTVShow(Long directorId, Long tvShowId) {
        Director findedDirector = this.findById(directorId);
        Optional<TVShow> optionalTVShow = tvShowDAO.findById(tvShowId);

        if(optionalTVShow.isPresent()){
            TVShow tvShow = optionalTVShow.get();
            tvShow.setDirector(null);
            tvShowDAO.save(tvShow);
            findedDirector.getTvShows().remove(tvShow);
            directorDAO.save(findedDirector);
        }else{
            throw new NoSuchElementException("TVShow with id = " + tvShowId + " was not found");
        }

    }

    @Override
    public void deleteMovie(Long directorId, Long movieId) {
        Director findedDirector = this.findById(directorId);
        Optional<Movie> optionalMovie = movieDAO.findById(movieId);
        if(optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setDirector(null);
            movieDAO.save(movie);
            findedDirector.getMovies().remove(movie);
            directorDAO.save(findedDirector);
        }
    }

    @Override
    public void addTVShow(Long tvShowId, Long directorId) {
        Director director= directorDAO.findById(directorId).get();
        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShowId);

        if(opTvShow.isPresent()){
            director.getTvShows().add(opTvShow.get());
            directorDAO.save(director);
            opTvShow.get().setDirector(director);
            tvShowDAO.save(opTvShow.get());
        }else{
            throw new NoSuchElementException("TVShow with id = " + tvShowId + " was not found");
        }
    }

    @Override
    public void addMovie(Long movieId, Long directorId) {
        Director director= directorDAO.findById(directorId).get();
        Optional<Movie> opMovie = movieDAO.findById(movieId);

        if(opMovie.isPresent()){
            director.getMovies().add(opMovie.get());
            directorDAO.save(director);
            opMovie.get().setDirector(director);
            movieDAO.save(opMovie.get());
        }else{
            throw new NoSuchElementException("Movie with id = " + movieId + " was not found");
        }
    }

}
