package com.SecondPersonalProject.Stones.dtos;

import java.awt.Color;

import com.SecondPersonalProject.Stones.models.Person;
import com.SecondPersonalProject.Stones.models.Stone.RockType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoneDTO {

	private String name;

    private RockType type;

    private double weight;

    private Color color;

    private String description;

    private Person person;
}
