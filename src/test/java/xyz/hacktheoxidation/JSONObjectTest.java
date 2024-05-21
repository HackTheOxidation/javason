package xyz.hacktheoxidation;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JSONObjectTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.OBJECT, new JSONObject(new HashMap<>()).getType());
    }

    @Test
    public void testEmptyObjectToString() {
        var jsonObject = new JSONObject(new HashMap<>());
        var expected = "JSONObject{value={}}";
        var actual = jsonObject.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyObjectSerialization() {
        var jsonObject = new JSONObject(new HashMap<>());
        var expected = "{}";
        var actual = jsonObject.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testSingleElementObjectToString() {
        var jsonObject = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3))));
        var expected = "JSONObject{value={foo=JSONNumber{value=42.3}}}";
        var actual = jsonObject.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testSingleElementObjectSerialization() {
        var jsonObject = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3))));
        var expected = "{\"foo\": 42.3}";
        var actual = jsonObject.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultiElementObjectToString() {
        var jsonObject = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3), "bar", new JSONBool(true))));
        var expected = "JSONObject{value={bar=JSONBool{value=true}, foo=JSONNumber{value=42.3}}}";
        var actual = jsonObject.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testMultiElementObjectSerialization() {
        var jsonObject = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3), "bar", new JSONBool(true))));
        var expected = "{\"bar\": true, \"foo\": 42.3}";
        var actual = jsonObject.serialize();

        assertEquals(expected, actual);
    }
}