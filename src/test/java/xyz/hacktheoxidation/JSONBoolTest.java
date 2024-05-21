package xyz.hacktheoxidation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONBoolTest {

    @Test
    public void testGetType() {
        assertEquals(JSONType.BOOL, new JSONBool(true).getType());
    }

    @Test
    public void testTrueToString() {
        var jsonTrue = new JSONBool(true);
        var expected = "JSONBool{value=true}";
        var actual = jsonTrue.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testFalseToString() {
        var jsonFalse = new JSONBool(false);
        var expected = "JSONBool{value=false}";
        var actual = jsonFalse.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testTrueSerialization() {
        var jsonTrue = new JSONBool(true);
        var expected = "true";
        var actual = jsonTrue.serialize();

        assertEquals(expected, actual);
    }

    @Test
    public void testFalseSerialization() {
        var jsonFalse = new JSONBool(false);
        var expected = "false";
        var actual = jsonFalse.serialize();

        assertEquals(expected, actual);
    }
}