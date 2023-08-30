package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TVShowServiceImpl implements WatchableService  {
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
    public List<WatchableBaseEntity> findAll() {
        List<TVShow> tvShows = tvShowDAO.findAll();
        if (tvShows == null || tvShows.isEmpty()) {
            return new ArrayList<>();
        }
        List<WatchableBaseEntity> watchableMovies = new ArrayList<>(tvShows);
        return watchableMovies;
    }

    @Override
    public WatchableBaseEntity update(WatchableBaseEntity watchable, Long id) {
        return null;
    }

    @Override
    public WatchableBaseEntity save(WatchableBaseEntity tvShow) {
        if(tvShow==null)
            throw new RuntimeException("TVShow doesn't exist");
        return tvShowDAO.save((TVShow) tvShow);
    }

    @Override
    public void deleteDistributor(Long watchableId, Long distributorId) {

    }

    @Override
    public void deleteActor(Long watchableId, Long actorId) {

    }

    @Override
    public void deleteById(Long id) {

    }


}
