package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;

import java.util.List;

public interface WatchableService <T extends WatchableBaseEntity> {
    T findById(Long id);
    List<T> findAll();
    void deleteById(Long id);

    T save(T watchable);
    T update(T watchable, Long id);
    void deleteDistributor(Long watchableId, Long distributorID);
    void deleteActor(Long watchableId,Long actorId);
}
