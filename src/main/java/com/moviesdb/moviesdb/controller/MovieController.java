package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.MovieServiceImpl;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class MovieController {
    private final WatchableService movieService;

    public MovieController(    @Qualifier("movieServiceImpl")
                               WatchableService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public @ResponseBody List<WatchableBaseEntity> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/movies/{id}")
    public @ResponseBody Movie getMovie(@PathVariable Long id) {
        Movie movie = (Movie) movieService.findById(id);
        if (movie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            return movie;
        }
    }
    @PostMapping("/movies/save")
    public @ResponseBody Movie saveMovie(@RequestBody Movie movie) {

        return (Movie) movieService.save(movie);
    }

    @DeleteMapping("/movies/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PutMapping("/movies/{movieId}/distributor/{distId}/delete")
    public void deleteDistributorById(@PathVariable Long movieId, @PathVariable Long distId)
    {
        movieService.deleteDistributor(movieId,distId);
    }
    @PutMapping("/movies/{id}/update")
    public @ResponseBody Movie edit(@RequestBody Movie movie, @PathVariable(value = "id") Long id) {
        return (Movie) movieService.update(movie,id);
    }
    @PutMapping("/movies/{movieId}/actor/{actorId}/delete")
    public void deleteActorById(@PathVariable Long movieId, @PathVariable Long actorId)
    {
        movieService.deleteActor(   movieId,actorId);
    }
    @ExceptionHandler
    public ResponseEntity<String> MovieException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
