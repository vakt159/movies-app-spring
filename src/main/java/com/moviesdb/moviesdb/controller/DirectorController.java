package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.ActorDTOConverter;
import com.moviesdb.moviesdb.DTOs.converters.DirectorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.DTOs.dto.DirectorDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.services.human.HumanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/director","director"})
public class DirectorController {

    private final HumanService directorService;

    public DirectorController(@Qualifier("directorServiceImpl") HumanService directorService) {
        this.directorService = directorService;
    }


    @GetMapping("/all")
    public @ResponseBody List<DirectorDTO> getAll() {

        List<HumanBaseEntity> directors = directorService.findAll();
        List<DirectorDTO> directorDTOS = new ArrayList<>();
        for (HumanBaseEntity director : directors){
            directorDTOS.add(DirectorDTOConverter.todirectorDTO((Director)director));
        }
        return directorDTOS;
    }

    @GetMapping("/find/{id}")
    public @ResponseBody DirectorDTO findById(@PathVariable(value = "id") Long id) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.findById(id));
    }

    @PostMapping("/save")
    public @ResponseBody DirectorDTO add(@RequestBody Director director) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.save(director));
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) {
        directorService.deleteById(id);
    }

    @PutMapping("/{id}/update")
    public @ResponseBody DirectorDTO edit(@RequestBody Director director, @PathVariable(value = "id") Long id) {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.update(director,id));
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
