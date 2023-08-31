package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.persistence.DirectorDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements HumanService {
    private final DirectorDAO directorDAO;

    public DirectorServiceImpl(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
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
    }

    @Override
    public HumanBaseEntity update(HumanBaseEntity human, Long id) {
        return null;
    }

    @Override
    public void deleteTVShow(Long humanId, Long TvShowId) {

    }

    @Override
    public void deleteMovie(Long humanId, Long movieId) {

    }

    @Override
    public HumanBaseEntity saveTVShow(Long humanId, Long TvShowId) {
        return null;
    }

    @Override
    public HumanBaseEntity saveMovie(Long humanId, Long movieId) {
        return null;
    }

    public HumanBaseEntity findDirectorByFirstNameAndLastName(String firstName, String lastName)
    {
        Director foundDirector = directorDAO.findDirectorByFirstNameAndLastName(firstName, lastName);
        if(foundDirector==null)
            throw new RuntimeException("Director doesn't exist");
        return foundDirector;
    }

}
