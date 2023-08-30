package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.services.human.ActorServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;

@Controller
@RequestMapping({"/actor", "actor"})
public class ActorController {
    private final ActorServiceImpl actorService;

    public ActorController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/all")
    public @ResponseBody List<Actor> getAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Actor getActor(@PathVariable Long id) {
        Actor actor = actorService.findById(id);
        if (actor == null) {
            throw new NoSuchElementException("Actor with id = " + id + " does not exist");
        } else {
            return actor;
        }
    }

    @PostMapping
    public @ResponseBody Actor saveActor(@RequestBody Actor actor) {
        return actorService.save(actor);
    }

    @GetMapping("/find")
    public @ResponseBody Actor findByFirstLastName(@RequestBody String firstName, String lastName) {
        Actor actor = actorService.findActorByFirstNameAndLastName(firstName, lastName);
        if (actor == null) {
            throw new NoSuchElementException("Actor with first name = " + firstName + "and last name = " + lastName + " does not exist");
        } else {
            return actorService.findActorByFirstNameAndLastName(firstName, lastName);
        }
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        actorService.deleteById(id);
    }
    @PutMapping("{id}/update")
    public @ResponseBody Actor updateById(@RequestBody Actor actor,@PathVariable Long id)
    {
        return actorService.update(actor, id);
    }

    @DeleteMapping("{actorId}/delete/tvShow/{tvShowId}")
    public @ResponseBody void deleteTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId){
        actorService.deleteTVShowFromActor(actorId,tvShowId);
    }

    @PutMapping("{actorId}/save/tvShow/{tvShowId}")
    public @ResponseBody Actor saveTVShowToActor(@PathVariable Long actorId, @PathVariable Long tvShowId){
        return actorService.saveTVShowToActor(actorId,tvShowId);
    }

    @ExceptionHandler
    public ResponseEntity<String> ActorException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
