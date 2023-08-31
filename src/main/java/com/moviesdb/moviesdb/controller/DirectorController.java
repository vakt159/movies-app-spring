package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.services.human.DirectorServiceImpl;
import com.moviesdb.moviesdb.services.human.HumanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/director","director"})
public class DirectorController {

    private final DirectorServiceImpl directorService;

    public DirectorController( DirectorServiceImpl directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Director> getAll() {
        return directorService.findAll();
    }

    @GetMapping("/find/{id}")
    public @ResponseBody Director findById(@PathVariable(value = "id") Long id) {
        return directorService.findById(id);
    }

    @PostMapping("/save")
    public @ResponseBody Director add(@RequestBody Director director) {
        return directorService.save(director);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) {
        directorService.deleteById(id);
    }

    @PutMapping("/{id}/update")
    public @ResponseBody Director edit(@RequestBody Director director, @PathVariable(value = "id") Long id) {
        return directorService.update(director,id);
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
}
