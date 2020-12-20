package org.example.backend.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class CraftRecipeSerializer extends JsonSerializer<Map<Item, Integer>> {
    @Override
    public void serialize(Map<Item, Integer> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Map.Entry<Item, Integer> entry : value.entrySet()) {
            gen.writeStartObject();
            gen.writeObjectField("item", entry.getKey());
            gen.writeNumberField("amount", entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
    }
}
