package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.services.human.ActorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/actor","actor"})
public class ActorController {
    private final ActorServiceImpl actorService;

    public ActorController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Actor> getAll()
    {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Actor getActor(@PathVariable Long id) {
        return actorService.findById(id);
    }
    @PostMapping()
    public @ResponseBody Actor saveActor(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    @GetMapping("/find")
    public @ResponseBody Actor findByFirstLastName(@RequestBody String firstName,String lastName)
    {
        return actorService.findActorByFirstNameAndLastName(firstName,lastName);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody Actor deleteById(@PathVariable Long id)
    {
        return actorService.deleteById(id);
    }
}
