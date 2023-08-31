package com.moviesdb.moviesdb.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/director","director"})
public class DirectorController {

    private final HumanService directorService;

    public DirectorController(@Qualifier("directorServiceImpl") HumanService directorService) {
        this.directorService = directorService;
    }


    @GetMapping("/all")
    public @ResponseBody List<HumanBaseEntity> getAll() {
        return directorService.findAll();
    }

    @GetMapping("/find/{id}")
    public @ResponseBody Director findById(@PathVariable(value = "id") Long id) {
        return (Director) directorService.findById(id);
    }

    @PostMapping("/save")
    public @ResponseBody Director add( @Valid  @RequestBody Director director) {
        return (Director) directorService.save(director);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) {
        directorService.deleteById(id);
    }

    @PutMapping("/{id}/update")
    public @ResponseBody Director edit(@Valid @RequestBody Director director, @PathVariable(value = "id") Long id) {
        return (Director) directorService.update(director,id);
    }

    @PutMapping("/{directorId}/add/movie/{movieId}")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long directorId) {
        directorService.addMovie(movieId, directorId);
    }

    @PutMapping("/{directorId}/add/TVShow/{tvShowId}")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long directorId) {
        directorService.addTVShow(tvShowId, directorId);
    }

    @PutMapping("/{id}/delete/movie/{movieId}")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId) {
        directorService.deleteMovie(id, movieId);
    }

    @PutMapping("/{id}/delete/TVShow/{TVShowId}")
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
