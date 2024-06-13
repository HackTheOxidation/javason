package io.github.hacktheoxidation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Base interface for JSON that allows for serialization and wrapping of java objects as JSON values.
 * @author HackTheOxidation
 */
public sealed interface JSON permits JSONBool, JSONString, JSONNumber, JSONNull, JSONArray, JSONObject {
    /**
     * The type of json value contained.
     * @return the type of the JSON value contained.
     */
    JSONType getType();

    /**
     * The usual toString() method. Note, that this does NOT return serialized json,
     * but rather a representation of the java object itself.
     * @return a string representation of the JSON object.
     */
    @Override
    String toString();

    /**
     * Serializes the java object to a valid json string representation.
     * This is the method that you want to use and NOT toString().
     * @return a serialization of the json object.
     */
    String serialize();

    /**
     * Factory method for creating JSON objects from java primitives.
     * @param value - a java primitive to wrap in a JSON object.
     * @throws IllegalArgumentException - if parsed a non-primitive java value.
     * @return the input value wrapped in a JSON object.
     */
    static JSON of(Object value) {
        return switch (value) {
            case null -> new JSONNull();
            case Boolean b -> new JSONBool(b);
            case Integer i -> new JSONNumber(i);
            case Double d -> new JSONNumber(d);
            case String s -> new JSONString(s);
            case JSON j -> j;
            case List<?> l -> new JSONArray(l.stream().map(JSON::of).toList());
            case Map<?, ?> m -> new JSONObject(m
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Object::toString,
                            kv -> JSON.of(kv.getValue()), (a, b) -> b, HashMap::new)));
            default -> {
                throw new IllegalArgumentException("Cannot convert unexpected value to JSON: " + value);
            }
        };
    }
}
