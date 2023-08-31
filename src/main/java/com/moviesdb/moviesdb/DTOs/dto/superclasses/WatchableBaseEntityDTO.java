package com.moviesdb.moviesdb.DTOs.dto.superclasses;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchableBaseEntityDTO {

    private Long id;


    private String name;


    private String description;


    private Short ageRestriction;


    private Short duration;


    private Byte rating;

}
