package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.HumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.*;
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
    public Director findById(Long id) throws HumanNotFoundException {
        if(id==null || id<=0) {
            throw new IllegalArgumentException("Id doesn't exist");
        }
        Optional<Director> directorOptional=directorDAO.findById(id);
        if(directorOptional.isPresent())
            return directorOptional.get();
        else
            throw new HumanNotFoundException("Director with id ="+id+"doesn't exist");
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
    public HumanBaseEntity save(HumanBaseEntity director) throws HumanNotFoundException {
        if(director==null)
            throw new IllegalArgumentException("Director doesn't exist");
        return directorDAO.save((Director) director);
    }

    public void deleteById(Long id) throws HumanNotFoundException {
        if(id==null||id<=0)
            throw new IllegalArgumentException("Id can't be null or <0");
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
            throw new HumanNotFoundException("Director with id = " + id + " is not found");
        }
    }

    @Override
    public HumanBaseEntity update(HumanBaseEntity human, Long id) throws HumanNotFoundException {
        Director director = findById(id);
        if (director == null) {
            throw new HumanNotFoundException("Director with id="+id+" not found");
        } else {
            director.setBiography(human.getBiography());
            director.setBirthday(human.getBirthday());
            director.setFirstName(human.getFirstName());
            director.setLastName(human.getLastName());
            directorDAO.save(director);
            return director;
        }
    }


    public HumanBaseEntity findByFirstNameAndLastName(String firstName, String lastName) throws HumanNotFoundException {
        Optional<Director> foundDirector = directorDAO.findDirectorByFirstNameAndLastName(firstName, lastName);
        if(foundDirector.isEmpty())
            throw new HumanNotFoundException("Director with first name = " + firstName +
                    "and last name = " + lastName + " does not exist");
        return foundDirector.get();
    }

    @Override
    public void deleteTVShow(Long directorId, Long tvShowId) throws HumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if(directorId<=0||directorId==null)
            throw new IllegalArgumentException("Director's id can't be null or <0");
        if(tvShowId<=0||tvShowId==null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Director findedDirector = findById(directorId);
        if(findedDirector==null)
            throw new HumanNotFoundException("Director with id="+directorId+" not found");

        Optional<TVShow> optionalTVShow = tvShowDAO.findById(tvShowId);
        if(optionalTVShow.isPresent()){
            TVShow tvShow = optionalTVShow.get();
            if(!findedDirector.containsTVShow(tvShow))
                throw new HasNotValueException("This tvShow is not directed by this director");
            findedDirector.removeTvShow(tvShow);
            tvShowDAO.save(tvShow);
            directorDAO.save(findedDirector);
        }else{
            throw new WatchableNotFoundException("TVShow with id = " + tvShowId + " was not found");
        }

    }

    @Override
    public void deleteMovie(Long directorId, Long movieId) throws HumanNotFoundException, WatchableNotFoundException, HasNotValueException {
        if(directorId<=0||directorId==null)
            throw new IllegalArgumentException("Director's id can't be null or <0");
        if(movieId<=0||movieId==null)
            throw new IllegalArgumentException("Movie's id can't be null or <0");

        Director findedDirector = findById(directorId);
        if(findedDirector==null)
            throw new HumanNotFoundException("Director with id="+directorId+" not found");
        Optional<Movie> optionalMovie = movieDAO.findById(movieId);
        if(optionalMovie.isPresent()) {
              Movie movie = optionalMovie.get();
            if(!findedDirector.containsMovie(movie))
                throw new HasNotValueException("This movie is not directed by this director");
            findedDirector.removeMovie(movie);
            movieDAO.save(movie);
            directorDAO.save(findedDirector);
        }
        else throw new WatchableNotFoundException("Movie with id = " + movieId + " was not found");
    }

    @Override
    public void addTVShow(Long tvShowId, Long directorId) throws HumanNotFoundException, AlreadyHasValueException {

        if(directorId<=0||directorId==null)
            throw new IllegalArgumentException("Director's id can't be null or <0");
        if(tvShowId<=0||tvShowId==null)
            throw new IllegalArgumentException("TvShow's id can't be null or <0");

        Director findedDirector = findById(directorId);
        if(findedDirector==null)
            throw new HumanNotFoundException("Director with id="+directorId+" not found");

        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShowId);

        if(opTvShow.isPresent()){
            TVShow tvShow=opTvShow.get();
            if(findedDirector.containsTVShow(tvShow))
                throw new AlreadyHasValueException("This tvShow already directed by this director");
            findedDirector.addTvShow(tvShow);
            directorDAO.save(findedDirector);
            tvShowDAO.save(opTvShow.get());
        }else{
            throw new NoSuchElementException("TVShow with id = " + tvShowId + " was not found");
        }
    }

    @Override
    public void addMovie(Long movieId, Long directorId) throws HumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException {
        if(directorId<=0||directorId==null)
            throw new IllegalArgumentException("Director's id can't be null or <0");
        if(movieId<=0||movieId==null)
            throw new IllegalArgumentException("Movie's id can't be null or <0");

        Director director= directorDAO.findById(directorId).get();
        if(director==null)
            throw new HumanNotFoundException("Director with id="+directorId+" not found");

        Optional<Movie> opMovie = movieDAO.findById(movieId);
        if(opMovie.isPresent()){
             Movie movie=opMovie.get();
             if(director.containsMovie(movie))
                throw new AlreadyHasValueException("This movie already directed by this director");
            director.addMovie(movie);
            directorDAO.save(director);
            movieDAO.save(opMovie.get());
        }else{
            throw new WatchableNotFoundException("Movie with id = " + movieId + " was not found");
        }
    }

}
