package com.moviesdb.moviesdb.DTOs.dto.superclasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HumanBaseEntityDTO {

    private Long id;


    private String firstName;


    private String lastName;


    private String biography;

    private String birthday;

}
