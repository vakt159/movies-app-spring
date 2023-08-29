package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.services.actor.ActorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @GetMapping("/find")
    public @ResponseBody Actor findByFirstLastName(@RequestBody String firstName,String lastName)
    {
        return actorService.findActorByFirstNameAndLastName(firstName,lastName);
    }
}
