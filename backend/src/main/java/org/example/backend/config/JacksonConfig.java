package org.example.backend.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.example.backend.domain.CraftRecipeSerializer;
import org.example.backend.domain.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JacksonConfig {
    @Bean
    public ObjectMapper jsonObjectMapper() {
        ArrayList<Module> modules = new ArrayList<>();

        //CollectionType Serialization
        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Map.class, new CraftRecipeSerializer());
        Map<Item, Integer> map = new HashMap<>();
        simpleModule.addSerializer((Class<? extends Map<Item, Integer>>) map.getClass(), new CraftRecipeSerializer());
        modules.add(simpleModule);

        return Jackson2ObjectMapperBuilder.json()
                .modules(modules)
                .build();
    }
}
