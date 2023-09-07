package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.exceptions.*;
import com.moviesdb.moviesdb.models.*;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.persistence.DirectorDAO;
import com.moviesdb.moviesdb.persistence.TVShowDAO;
import com.moviesdb.moviesdb.services.human.ActorServiceImpl;
import com.moviesdb.moviesdb.services.nonhuman.DistributorServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TVShowServiceImpl implements WatchableService {
    private final TVShowDAO tvShowDAO;
    private final DistributorServiceImpl distributorService;
    private final DirectorDAO directorDAO;
    private final ActorServiceImpl actorService;

    public TVShowServiceImpl(TVShowDAO tvShowDAO,
                             @Lazy DistributorServiceImpl distributorService,
                             DirectorDAO directorDAO, @Lazy ActorServiceImpl actorService) {
        this.tvShowDAO = tvShowDAO;
        this.distributorService = distributorService;
        this.directorDAO = directorDAO;
        this.actorService = actorService;
    }

    @Override
    public TVShow findById(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("Id doesn't exist");
        }
        Optional<TVShow> tvShowOptional = tvShowDAO.findById(id);
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
    public WatchableBaseEntity update(WatchableBaseEntity watchable, Long id) throws WatchableNotFoundException {
        TVShow tvShow = (TVShow) watchable;
        if (tvShow == null) {
            throw new WatchableNotFoundException("Passed tvShow can't be null");
        } else if (id == null) {
            throw new IllegalArgumentException("Passed id of tvShow can't be null");
        }
        TVShow tvShowToUpdate = tvShowDAO.findById(id).orElse(null);

        if (tvShowToUpdate == null)
            throw new WatchableNotFoundException("TvShow to update can't be found");
        tvShowToUpdate.setName(tvShow.getName());
        tvShowToUpdate.setAgeRestriction(tvShow.getAgeRestriction());
        tvShowToUpdate.setDescription(tvShow.getDescription());
        tvShowToUpdate.setDirector(tvShow.getDirector());
        tvShowDAO.save(tvShowToUpdate);
        return tvShowToUpdate;
    }

    @Override
    public WatchableBaseEntity save(WatchableBaseEntity tvShow) throws WatchableNotFoundException {
        if (tvShow == null)
            throw new IllegalArgumentException("TVShow doesn't exist");
        return tvShowDAO.save((TVShow) tvShow);
    }

    @Override
    public void deleteDistributor(Long watchableId, Long distributorId) throws WatchableNotFoundException, NonHumanNotFoundException {
        TVShow origin_tvShow = findById(watchableId);
        Distributor distributor = distributorService.findDistributorById(distributorId);
        if (origin_tvShow == null) {
            throw new WatchableNotFoundException("TVShow with id = " + watchableId + " does not exist");
        } else if (distributor == null) {
            throw new NonHumanNotFoundException("Distributor with id = " + distributorId + " does not exist");

        } else if (!origin_tvShow.containsDistributor(distributor)) {
            throw new NoSuchElementException("TVShow with id = " + watchableId + " does not have distributor with id = " + distributorId);
        } else {
            origin_tvShow.getDistributors().remove(distributor);
            distributor.getTvShows().remove(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            distributorService.save(distributor);
        }
    }

    @Override
    public void deleteActor(Long watchableId, Long actorId) throws WatchableNotFoundException, HumanNotFoundException, HasNotValueException {
        if (watchableId == null || watchableId <= 0) {
            throw new IllegalArgumentException("TvShowId is not correct");
        } else if (actorId == null || actorId <= 0) {
            throw new IllegalArgumentException("ActorId is not correct");
        } else {
            TVShow origin_tvShow = findById(watchableId);
            Actor actor = actorService.findById(actorId);
            if (origin_tvShow == null) {
                throw new WatchableNotFoundException("TVSHow with id = " + watchableId + " does not exist");
            } else if (actor == null) {
                throw new HumanNotFoundException("Actor with id = " + actorId + " does not exist");
            } else if (!origin_tvShow.containsActor(actor)) {
                throw new HasNotValueException("Actor with id = " + actorId + " have not played in TV show with id = " + watchableId);
            } else {
                origin_tvShow.getActors().remove(actor);
                actor.getTvShows().remove(origin_tvShow);
                tvShowDAO.save(origin_tvShow);
                actorService.save(actor);
            }
        }
    }

    @Override
    public void addDirector(Long watchId, Director director) throws AlreadyHasValueException, WatchableNotFoundException, HumanNotFoundException {
        TVShow tvShow = tvShowDAO.findById(watchId).orElse(null);
        if (director == null)
            throw new HumanNotFoundException("Director is null");
        if (tvShow == null)
            throw new WatchableNotFoundException("TVShow can`t be null or it doesn't have director");
        if (tvShow.getDirector() != null)
            throw new AlreadyHasValueException("Movie already has director");

        tvShow.addDirector(director);
        tvShowDAO.save(tvShow);
        directorDAO.save(director);
    }

    @Override
    public void removeDirector(Long watchId) throws WatchableNotFoundException, HasNotValueException {
        TVShow tvShow = tvShowDAO.findById(watchId).orElse(null);
        if (tvShow.getDirector() == null) {
            throw new HasNotValueException("TvShow doesn't have director");
        } else if (tvShow == null) {
            throw new WatchableNotFoundException("TvShow can`t be null");
        }
        Director director = tvShow.getDirector();
        tvShow.removeDirector();
        directorDAO.save(director);
        tvShowDAO.save(tvShow);
    }

    @Override
    public void deleteById(Long id) throws WatchableNotFoundException {
        TVShow deleteTVShow = findById(id);
        if (deleteTVShow == null) {
            throw new WatchableNotFoundException("TVShow with id = " + id + " does not exist");
        } else {
            for (Distributor distributor : deleteTVShow.getDistributors()) {
                distributor.getTvShows().remove(deleteTVShow);
            }
            for (Actor actor : deleteTVShow.getActors()) {
                actor.getTvShows().remove(deleteTVShow);
            }

            deleteTVShow.getActors().clear();
            deleteTVShow.getDistributors().clear();

            tvShowDAO.deleteById(id);
            tvShowDAO.flush();
        }
    }

    @Override
    public List<WatchableBaseEntity> findByName(String name) {
        if (name == null || name.equals(""))
            throw new RuntimeException("Name can't null");
        List<TVShow> tvShows = tvShowDAO.findByName(name);
        return new ArrayList<>(tvShows);
    }

    @Override
    public void addActor(Long watchId, Long actorId) throws WatchableNotFoundException, HumanNotFoundException {
        TVShow origin_tvShow = findById(watchId);
        Actor actor = actorService.findById(actorId);
        if (origin_tvShow == null) {
            throw new WatchableNotFoundException("TVShow with id = " + watchId + " does not exist");
        } else if (actor == null) {
            throw new HumanNotFoundException("Actor with id = " + actorId + " does not exist");
        } else {
            origin_tvShow.getActors().add(actor);
            actor.getTvShows().add(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            actorService.save(actor);
        }
    }

    @Override
    public void addDistributor(Long watchId, Long distributorId) throws HumanNotFoundException, WatchableNotFoundException, NonHumanNotFoundException {
        TVShow origin_tvShow = findById(watchId);
        Distributor distributor = distributorService.findDistributorById(distributorId);
        if (origin_tvShow == null) {
            throw new WatchableNotFoundException("TVShow with id = " + watchId + " does not exist");
        } else if (distributor == null) {
            throw new HumanNotFoundException("Distributor with id = " + distributorId + " does not exist");
        } else {
            origin_tvShow.getDistributors().add(distributor);
            distributor.getTvShows().add(origin_tvShow);
            tvShowDAO.save(origin_tvShow);
            distributorService.save(distributor);
        }
    }


}
