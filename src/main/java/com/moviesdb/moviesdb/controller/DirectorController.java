package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.ActorDTOConverter;
import com.moviesdb.moviesdb.DTOs.converters.DirectorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.DTOs.dto.DirectorDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.services.human.HumanService;
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
public class DirectorController {

    private final HumanService directorService;

    public DirectorController(@Qualifier("directorServiceImpl") HumanService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    public @ResponseBody List<DirectorDTO> findAll() {

        List<HumanBaseEntity> directors = directorService.findAll();
        List<DirectorDTO> directorDTOS = new ArrayList<>();
        for (HumanBaseEntity director : directors){
            directorDTOS.add(DirectorDTOConverter.todirectorDTO((Director)director));
        }
        return directorDTOS;
    }

    @GetMapping("/directors/{id}")
    public @ResponseBody DirectorDTO findById(@PathVariable(value = "id") Long id) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.findById(id));
    }

    @PostMapping("/directors/save")
    public @ResponseBody DirectorDTO save(@Valid @RequestBody Director director) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.save(director));

    }

    @DeleteMapping("/directors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) {
        directorService.deleteById(id);
    }

    @PutMapping("/directors/{id}/update")
    public @ResponseBody DirectorDTO update(@Valid @RequestBody Director director, @PathVariable(value = "id") Long id) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.update(director,id));
    }

    @PutMapping("/directors/{directorId}/movie/{movieId}/add")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long directorId) {
        directorService.addMovie(movieId, directorId);
    }

    @PutMapping("/directors/{directorId}/TVShow/{tvShowId}/add")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long directorId) {
        directorService.addTVShow(tvShowId, directorId);
    }

    @PutMapping("/directors/{id}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId) {
        directorService.deleteMovie(id, movieId);
    }

    @PutMapping("/directors/{id}/TVShow/{TVShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId) {
        directorService.deleteTVShow(id, TVShowId);
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingDirector(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ": no director was found");
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
