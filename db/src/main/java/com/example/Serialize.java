package com.example;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Serialize {
    public static <E> String serializeArray(ArrayList<E> classList, String key) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();

        ArrayNode arrayNode = objectNode.putArray(key);

        for (E object : classList) {
            String objectString = objectMapper.writeValueAsString(object);
            arrayNode.add(objectString);
        }

        String json = objectMapper.writeValueAsString(objectNode);

        return json;
    }
}
