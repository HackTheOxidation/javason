package io.github.hacktheoxidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONNumberTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.NUMBER, new JSONNumber(42.3).getType());
    }

    @Test
    public void testNumberToString() {
        var jsonNumber = new JSONNumber(42.3);
        var expected = "JSONNumber{value=42.3}";
        var actual = jsonNumber.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testNumberSerialization() {
        var jsonNumber = new JSONNumber(42.3);
        var expected = "42.3";
        var actual = jsonNumber.serialize();

        assertEquals(expected, actual);
    }

}