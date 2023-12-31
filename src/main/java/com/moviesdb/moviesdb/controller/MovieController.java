package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.MovieDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.MovieDTO;
import com.moviesdb.moviesdb.exceptions.*;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
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

    @GetMapping("/movies/id/{id}")
    public @ResponseBody MovieDTO findById(@PathVariable Long id) throws WatchableNotFoundException {
        Movie movie = (Movie) movieService.findById(id);
        if (movie == null) {
            throw new NoSuchElementException("Movie with id = " + id + " does not exist");
        } else {
            return MovieDTOConverter.tomovieDTO(movie);
        }
    }
    @PostMapping("/movies/save")
    public @ResponseBody MovieDTO save(@Valid @RequestBody Movie movie) throws WatchableNotFoundException {
        return MovieDTOConverter.tomovieDTO((Movie) movieService.save(movie));
    }
    @DeleteMapping("/movies/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) throws WatchableNotFoundException {
        movieService.deleteById(id);
    }
    @PutMapping("/movies/{id}/update")
    public @ResponseBody MovieDTO update(@RequestBody Movie movie, @PathVariable(value = "id") Long id) throws WatchableNotFoundException {
        return MovieDTOConverter.tomovieDTO((Movie)movieService.update(movie,id));
    }
    @PutMapping("/movies/{movieId}/distributor/{distId}/delete")
    public @ResponseBody void deleteDistributor(@PathVariable Long movieId, @PathVariable Long distId) throws AlreadyHasValueException, HasNotValueException, WatchableNotFoundException, NonHumanNotFoundException {
        movieService.deleteDistributor(movieId,distId);
    }

    @PutMapping("/movies/{movieId}/actor/{actorId}/delete")
    public @ResponseBody void deleteActor(@PathVariable Long movieId, @PathVariable Long actorId) throws HasNotValueException, WatchableNotFoundException, HumanNotFoundException {
        movieService.deleteActor(movieId,actorId);
    }
    @PutMapping("/movies/{movieId}/actor/{actorId}/add")
    public @ResponseBody void addActor(@PathVariable Long movieId, @PathVariable Long actorId) throws WatchableNotFoundException, HumanNotFoundException {
        movieService.addActor(movieId,actorId);
    }
    @PutMapping("/movies/{movieId}/distributor/{distId}/add")
    public @ResponseBody void addDistributor(@PathVariable Long movieId, @PathVariable Long distId) throws WatchableNotFoundException, HumanNotFoundException, NonHumanNotFoundException {
        movieService.addDistributor(movieId,distId);
    }
    @GetMapping("/movies/name/{name}")
    public @ResponseBody List<MovieDTO> findByName(@PathVariable String name)
    {
        List<WatchableBaseEntity> movies = movieService.findByName(name);
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (WatchableBaseEntity movie : movies){
            movieDTOS.add(MovieDTOConverter.tomovieDTO((Movie)movie));
        }
        return movieDTOS;
    }
}
