package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.TVShowDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.TVShowDTO;
import com.moviesdb.moviesdb.exceptions.*;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.watchable.WatchableService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TVShowController {

    private final WatchableService tvShowService;
    public TVShowController(@Qualifier("TVShowServiceImpl")
                                 WatchableService twShowService) {
        this.tvShowService = twShowService;
    }

    @GetMapping("/tvShows")
    public @ResponseBody List<TVShowDTO> findAll() {
        List<WatchableBaseEntity> tvShows = tvShowService.findAll();
        List<TVShowDTO> tvShowDTOS = new ArrayList<>();
        for (WatchableBaseEntity tvShow : tvShows){
            tvShowDTOS.add(TVShowDTOConverter.totvShowDTO((TVShow) tvShow));
        }
        return tvShowDTOS;
    }

    @GetMapping("/tvShows/id/{id}")
    public @ResponseBody TVShowDTO findById(@PathVariable Long id) throws WatchableNotFoundException {
        TVShow tvShow = (TVShow) tvShowService.findById(id);
        if (tvShow == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return TVShowDTOConverter.totvShowDTO(tvShow);
        }
    }
    @PostMapping("/tvShows/save")
    public @ResponseBody TVShowDTO save(@Valid @RequestBody TVShow tvShow) throws WatchableNotFoundException {
        System.out.println("------------ : " + tvShow.getTotalEpisodes());
        System.out.println("------------ : " + tvShow.getActors());
        return TVShowDTOConverter.totvShowDTO((TVShow) tvShowService.save(tvShow));

    }
    @GetMapping("/tvShows/name/{name}")
    public @ResponseBody List<TVShowDTO> findByName(@PathVariable String name)
    {
        List<WatchableBaseEntity> tvShows = tvShowService.findByName(name);
        List<TVShowDTO> tvShowDTOS = new ArrayList<>();
        for (WatchableBaseEntity tvShow : tvShows){
            tvShowDTOS.add(TVShowDTOConverter.totvShowDTO((TVShow) tvShow));
        }
        return tvShowDTOS;
    }
    @PutMapping("/tvShows/{id}/update")
    public @ResponseBody TVShowDTO update(@Valid @RequestBody TVShow tvShow, @PathVariable Long id) throws WatchableNotFoundException {
        return TVShowDTOConverter.totvShowDTO((TVShow) tvShowService.update(tvShow,id));
    }
    @PutMapping("/tvShows/{tvShowId}/distributor/{distId}/delete")
    public @ResponseBody void deleteDistributor(@PathVariable Long tvShowId, @PathVariable Long distId) throws AlreadyHasValueException, HasNotValueException, WatchableNotFoundException, NonHumanNotFoundException {
        tvShowService.deleteDistributor(tvShowId,distId);
    }
    @PutMapping("/tvShows/{tvShowId}/actor/{actorId}/delete")
    public @ResponseBody void deleteActor(@PathVariable Long tvShowId, @PathVariable Long actorId) throws HasNotValueException, WatchableNotFoundException, HumanNotFoundException {
        tvShowService.deleteActor(tvShowId,actorId);
    }
    @PutMapping("/tvShows/{tvShowId}/actor/{actorId}/add")
    public @ResponseBody void addActor(@PathVariable Long tvShowId, @PathVariable Long actorId) throws WatchableNotFoundException, HumanNotFoundException {
        tvShowService.addActor(tvShowId,actorId);
    }
    @PutMapping("/tvShows/{tvShowId}/distributor/{distId}/add")
    public @ResponseBody void addDistributor(@PathVariable Long tvShowId, @PathVariable Long distId) throws WatchableNotFoundException, HumanNotFoundException, NonHumanNotFoundException {
        tvShowService.addDistributor(tvShowId,distId);
    }

    @DeleteMapping("/tvShows/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) throws WatchableNotFoundException {
        tvShowService.deleteById(id);
    }

}
