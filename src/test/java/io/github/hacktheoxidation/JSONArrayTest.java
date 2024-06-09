package io.github.hacktheoxidation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONArrayTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.ARRAY, new JSONArray(new ArrayList<>()).getType());
    }

    @Test
    public void testEmptyArrayToString() {
        var jsonArray = new JSONArray(new ArrayList<>());
        var expected = "JSONArray{value=[]}";
        var actual = jsonArray.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyArraySerialization() {
        var jsonArray = new JSONArray(new ArrayList<>());
        var expected = "[]";
        var actual = jsonArray.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testSingleElementArrayToString() {
        var jsonArray = new JSONArray(new ArrayList<>(List.of(new JSONBool(true))));
        var expected = "JSONArray{value=[JSONBool{value=true}]}";
        var actual = jsonArray.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testSingleElementArraySerialization() {
        var jsonArray = new JSONArray(new ArrayList<>(List.of(new JSONBool(true))));
        var expected = "[true]";
        var actual = jsonArray.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultiElementArrayToString() {
        var jsonArray = new JSONArray(new ArrayList<>(Arrays.asList(new JSONBool(true), new JSONNumber(42.3))));
        var expected = "JSONArray{value=[JSONBool{value=true}, JSONNumber{value=42.3}]}";
        var actual = jsonArray.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultiElementArraySerialization() {
        var jsonArray = new JSONArray(new ArrayList<>(Arrays.asList(new JSONBool(true), new JSONNumber(42.3))));
        var expected = "[true, 42.3]";
        var actual = jsonArray.serialize();

        assertEquals(expected, actual);
    }
}