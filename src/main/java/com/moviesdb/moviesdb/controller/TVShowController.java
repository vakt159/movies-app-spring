package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.MovieDTOConverter;
import com.moviesdb.moviesdb.DTOs.converters.TVShowDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.MovieDTO;
import com.moviesdb.moviesdb.DTOs.dto.TVShowDTO;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.Director;

import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
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
public class TVShowController {

    private final WatchableService tvShowService;
    public TVShowController(@Qualifier("TVShowServiceImpl")
                                 WatchableService twShowService) {
        this.tvShowService = twShowService;
    }

    @GetMapping("/tvShows")
    public @ResponseBody List<TVShowDTO> getAll() {
        List<WatchableBaseEntity> tvShows = tvShowService.findAll();
        List<TVShowDTO> tvShowDTOS = new ArrayList<>();
        for (WatchableBaseEntity tvShow : tvShows){
            tvShowDTOS.add(TVShowDTOConverter.totvShowDTO((TVShow) tvShow));
        }
        return tvShowDTOS;
    }

    @GetMapping("/tvShows/{id}")
    public @ResponseBody TVShowDTO getTvShowService(@PathVariable Long id) {
        TVShow tvShow = (TVShow) tvShowService.findById(id);
        if (tvShow == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return TVShowDTOConverter.totvShowDTO(tvShow);
        }
    }
    @PostMapping("/tvShows/save")
    public @ResponseBody TVShowDTO saveTVShow(@Valid @RequestBody TVShow tvShow) {
        return TVShowDTOConverter.totvShowDTO((TVShow) tvShowService.save(tvShow));

    }
    @PutMapping("/tvShow/{id}/update")
    public @ResponseBody TVShow updateById(@Valid @RequestBody TVShow tvShow, @PathVariable Long id)
    {
        return (TVShow) tvShowService.update(tvShow,id);
    }

    @DeleteMapping("/tvShows/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        tvShowService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> TVShowException(NoSuchElementException ex) {
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
