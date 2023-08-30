package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Distributor;
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
        return distributorService.findById(id);
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

    @ExceptionHandler
    public ResponseEntity<String> onMissingDistributor(NoSuchElementException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage() + ": no one distributor was found");
    }

}
