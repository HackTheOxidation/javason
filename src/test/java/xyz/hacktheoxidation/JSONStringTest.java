package xyz.hacktheoxidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONStringTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.STRING, new JSONString("foo").getType());
    }

    @Test
    public void testNonemptyStringToString() {
        var jsonString = new JSONString("foo");
        var expected = "JSONString{value=\"foo\"}";
        var actual = jsonString.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testNonemptyStringSerialization() {
        var jsonString = new JSONString("foo");
        var expected = "\"foo\"";
        var actual = jsonString.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyStringToString() {
        var jsonString = new JSONString("");
        var expected = "JSONString{value=\"\"}";
        var actual = jsonString.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testEmptyStringSerialization() {
        var jsonString = new JSONString("");
        var expected = "\"\"";
        var actual = jsonString.serialize();

        assertEquals(expected, actual);
    }

}