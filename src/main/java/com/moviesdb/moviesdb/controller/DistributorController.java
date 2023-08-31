package com.moviesdb.moviesdb.controller;


import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.services.nonhuman.DistributorService;
import jakarta.validation.Valid;
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
@RequestMapping({"/distributor","distributor"})
public class DistributorController {

    private final DistributorService distributorService;

    public DistributorController(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Distributor> getAll()
    {
        return distributorService.findAll();
    }
    @GetMapping("/find")
    public @ResponseBody Distributor findByName(@RequestBody Map<String,String> body)
    {
        return distributorService.findByName(body.get("name"));
    }
    @GetMapping("/find/{id}")
    public @ResponseBody Distributor findById(@PathVariable(value = "id") Long id)
    {
        return distributorService.findDistributorById(id);
    }
    @PostMapping("/save")
    public @ResponseBody Distributor add(@Valid  @RequestBody Distributor distributor)
    {
        return distributorService.save(distributor);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id)
    {
        distributorService.deleteDistributorById(id);
    }
    @PutMapping("/{id}/update")
    public @ResponseBody Distributor edit(@Valid @RequestBody Distributor distributor, @PathVariable(value = "id") Long id)
    {
        return distributorService.update(id,distributor);
    }

    @PutMapping("/{distributorId}/add/movie/{movieId}")
    public @ResponseBody void addMovie(@PathVariable Long movieId, @PathVariable Long distributorId){
        distributorService.addMovie(movieId, distributorId);
    };

    @PutMapping("/{distributorId}/add/TVShow/{tvShowId}")
    public @ResponseBody void addTVShow(@PathVariable Long tvShowId, @PathVariable Long distributorId){
        distributorService.addTVShow(tvShowId, distributorId);
    };

    @PutMapping("/{id}/delete/movie/{movieId}")
    public @ResponseBody void deleteMovie(@PathVariable Long id, @PathVariable Long movieId){
        distributorService.deleteMovie(id,movieId);
    };
    @PutMapping("/{id}/delete/TVShow/{TVShowId}")
    public @ResponseBody void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId){
        distributorService.deleteTVShow(id,TVShowId);
    };

    @ExceptionHandler
    public ResponseEntity<String> onMissingDistributor(NoSuchElementException ex){

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
