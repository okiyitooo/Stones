package com.SecondPersonalProject.Stones.models;

import java.awt.Color;

import com.SecondPersonalProject.Stones.serializers.StoneSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = StoneSerializer.class)
public class Stone {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long stoneId;
	
	private String name;

    private RockType type;

    private double weight;

    @Column(nullable = false)
    private Color color;

    private String description;

    @ManyToOne
    private Person person;
    
    public enum RockType {
        IGNEOUS,
        SEDIMENTARY,
        METAMORPHIC,
        SUPER
    }
}
