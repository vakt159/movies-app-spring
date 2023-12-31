package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.DirectorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.DirectorDTO;
import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.HumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Director;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.services.human.HumanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
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
    @GetMapping("/directors/find")
    public @ResponseBody DirectorDTO findByFirstLastName(@RequestBody Map<String,String> fullname) throws HumanNotFoundException {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.
                findByFirstNameAndLastName(fullname.get("firstname"),fullname.get("lastname")));
    }

    @GetMapping("/directors/id/{id}")
    public @ResponseBody DirectorDTO findById(@PathVariable(value = "id") Long id) throws HumanNotFoundException {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.findById(id));
    }

    @PostMapping("/directors/save")
    public @ResponseBody DirectorDTO save(@Valid @RequestBody Director director) throws HumanNotFoundException {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.save(director));

    }

    @DeleteMapping("/directors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) throws HumanNotFoundException {
        directorService.deleteById(id);
    }

    @PutMapping("/directors/{id}/update")
    public @ResponseBody DirectorDTO update(@Valid @RequestBody Director director, @PathVariable(value = "id") Long id) throws HumanNotFoundException {
        return DirectorDTOConverter.todirectorDTO((Director) directorService.update(director,id));
    }

    @PutMapping("/directors/{directorId}/movie/{movieId}/add")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long directorId) throws WatchableNotFoundException, AlreadyHasValueException, HumanNotFoundException {
        directorService.addMovie(movieId, directorId);
    }

    @PutMapping("/directors/{directorId}/TVShow/{tvShowId}/add")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long directorId) throws WatchableNotFoundException, AlreadyHasValueException, HumanNotFoundException {
        directorService.addTVShow(tvShowId, directorId);
    }

    @PutMapping("/directors/{id}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId) throws WatchableNotFoundException, HasNotValueException, HumanNotFoundException {
        directorService.deleteMovie(id, movieId);
    }

    @PutMapping("/directors/{id}/TVShow/{TVShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId) throws WatchableNotFoundException, HasNotValueException, HumanNotFoundException {
        directorService.deleteTVShow(id, TVShowId);
    }

}
