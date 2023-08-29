package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements WatchableService<Movie> {
    private final MovieDAO movieDAO;

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
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
    public Movie update(Movie movie,Long id) {
        if(movie==null||id==null)
            throw new RuntimeException("Movie or id can't be null");
        if(movie.getDirector()==null)
            throw new RuntimeException("Director can't be null");
        if(movie.getDistributors()==null)
            throw new RuntimeException("Distributors can't be null");
        if(movie.getName()==null||movie.getName().equals(""))
            throw new RuntimeException("Name can't be null");
        if(movie.getActors()==null||movie.getActors().isEmpty())
            throw new RuntimeException("Movie should have at least 1 actor");
        if(movie.getAgeRestriction()<=0)
            throw new RuntimeException("Age restriction can't be less than 1");
        if(movie.getDescription()==null||movie.getDescription().equals(""))
            throw new RuntimeException("Description can't be null");

        Optional<Movie> movieToUpdateOptional=movieDAO.findById(id);
        Movie movieToUpdate=movieToUpdateOptional.orElse(null);

        if(movieToUpdate==null)
            throw new RuntimeException("Movie ot update can't be found");
        movieToUpdate.setActors(movie.getActors());
        movieToUpdate.setDirector(movie.getDirector());
        movieToUpdate.setDistributors(movie.getDistributors());
        movieToUpdate.setName(movie.getName());
        movieToUpdate.setAgeRestriction(movie.getAgeRestriction());
        movieToUpdate.setDescription(movie.getDescription());

        return null;
    }

}
