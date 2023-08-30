package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.persistence.DistributorDAO;
import com.moviesdb.moviesdb.persistence.MovieDAO;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class DistributorServiceImpl  implements DistributorService{

    private DistributorDAO distributorDAO;
    private MovieDAO movieDAO;
    private TVShowDAO tvShowDAO;

    public DistributorServiceImpl(DistributorDAO distributorDAO, MovieDAO movieDAO, TVShowDAO tvShowDAO) {
        this.distributorDAO = distributorDAO;
        this.movieDAO = movieDAO;
        this.tvShowDAO = tvShowDAO;
    }

    @Override
    public List<Distributor> findAll() {

        return distributorDAO.findAll();
    }

    @Override
    @Transactional
    public void deleteDistributorById(Long id) {
        if(distributorDAO.findById(id).isPresent()){
            distributorDAO.deleteById(id);
        }else{
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }

    }

    @Override
    public Distributor findDistributorById(Long id) {
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if(dOptional.isPresent()){
            return (Distributor) dOptional.get();
        }else{
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }
    }

    @Override
    public Distributor findByName(String name) {
        Optional<Distributor> dOptional = distributorDAO.findDistributorByName(name);
        if(dOptional.isPresent()){
            return (Distributor) dOptional.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public Distributor save(Distributor distributor) {
        return distributorDAO.save(distributor);
    }

    public Distributor update(Long id, Distributor distributor){
        Distributor distributorToUpdate;
        Optional<Distributor> dOptional = distributorDAO.findById(id);
        if(dOptional.isPresent()){
             distributorToUpdate = dOptional.get();
        }else{
            throw new NoSuchElementException("Distributor with id = " + id + " is not found");
        }

        distributorToUpdate.setName(distributor.getName());
        distributorToUpdate.setDescription(distributor.getDescription());
        return distributorDAO.save(distributorToUpdate);
    }

    @Override
    public void deleteTVShow(Long distributorId,Long tvShowId) {
    Distributor distributor = this.findDistributorById(distributorId);
    TVShow tvShow = tvShowDAO.findById(tvShowId).get();

   Set<Distributor> distributorSet=  tvShow.getDistributors().stream().
           filter(distributor1 -> distributor1.getId() != distributorId).collect(Collectors.toSet());
   tvShow.setDistributors(distributorSet);
   tvShowDAO.save(tvShow);
    distributor.getTvShows().remove(tvShow);
    distributorDAO.save(distributor);
    }

    @Override
    public void deleteMovie(Long distributorId,Long movieId) {
        Distributor distributor = this.findDistributorById(distributorId);
        Movie movie = movieDAO.findById(movieId).get();

        Set<Distributor> distributorSet=  movie.getDistributors().stream().
                filter(distributor1 -> distributor1.getId() != distributorId).collect(Collectors.toSet());
        movie.setDistributors(distributorSet);
        movieDAO.save(movie);
        distributor.getMovies().remove(movie);
        distributorDAO.save(distributor);
    }

    @Override
    public void addTVShow(TVShow tvShow, Long id) {
        Distributor distributor= distributorDAO.findById(id).get();
        Optional<TVShow> opTvShow = tvShowDAO.findById(tvShow.getId());

        if(opTvShow.isPresent()){
            distributor.getTvShows().add(opTvShow.get());
            distributorDAO.save(distributor);
        }else{
            distributor.getTvShows().add(tvShowDAO.save(tvShow));
            distributorDAO.save(distributor);
        }
    }

    @Override
    public void addMovie(Movie movie, Long id) {
        Distributor distributor= distributorDAO.findById(id).get();
        Optional<Movie> opMovie = movieDAO.findById(movie.getId());

        if(opMovie.isPresent()){
            distributor.getMovies().add(opMovie.get());
            distributorDAO.save(distributor);
        }else{
            distributor.getMovies().add(movieDAO.save(movie));
            distributorDAO.save(distributor);
        }
    }

}
