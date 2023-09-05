package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.ActorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.services.human.HumanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.HttpStatus;

@Controller
public class ActorController {
    private final HumanService actorService;

    public ActorController(@Qualifier("actorServiceImpl") HumanService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public @ResponseBody List<ActorDTO> findAll() {
        List<HumanBaseEntity> actors = actorService.findAll();
        List<ActorDTO> actorDTOS = new ArrayList<>();
        for (HumanBaseEntity actor : actors){
            actorDTOS.add(ActorDTOConverter.toactorDTO((Actor)actor));
        }
        return actorDTOS;
    }

    @GetMapping("/actors/{id}")
    public @ResponseBody ActorDTO findById(@PathVariable Long id) {
        Actor actor = (Actor)actorService.findById(id);
        if (actor == null) {
            throw new NoSuchElementException("Actor with id = " + id + " does not exist");
        } else {
            return ActorDTOConverter.toactorDTO(actor);
        }
    }

    @PostMapping("/actors/save")
    public @ResponseBody ActorDTO save(@RequestBody Actor actor) {
        return ActorDTOConverter.toactorDTO((Actor)actorService.save(actor));

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
    public @ResponseBody ActorDTO edit(@Valid @RequestBody Actor actor,@PathVariable Long id)
    {
        return ActorDTOConverter.toactorDTO((Actor) actorService.update(actor, id));
    }

    @DeleteMapping("/actors/{actorId}/tvShow/{tvShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId){
        actorService.deleteTVShow(actorId,tvShowId);
    }

    @PutMapping("/actors/{actorId}/tvShow/{tvShowId}/save")
    public @ResponseBody void addTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId){
         actorService.addTVShow(actorId,tvShowId);
    }
    @DeleteMapping("/actors/{actorId}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long actorId, @PathVariable Long movieId){
        actorService.deleteTVShow(actorId,movieId);
    }

    @PutMapping("/actors/{actorId}/movie/{movieId}/save")
    public @ResponseBody void addMovie(@PathVariable Long actorId, @PathVariable Long movieId){
        actorService.addMovie(actorId,movieId);
    }

    @ExceptionHandler
    public ResponseEntity<String> ActorException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
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
