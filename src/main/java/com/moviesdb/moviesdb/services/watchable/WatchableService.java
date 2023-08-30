package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;

import java.util.List;

public interface WatchableService {
    WatchableBaseEntity findById(Long id);
    List<WatchableBaseEntity> findAll();
    WatchableBaseEntity save(WatchableBaseEntity watchable);
    WatchableBaseEntity update(WatchableBaseEntity watchable, Long id);
    void deleteDistributor(Long watchableId, Long distributorId);
    void deleteActor(Long watchableId,Long actorId);
    void deleteById(Long id);
}
