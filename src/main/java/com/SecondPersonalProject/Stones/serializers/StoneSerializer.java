package com.SecondPersonalProject.Stones.serializers;

import java.io.IOException;

import com.SecondPersonalProject.Stones.models.Stone;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StoneSerializer extends JsonSerializer<Stone> {

	@Override
	public void serialize(Stone stone, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		gen.writeStartObject();

			gen.writeNumberField("stoneId", stone.getStoneId());
	        gen.writeStringField("name", stone.getName());
	        gen.writeStringField("type", stone.getType().toString());
	        gen.writeNumberField("weight", stone.getWeight());
        
	        gen.writeObjectFieldStart("color");
		        gen.writeNumberField("red", stone.getColor().getRed());
		        gen.writeNumberField("green", stone.getColor().getGreen());
		        gen.writeNumberField("blue", stone.getColor().getBlue());
	        gen.writeEndObject(); 
        
	        gen.writeStringField("description", stone.getDescription());

	        gen.writeObjectFieldStart("person");
	        	if (stone.getPerson()!=null)
	        		gen.writeNumberField("personId", stone.getPerson().getPersonId());
	        gen.writeEndObject();

        gen.writeEndObject(); 
	}

}
