package com.SecondPersonalProject.Stones.config;

import java.awt.Color;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.SecondPersonalProject.Stones.deserializers.ColorDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JackSonConfig {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Color.class, new ColorDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}
