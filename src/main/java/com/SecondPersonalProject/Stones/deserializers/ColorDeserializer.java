package com.SecondPersonalProject.Stones.deserializers;

import java.awt.Color;
import java.io.IOException;

import com.SecondPersonalProject.Stones.exceptions.JsonProcessingColorException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class ColorDeserializer extends JsonDeserializer<Color> {

	@Override
	public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String colorHex = p.getValueAsString();
        try {
            return Color.decode(colorHex);
        } catch (NumberFormatException e) {
            throw new JsonProcessingColorException("Invalid color value: " + colorHex, p.getCurrentLocation(), e);
        }
    }

}
