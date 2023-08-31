package com.moviesdb.moviesdb.controller;


import com.moviesdb.moviesdb.DTOs.converters.DistributorDTOConverter;
import com.moviesdb.moviesdb.DTOs.converters.TVShowDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.DistributorDTO;
import com.moviesdb.moviesdb.DTOs.dto.TVShowDTO;
import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.models.superclasses.NonHumanBaseEntity;
import com.moviesdb.moviesdb.models.superclasses.WatchableBaseEntity;
import com.moviesdb.moviesdb.services.nonhuman.NonHumanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class DistributorController {

    private final NonHumanService nonHumanService;

    public DistributorController(@Qualifier("distributorServiceImpl") NonHumanService nonHumanService) {
        this.nonHumanService = nonHumanService;
    }

    @GetMapping("/distributors")
    public @ResponseBody List<DistributorDTO> getAll() {
        List<NonHumanBaseEntity> distributors = nonHumanService.findAll();
        List<DistributorDTO> distributorDTOS = new ArrayList<>();
        for (NonHumanBaseEntity distributor : distributors) {
            distributorDTOS.add(DistributorDTOConverter.todistributorDTO((Distributor) distributor));
        }
        return distributorDTOS;
    }

    @GetMapping("/distributors/findByName")
    public @ResponseBody Distributor findByName(@RequestBody Map<String, String> body) {
        return (Distributor) nonHumanService.findByName(body.get("name"));
    }

    @GetMapping("/distributors/{id}")
    public @ResponseBody DistributorDTO findById(@PathVariable(value = "id") Long id) {
        Distributor distributor = (Distributor) nonHumanService.findDistributorById(id);
        if (distributor == null) {
            throw new NoSuchElementException("TV show with id = " + id + " does not exist");
        } else {
            return DistributorDTOConverter.todistributorDTO(distributor);
        }
    }

    @PostMapping("/distributors/save")
    public @ResponseBody DistributorDTO add(@Valid @RequestBody Distributor distributor) {
        return DistributorDTOConverter.todistributorDTO((Distributor) nonHumanService.save(distributor));
    }

    @DeleteMapping("/distributors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id) {
        nonHumanService.deleteDistributorById(id);
    }

    @PutMapping("/distributors/{id}/update")
    public @ResponseBody Distributor edit(@Valid @RequestBody Distributor distributor, @PathVariable(value = "id") Long id) {
        return (Distributor) nonHumanService.update(id, distributor);
    }

    @PutMapping("/distributors/{distributorId}/movie/{movieId}/add")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long distributorId) {
        nonHumanService.addMovie(movieId, distributorId);
    }

    ;

    @PutMapping("/distributors/{distributorId}/TVShow/{tvShowId}/add")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long distributorId) {
        nonHumanService.addTVShow(tvShowId, distributorId);
    }

    ;

    @PutMapping("/distributors/{id}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId) {
        nonHumanService.deleteMovie(id, movieId);
    }

    ;

    @PutMapping("/distributors/{id}/TVShow/{TVShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId) {
        nonHumanService.deleteTVShow(id, TVShowId);
    }

    ;

    @ExceptionHandler
    public ResponseEntity<String> onMissingDistributor(NoSuchElementException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ": no one distributor was found");
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
