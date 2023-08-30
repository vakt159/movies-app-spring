package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.persistence.TVShowDAO;

import java.util.List;
import java.util.Optional;

public class TVShowServiceImpl implements WatchableService<TVShow> {
    private final TVShowDAO tvShowDAO;

    public TVShowServiceImpl(TVShowDAO tvShowDAO) {
        this.tvShowDAO = tvShowDAO;
    }
    @Override
    public TVShow findById(Long id) {
        if(id==null || id<=0) {
            throw new RuntimeException("Id doesn't exist");
        }
        Optional<TVShow> tvShowOptional= tvShowDAO.findById(id);
        return tvShowOptional.orElse(null);
    }
    @Override
    public List<TVShow> findAll() {
        List<TVShow> tvShows= tvShowDAO.findAll();
        if(tvShows.isEmpty()||tvShows==null)
            return null;
        return tvShows;
    }

    @Override
    public TVShow save(TVShow tvShow) {
        if(tvShow==null)
            throw new RuntimeException("TVShow doesn't exist");
        return tvShowDAO.save(tvShow);
    }

    @Override
    public TVShow update(TVShow watchable, Long id) {
        return null;
    }
}
