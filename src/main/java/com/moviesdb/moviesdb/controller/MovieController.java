package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.MovieServiceImpl;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/movie", "movie"})
public class MovieController {
    private final WatchableService movieService;

    public MovieController(    @Qualifier("movieServiceImpl")
                               WatchableService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public @ResponseBody List<WatchableBaseEntity> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Movie getMovie(@PathVariable Long id) {
        Movie movie = (Movie) movieService.findById(id);
        if (movie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            return movie;
        }
    }
    @PostMapping
    public @ResponseBody Movie saveMovie(@Valid @RequestBody Movie movie) {

        return (Movie) movieService.save(movie);
    }
    @PutMapping("{id}/update")
    public @ResponseBody Movie updateById(@Valid @RequestBody Movie movie, @PathVariable Long id)
    {
        return (Movie) movieService.update(movie,id);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PutMapping("{movieId}/delete/distributor/{distId}")
    public void deleteDistributorById(@PathVariable Long movieId, @PathVariable Long distId)
    {
        movieService.deleteDistributor(movieId,distId);
    }
    @PutMapping("{movieId}/delete/actor/{actorId}")
    public void deleteActorById(@PathVariable Long movieId, @PathVariable Long actorId)
    {
        movieService.deleteActor(   movieId,actorId);
    }
    @ExceptionHandler
    public ResponseEntity<String> MovieException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
