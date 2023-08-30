package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.services.watchable.TVShowServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/tvShow", "tvShow"})
public class TVShowController {
    private final TVShowServiceImpl tvShowService;

    public TVShowController(TVShowServiceImpl tvShowService) {
        this.tvShowService = tvShowService;
    }

    @GetMapping("/all")
    public @ResponseBody List<TVShow> getAll() {
        return tvShowService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody TVShow getTvShowService(@PathVariable Long id) {
        TVShow tvShow = tvShowService.findById(id);
        if (tvShow == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return tvShow;
        }
    }

    @PostMapping()
    public @ResponseBody TVShow saveTVShow(@RequestBody TVShow tvShow) {
        return tvShowService.save(tvShow);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        tvShowService.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> TVShowException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
