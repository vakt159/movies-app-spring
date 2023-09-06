package com.moviesdb.moviesdb.persistence;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DirectorDAO extends JpaRepository<Director,Long> {
    @Query("from Director d where d.firstName = :firstName AND d.lastName = :lastName")
    Optional<Director> findDirectorByFirstNameAndLastName(@Param("firstName") String firstName,
                                                          @Param("lastName") String lastName);
}
