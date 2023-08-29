package com.moviesdb.moviesdb.services.watchable;

import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;

import java.util.List;

public interface WatchableService <T extends WatchableBaseEntity> {
    T findById(Long id);
    List<T> findAll();
    T save(T watchable);
    T update(T watchable, Long id);
}
