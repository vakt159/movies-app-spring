package com.moviesdb.moviesdb.controller;


import com.moviesdb.moviesdb.DTOs.converters.DistributorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.DistributorDTO;
import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.NonHumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import com.moviesdb.moviesdb.services.nonhuman.NonHumanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class DistributorController {

    private final NonHumanService nonHumanService;

    public DistributorController(@Qualifier("distributorServiceImpl") NonHumanService nonHumanService) {
        this.nonHumanService = nonHumanService;
    }

    @GetMapping("/distributors")
    public @ResponseBody List<DistributorDTO> findAll() {
        List<NonHumanBaseEntity> distributors = nonHumanService.findAll();
        List<DistributorDTO> distributorDTOS = new ArrayList<>();
        for (NonHumanBaseEntity distributor : distributors) {
            distributorDTOS.add(DistributorDTOConverter.todistributorDTO((Distributor) distributor));
        }
        return distributorDTOS;
    }

    //Not implemented
    @GetMapping("/distributors/name")
    public @ResponseBody Distributor findByName(@RequestBody Map<String,String> name) {
        return (Distributor) nonHumanService.findByName(name.get("name"));
    }


    @GetMapping("/distributors/id/{id}")
    public @ResponseBody DistributorDTO findById(@PathVariable(value = "id") Long id) throws NonHumanNotFoundException {
        Distributor distributor = (Distributor) nonHumanService.findDistributorById(id);
        if (distributor == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return DistributorDTOConverter.todistributorDTO(distributor);
        }
    }

    @PostMapping("/distributors/save")
    public @ResponseBody DistributorDTO save(@Valid @RequestBody Distributor distributor) throws NonHumanNotFoundException {
        return DistributorDTOConverter.todistributorDTO((Distributor) nonHumanService.save(distributor));
    }

    @DeleteMapping("/distributors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) throws NonHumanNotFoundException {
        nonHumanService.deleteById(id);
    }

    @PutMapping("/distributors/{id}/update")
    public @ResponseBody DistributorDTO update(@Valid @RequestBody Distributor distributor, @PathVariable(value = "id") Long id) throws NonHumanNotFoundException {
        return DistributorDTOConverter.todistributorDTO((Distributor) nonHumanService.update(id, distributor));
    }

    @PutMapping("/distributors/{distributorId}/movie/{movieId}/add")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long distributorId) throws AlreadyHasValueException, WatchableNotFoundException, NonHumanNotFoundException {
        nonHumanService.addMovie(movieId, distributorId);
    }

    ;

    @PutMapping("/distributors/{distributorId}/TVShow/{tvShowId}/add")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long distributorId) throws AlreadyHasValueException, WatchableNotFoundException, NonHumanNotFoundException {
        nonHumanService.addTVShow(tvShowId, distributorId);
    }

    @PutMapping("/distributors/{id}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId) throws HasNotValueException, WatchableNotFoundException, NonHumanNotFoundException {
        nonHumanService.deleteMovie(id, movieId);
    }

    @PutMapping("/distributors/{id}/TVShow/{TVShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId) throws HasNotValueException, WatchableNotFoundException, NonHumanNotFoundException {
        nonHumanService.deleteTVShow(id, TVShowId);
    }
}
