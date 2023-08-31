package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.MovieDTOConverter;
import com.moviesdb.moviesdb.DTOs.converters.TVShowDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.MovieDTO;
import com.moviesdb.moviesdb.DTOs.dto.TVShowDTO;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping({"/tvShow", "tvShow"})
public class TVShowController {

    private final WatchableService tvShowService;
    public TVShowController(@Qualifier("TVShowServiceImpl")
                                 WatchableService twShowService) {
        this.tvShowService = twShowService;
    }

    @GetMapping("/all")
    public @ResponseBody List<TVShowDTO> getAll() {
        List<WatchableBaseEntity> tvShows = tvShowService.findAll();
        List<TVShowDTO> tvShowDTOS = new ArrayList<>();
        for (WatchableBaseEntity tvShow : tvShows){
            tvShowDTOS.add(TVShowDTOConverter.totvShowDTO((TVShow) tvShow));
        }
        return tvShowDTOS;
    }

    @GetMapping("/{id}")
    public @ResponseBody TVShowDTO getTvShowService(@PathVariable Long id) {
        TVShow tvShow = (TVShow) tvShowService.findById(id);
        if (tvShow == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return TVShowDTOConverter.totvShowDTO(tvShow);
        }
    }
    @PostMapping()
    public @ResponseBody TVShowDTO saveTVShow(@RequestBody TVShow tvShow) {
        return TVShowDTOConverter.totvShowDTO((TVShow) tvShowService.save(tvShow));
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
