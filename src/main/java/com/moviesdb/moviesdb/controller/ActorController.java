package com.moviesdb.moviesdb.controller;

import com.moviesdb.moviesdb.DTOs.converters.ActorDTOConverter;
import com.moviesdb.moviesdb.DTOs.dto.ActorDTO;
import com.moviesdb.moviesdb.exceptions.AlreadyHasValueException;
import com.moviesdb.moviesdb.exceptions.HasNotValueException;
import com.moviesdb.moviesdb.exceptions.HumanNotFoundException;
import com.moviesdb.moviesdb.exceptions.WatchableNotFoundException;
import com.moviesdb.moviesdb.models.Actor;
import com.moviesdb.moviesdb.models.superclasses.HumanBaseEntity;
import com.moviesdb.moviesdb.s3.S3Service;
import com.moviesdb.moviesdb.services.human.HumanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class ActorController {
    private final HumanService actorService;
    private final S3Service s3;

    public ActorController(@Qualifier("actorServiceImpl") HumanService actorService, S3Service s31) {
        this.actorService = actorService;
        this.s3 = s31;
    }

    @GetMapping("/actors")
    public @ResponseBody List<ActorDTO> findAll() {
        List<HumanBaseEntity> actors = actorService.findAll();
        List<ActorDTO> actorDTOS = new ArrayList<>();

        s3.putObject("movies-app-bucket", "actor1Image", "Hello, World".getBytes());
        System.out.println(Arrays.toString(s3.getObject("movies-app-bucket", "foo")));

        for (HumanBaseEntity actor : actors){
            actorDTOS.add(ActorDTOConverter.toactorDTO((Actor)actor));
        }
        return actorDTOS;
    }

    @GetMapping("/actors/{id}")
    public @ResponseBody ActorDTO findById(@PathVariable Long id) throws HumanNotFoundException {
        Actor actor = (Actor)actorService.findById(id);
        if (actor == null) {
            throw new NoSuchElementException("Actor with id = " + id + " does not exist");
        } else {
            return ActorDTOConverter.toactorDTO(actor);
        }
    }

    @PostMapping("/actors/save")
    public @ResponseBody ActorDTO save(@RequestBody Actor actor) throws HumanNotFoundException {
        return ActorDTOConverter.toactorDTO((Actor)actorService.save(actor));

    }

    @GetMapping("/actors/find")
    public @ResponseBody ActorDTO findByFirstLastName(@RequestBody Map<String,String> fullname) throws HumanNotFoundException {
        return ActorDTOConverter.toactorDTO((Actor)actorService
                .findByFirstNameAndLastName(fullname.get("firstname"), fullname.get("lastname")));
    }

    @DeleteMapping("/actors/{id}/delete")
    public @ResponseBody void deleteById(@PathVariable Long id) throws HumanNotFoundException {
        actorService.deleteById(id);
    }
    @PutMapping("/actors/{id}/update")
    public @ResponseBody ActorDTO edit(@Valid @RequestBody Actor actor,@PathVariable Long id) throws HumanNotFoundException {
        return ActorDTOConverter.toactorDTO((Actor) actorService.update(actor, id));
    }

    @DeleteMapping("/actors/{actorId}/tvShow/{tvShowId}/delete")
    public @ResponseBody void deleteTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId) throws WatchableNotFoundException, HasNotValueException, HumanNotFoundException {
        actorService.deleteTVShow(actorId,tvShowId);
    }

    @PutMapping("/actors/{actorId}/tvShow/{tvShowId}/save")
    public @ResponseBody void addTVShow(@PathVariable Long actorId, @PathVariable Long tvShowId) throws WatchableNotFoundException, AlreadyHasValueException, HumanNotFoundException {
         actorService.addTVShow(actorId,tvShowId);
    }
    @DeleteMapping("/actors/{actorId}/movie/{movieId}/delete")
    public @ResponseBody void deleteMovie(@PathVariable Long actorId, @PathVariable Long movieId) throws WatchableNotFoundException, HasNotValueException, HumanNotFoundException {
        actorService.deleteTVShow(actorId,movieId);
    }

    @PutMapping("/actors/{actorId}/movie/{movieId}/save")
    public @ResponseBody void addMovie(@PathVariable Long actorId, @PathVariable Long movieId) throws WatchableNotFoundException, AlreadyHasValueException, HumanNotFoundException {
        actorService.addMovie(actorId,movieId);
    }




}
