package com.moviesdb.moviesdb.services.human;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;

import java.util.List;

public interface HumanService<T extends HumanBaseEntity> {
    T findById(Long id);
    List<T> findAll();
    T save(T human);
}
