package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Distributor;
import com.moviesdb.moviesdb.models.Movie;
import com.moviesdb.moviesdb.models.TVShow;
import com.moviesdb.moviesdb.services.human.DistributorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Distributor add(@RequestBody Distributor distributor)
    {
        return distributorService.save(distributor);
    }
    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable(value = "id") Long id)
    {
        distributorService.deleteDistributorById(id);
    }
    @PutMapping("/{id}/update")
    public @ResponseBody Distributor edit(@RequestBody Distributor distributor, @PathVariable(value = "id") Long id)
    {
        return distributorService.update(id,distributor);
    }

    @PutMapping("/{id}/add/movie")
    public void addMovie(@RequestBody Movie movie, @PathVariable Long id){
        distributorService.addMovie(movie, id);
    };

    @PutMapping("/{id}/add/TVShow")
    public void addTVShow(@RequestBody TVShow tvShow, @PathVariable Long id){
        distributorService.addTVShow(tvShow, id);
    };

    @PutMapping("/{id}/delete/movie/{movieId}")
    public void deleteMovie(@PathVariable Long id, @PathVariable Long movieId){
        distributorService.deleteMovie(id,movieId);
    };
    @PutMapping("/{id}/delete/TVShow/{TVShowId}")
    public void deleteTVShow(@PathVariable Long id, @PathVariable Long TVShowId){
        distributorService.deleteTVShow(id,TVShowId);
    };

    @ExceptionHandler
    public ResponseEntity<String> onMissingDistributor(NoSuchElementException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ": no one distributor was found");
    }

}
