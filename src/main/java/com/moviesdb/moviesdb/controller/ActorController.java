package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.ActorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.services.human.HumanService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;

@Controller
public class ActorController {
    private final HumanService actorService;

    public ActorController(@Qualifier("actorServiceImpl") HumanService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public @ResponseBody List<ActorDTO> getAll() {
        List<HumanBaseEntity> actors = actorService.findAll();
        List<ActorDTO> actorDTOS = new ArrayList<>();
        for (HumanBaseEntity actor : actors){
            actorDTOS.add(ActorDTOConverter.toactorDTO((Actor)actor));
        }
        return actorDTOS;
    }

    @GetMapping("/actors/{id}")
    public @ResponseBody Actor getActor(@PathVariable Long id) {
        Actor actor = (Actor)actorService.findById(id);
        if (actor == null) {
            throw new NoSuchElementException("Actor with id = " + id + " does not exist");
        } else {
            return actor;
        }
    }

    @PostMapping("/actors/save")
    public @ResponseBody Actor saveActor(@RequestBody Actor actor) {
        return (Actor)actorService.save(actor);
    }

//    @GetMapping("/find")
//    public @ResponseBody Actor findByFirstLastName(@RequestBody String firstName, String lastName) {
//        Actor actor = actorService.findActorByFirstNameAndLastName(firstName, lastName);
//        if (actor == null) {
//            throw new NoSuchElementException("Actor with first name = " + firstName + "and last name = " + lastName + " does not exist");
//        } else {
//            return actorService.findActorByFirstNameAndLastName(firstName, lastName);
//        }
//    }

    @DeleteMapping("/actors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) {
        actorService.deleteById(id);
    }
    @PutMapping("/actors/{id}/update")
    public @ResponseBody Actor updateById(@RequestBody Actor actor,@PathVariable Long id)
    {
        return (Actor) actorService.update(actor, id);
    }

    @DeleteMapping("/actors/{actorId}/tvShow/{tvShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId){
        actorService.deleteTVShow(actorId,tvShowId);
    }

    @PutMapping("/actors/{actorId}/tvShow/{tvShowId}/save")
    public @ResponseBody void saveTVShowToActor(@PathVariable Long actorId, @PathVariable Long tvShowId){
         actorService.addTVShow(actorId,tvShowId);
    }

    @ExceptionHandler
    public ResponseEntity<String> ActorException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
