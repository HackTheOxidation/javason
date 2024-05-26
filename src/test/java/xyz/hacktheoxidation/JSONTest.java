package xyz.hacktheoxidation;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JSONTest {

    @Test
    public void testOfNull() {
        var expected = new JSONNull();
        var actual = JSON.of(null);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfBool() {
        var expected = new JSONBool(true);
        var actual = JSON.of(true);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfNumber() {
        var expected = new JSONNumber(42.3);
        var actual = JSON.of(42.3);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfString() {
        var expected = new JSONString("foo");
        var actual = JSON.of("foo");

        assertEquals(expected, actual);
    }

    @Test
    public void testOfArrayEmpty() {
        var expected = new JSONArray(new ArrayList<>());
        var actual = JSONArray.of();

        assertEquals(expected, actual);
    }

    @Test
    public void testOfArraySingle() {
        var expected = new JSONArray(new ArrayList<>(List.of(new JSONBool(true))));
        var actual = JSONArray.of(true);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfArrayMulti() {
        var expected = new JSONArray(new ArrayList<>(Arrays.asList(new JSONBool(true), new JSONNumber(42.3))));
        var actual = JSONArray.of(true, 42.3);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfObjectEmpty() {
        var expected = new JSONObject(new HashMap<>());
        var actual = JSONObject.of();

        assertEquals(expected, actual);
    }

    @Test
    public void testOfObjectSingle() {
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3))));
        var actual = JSONObject.of("foo", 42.3);

        assertEquals(expected, actual);
    }

    @Test
    public void testOfObjectMulti() {
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3), "bar", new JSONBool(true))));
        var actual = JSONObject.of("foo", 42.3, "bar", true);

        assertEquals(expected, actual);
    }
}