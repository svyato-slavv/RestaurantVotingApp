package ru.ivanov.restaurantvotingapplication.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtil {
    private static ObjectMapper objectMapper;

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JsonUtil.objectMapper = objectMapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) throws IOException {
        ObjectReader reader = objectMapper.readerFor(clazz);
        return reader.<T>readValues(json).readAll();
    }

    public static <T> T readValue(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public static <T> String writeValue(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        return writeAdditionProps(obj, Map.of(addName, addValue));
    }

    public static <T> String writeAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = objectMapper.convertValue(obj, new TypeReference<>() {
        });
        map.putAll(addProps);
        return writeValue(map);
    }
}
