package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class TVShowController {

    private final WatchableService tvShowService;
    public TVShowController(@Qualifier("TVShowServiceImpl")
                                 WatchableService twShowService) {
        this.tvShowService = twShowService;
    }

    @GetMapping("/tvShows")
    public @ResponseBody List<WatchableBaseEntity> getAll() {
        return tvShowService.findAll();
    }

    @GetMapping("/tvShows/{id}")
    public @ResponseBody TVShow getTvShowService(@PathVariable Long id) {
        TVShow tvShow = (TVShow) tvShowService.findById(id);
        if (tvShow == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return tvShow;
        }
    }
    @PutMapping("/tvShows/{id}/update")
    public @ResponseBody TVShow edit(@RequestBody TVShow tvShow, @PathVariable(value = "id") Long id) {
        return (TVShow) tvShowService.update(tvShow,id);
    }
    @PostMapping("/tvShows/save")
    public @ResponseBody TVShow saveTVShow(@RequestBody TVShow tvShow) {
        return (TVShow) tvShowService.save(tvShow);
    }

    @DeleteMapping("/tvShows/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        tvShowService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> TVShowException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
