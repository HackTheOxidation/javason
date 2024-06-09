package io.github.hacktheoxidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONNullTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.NULL, new JSONNull().getType());
    }

    @Test
    public void testNullToString() {
        var jsonNull = new JSONNull();
        var expected = "JSONNull{}";
        var actual = jsonNull.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testNullSerialization() {
        var jsonNull = new JSONNull();
        var expected = "null";
        var actual = jsonNull.serialize();

        assertEquals(expected, actual);
    }

}