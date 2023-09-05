package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;

import java.util.List;

public interface WatchableService {
    WatchableBaseEntity findById(Long id);
    List<WatchableBaseEntity> findAll();
    WatchableBaseEntity save(WatchableBaseEntity watchable);
    WatchableBaseEntity update(WatchableBaseEntity watchable, Long id);
    List<WatchableBaseEntity> findByName(String name);
    void deleteById(Long id);
    void addActor(Long watchId,Long actorId);
    void addDistributor(Long watchId,Long distributorId);
    void deleteDistributor(Long watchableId, Long distributorId);
    void deleteActor(Long watchableId,Long actorId);
    void removeDirector(Long watchId);
    void addDirector(Long watchId, Director director);

}
