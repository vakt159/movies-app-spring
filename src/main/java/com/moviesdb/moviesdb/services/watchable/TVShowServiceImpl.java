package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import com.moviesdb.moviesdb.services.human.ActorServiceImpl;
import com.moviesdb.moviesdb.services.nonhuman.DistributorServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TVShowServiceImpl implements WatchableService  {
    private final TVShowDAO tvShowDAO;
    private final DistributorServiceImpl distributorService;
    private final ActorServiceImpl actorService;

    public TVShowServiceImpl(TVShowDAO tvShowDAO, DistributorServiceImpl distributorService, ActorServiceImpl actorService) {
        this.tvShowDAO = tvShowDAO;
        this.distributorService = distributorService;
        this.actorService = actorService;
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


    public void deleteDistributor(Long watchableId, Long distributorId) {
        TVShow origin_tvShow = findById(watchableId);
        Distributor distributor = distributorService.findDistributorById(watchableId);
        if (origin_tvShow == null){
            throw new NoSuchElementException("TVShow with id = " + watchableId + " does not exist");
        } else if(distributor == null){
            throw new NoSuchElementException("Distributor with id = " + distributorId + " does not exist");
        } else if(!origin_tvShow.getDistributors().contains(distributor)){
            throw new NoSuchElementException("TVShow with id = " + watchableId + " does not have distributor with id = " + distributorId);
        }
        else{
            origin_tvShow.getDistributors().remove(distributor);
            distributor.getTvShows().remove(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            distributorService.save(distributor);
        }
    }


    public void deleteActor(Long watchableId, Long actorId) {
        TVShow origin_tvShow = findById(watchableId);
        Actor actor = actorService.findById(actorId);
        if (origin_tvShow == null){
            throw new NoSuchElementException("TVSHow with id = " + watchableId + " does not exist");
        } else if(actor == null){
            throw new NoSuchElementException("Actor with id = " + actorId + " does not exist");
        } else if(!origin_tvShow.getActors().contains(actor)){
            throw new NoSuchElementException("Actor with id = " + actorId + " have not played in TV show with id = " + watchableId);
        }
        else{
            origin_tvShow.getActors().remove(actor);
            actor.getTvShows().remove(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            actorService.save(actor);
        }
    }

    @Override
    public void deleteById(Long id) {
        TVShow deleteTVShow = findById(id);
        if (deleteTVShow == null) {
            throw new NoSuchElementException("TVShow with id = " + id + " does not exist");
        } else {
            for (Distributor distributor : deleteTVShow.getDistributors()) {
                distributor.getTvShows().remove(deleteTVShow);
            }
            for (Actor actor : deleteTVShow.getActors()) {
                actor.getTvShows().remove(deleteTVShow);
            }
            ///??????
            deleteTVShow.getActors().clear();
            deleteTVShow.getDistributors().clear();
            ///??????
            tvShowDAO.deleteById(id);
            tvShowDAO.flush();
        }
    }

    public TVShow saveActorToTVShow(Long tvShow_id, Long actor_id) {
        TVShow origin_tvShow = findById(tvShow_id);
        Actor actor = actorService.findById(actor_id);
        if (origin_tvShow == null){
            throw new NoSuchElementException("TVShow with id = " + tvShow_id + " does not exist");
        } else if(actor == null){
            throw new NoSuchElementException("Actor with id = " + actor_id + " does not exist");
        }else{
            origin_tvShow.getActors().add(actor);
            actor.getTvShows().add(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            actorService.save(actor);
            return origin_tvShow;
        }
    }

    public TVShow saveDistributorToTVShow(Long tvShow_id, Long distributor_id) {
        TVShow origin_tvShow = findById(tvShow_id);
        Distributor distributor = distributorService.findDistributorById(distributor_id);
        if (origin_tvShow == null){
            throw new NoSuchElementException("TVShow with id = " + tvShow_id + " does not exist");
        } else if(distributor == null){
            throw new NoSuchElementException("Distributor with id = " + distributor_id + " does not exist");
        }else{
            origin_tvShow.getDistributors().add(distributor);
            distributor.getTvShows().add(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            distributorService.save(distributor);
            return origin_tvShow;
        }
    }
}
