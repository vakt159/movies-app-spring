package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.exceptions.*;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;

import java.util.List;

public interface WatchableService {
    WatchableBaseEntity findById(Long id) throws WatchableNotFoundException;
    List<WatchableBaseEntity> findAll();
    WatchableBaseEntity save(WatchableBaseEntity watchable) throws WatchableNotFoundException;
    WatchableBaseEntity update(WatchableBaseEntity watchable, Long id) throws WatchableNotFoundException;
    List<WatchableBaseEntity> findByName(String name);
    void deleteById(Long id) throws WatchableNotFoundException;
    void addActor(Long watchId,Long actorId) throws WatchableNotFoundException, HumanNotFoundException;
    void addDistributor(Long watchId,Long distributorId) throws WatchableNotFoundException, HumanNotFoundException, NonHumanNotFoundException;
    void deleteDistributor(Long watchableId, Long distributorId) throws NonHumanNotFoundException, WatchableNotFoundException, AlreadyHasValueException, HasNotValueException;
    void deleteActor(Long watchableId,Long actorId) throws HumanNotFoundException, WatchableNotFoundException, HasNotValueException;
    void removeDirector(Long watchId) throws HasNotValueException, WatchableNotFoundException;
    void addDirector(Long watchId, Director director) throws AlreadyHasValueException, WatchableNotFoundException, HumanNotFoundException;

}
