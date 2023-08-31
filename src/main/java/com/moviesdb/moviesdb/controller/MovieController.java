package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.MovieDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.MovieDTO;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
public class MovieController {
    private final WatchableService movieService;

    public MovieController(    @Qualifier("movieServiceImpl")
                               WatchableService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public @ResponseBody List<MovieDTO> findAll() {
        List<WatchableBaseEntity> movies = movieService.findAll();
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (WatchableBaseEntity movie : movies){
            movieDTOS.add(MovieDTOConverter.tomovieDTO((Movie)movie));
        }
        return movieDTOS;
    }

    @GetMapping("/movies/{id}")
    public @ResponseBody MovieDTO findById(@PathVariable Long id) {
        Movie movie = (Movie) movieService.findById(id);
        if (movie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            return MovieDTOConverter.tomovieDTO(movie);
        }
    }
    @PostMapping("/movies/save")
    public @ResponseBody MovieDTO save(@Valid @RequestBody Movie movie) {
        return MovieDTOConverter.tomovieDTO((Movie) movieService.save(movie));
    }
    @DeleteMapping("/movies/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        movieService.deleteById(id);
    }

    @PutMapping("/movies/{movieId}/distributor/{distId}/delete")
    public void deleteDistributor(@PathVariable Long movieId, @PathVariable Long distId)
    {
        movieService.deleteDistributor(movieId,distId);
    }
    @PutMapping("/movies/{id}/update")
    public @ResponseBody Movie update(@RequestBody Movie movie, @PathVariable(value = "id") Long id) {
        return (Movie) movieService.update(movie,id);
    }
    @PutMapping("/movies/{movieId}/actor/{actorId}/delete")
    public void deleteActor(@PathVariable Long movieId, @PathVariable Long actorId)
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
