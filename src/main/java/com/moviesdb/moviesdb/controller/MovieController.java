package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.services.watchable.MovieServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/movie", "movie"})
public class MovieController {
    private final MovieServiceImpl movieService;

    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Movie> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Movie getMovie(@PathVariable Long id) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            return movie;
        }
    }
    @PostMapping
    public @ResponseBody Movie saveMovie(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PutMapping("movie/{movieId}/delete/distributor/{distId}")
    public void deleteDistributorById(@PathVariable Long movieId, @PathVariable Long distId)
    {
        movieService.deleteDistributor(movieId,distId);
    }
    @PutMapping("movie/{movieId}/delete/actor/{actorId}")
    public void deleteActorById(@PathVariable Long movieId, @PathVariable Long actorId)
    {
        movieService.deleteActor(   movieId,actorId);
    }
    @ExceptionHandler
    public ResponseEntity<String> MovieException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
