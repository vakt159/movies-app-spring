package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.persistence.DirectorDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements HumanService<Director> {
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
    public List<Director> findAll() {
        List<Director> directors=directorDAO.findAll();
        if(directors.isEmpty()||directors==null)
            return null;
        return directors;
    }

    @Override
    public Director save(Director director) {
        if(director==null)
            throw new RuntimeException("Director doesn't exist");
        return directorDAO.save(director);
    }

    public HumanBaseEntity findDirectorByFirstNameAndLastName(String firstName, String lastName)
    {
        Director foundDirector = directorDAO.findDirectorByFirstNameAndLastName(firstName, lastName);
        if(foundDirector==null)
            throw new RuntimeException("Director doesn't exist");
        return foundDirector;
    }

}
