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
        if(directorOptional.isPresent())
            return directorOptional.get();
        else
            throw new RuntimeException("Director with this id doesn't exist");
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

    public void deleteById(Long id) {
        Director director;
        if(directorDAO.findById(id).isPresent()){
            director = directorDAO.findById(id).get();

            director.getMovies().forEach(movie -> {
                movie.setDirector(null);
                movieDAO.save(movie);
            });

            director.getTvShows().forEach(tvShow -> {
                tvShow.setDirector(null);
                tvShowDAO.save(tvShow);
            });
            directorDAO.deleteById(id);
        }else{
            throw new NoSuchElementException("Director with id = " + id + " is not found");
        }
    }

    @Override
    public HumanBaseEntity update(HumanBaseEntity human, Long id) {
        return null;
    }


    public HumanBaseEntity findByFirstNameAndLastName(String firstName, String lastName)
    {
        Optional<Director> foundDirector = directorDAO.findDirectorByFirstNameAndLastName(firstName, lastName);
        if(foundDirector.isEmpty())
            throw new NoSuchElementException("Director with first name = " + firstName +
                    "and last name = " + lastName + " does not exist");
        return foundDirector.get();
    }

    @Override
    public void deleteTVShow(Long directorId, Long tvShowId) {
        Director findedDirector = this.findById(directorId);
        Optional<TVShow> optionalTVShow = tvShowDAO.findById(tvShowId);

        if(optionalTVShow.isPresent()){
            TVShow tvShow = optionalTVShow.get();
            findedDirector.removeTvShow(tvShow);
            tvShowDAO.save(tvShow);
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
            findedDirector.removeMovie(movie);
            movieDAO.save(movie);
            directorDAO.save(findedDirector);
        }
    }

    @Override
    public void addTVShow(Long tvShowId, Long directorId) {
        Director director= directorDAO.findById(directorId).get();
        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShowId);

        if(opTvShow.isPresent()){
            TVShow tvShow=opTvShow.get();
            director.addTvShow(tvShow);
            directorDAO.save(director);
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
            Movie movie=opMovie.get();
            director.addMovie(movie);
            directorDAO.save(director);
            movieDAO.save(opMovie.get());
        }else{
            throw new NoSuchElementException("Movie with id = " + movieId + " was not found");
        }
    }

}
