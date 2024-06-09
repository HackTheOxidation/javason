package io.github.hacktheoxidation;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveDescentParserTest {

    @Test
    public void parseEmptyObject() {
        var lexer = new SimpleLexer("{}");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>()).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseNull() {
        var lexer = new SimpleLexer("{ \"foo\": null }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNull()))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseTrue() {
        var lexer = new SimpleLexer("{ \"foo\": true }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONBool(true)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseFalse() {
        var lexer = new SimpleLexer("{ \"foo\": false }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONBool(false)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseZero() {
        var lexer = new SimpleLexer("{ \"foo\": 0 }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(0)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parsePositiveNumber() {
        var lexer = new SimpleLexer("{ \"foo\": 42.3 }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(42.3)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseNegativeNumber() {
        var lexer = new SimpleLexer("{ \"foo\": -42.3 }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONNumber(-42.3)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyString() {
        var lexer = new SimpleLexer("{ \"foo\": \"\" }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONString("")))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseString() {
        var lexer = new SimpleLexer("{ \"foo\": \"bar\" }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONString("bar")))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseEmptyArray() {
        var lexer = new SimpleLexer("{ \"foo\": [] }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(new HashMap<>(Map.of("foo", new JSONArray(new ArrayList<>())))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseSimpleArray() {
        var lexer = new SimpleLexer("{ \"foo\": [42.3] }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(
                new HashMap<>(
                        Map.of("foo",
                                new JSONArray(new ArrayList<>(
                                        List.of(new JSONNumber(42.3))
                                ))))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseComplicatedArray() {
        var lexer = new SimpleLexer("{ \"foo\": [42.3, true] }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(
                new HashMap<>(
                        Map.of("foo",
                                new JSONArray(new ArrayList<>(
                                        List.of(new JSONNumber(42.3),
                                                new JSONBool(true))
                                ))))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseNestedObject() {
        var lexer = new SimpleLexer("{ \"obj\": { \"foo\": 42.3 } }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(
                new HashMap<>(
                        Map.of("obj",
                                new JSONObject(new HashMap<>(
                                        Map.of("foo",
                                                new JSONNumber(42.3))
                                ))))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }

    @Test
    public void parseFlatObject() {
        var lexer = new SimpleLexer("{ \"foo\": true, \"bar\": 42.3 }");
        var parser = new RecursiveDescentParser(lexer);
        var expected = new JSONObject(
                new HashMap<>(
                        Map.of("foo", new JSONBool(true),
                               "bar", new JSONNumber(42.3)))).toString();

        var actual = parser.parse().toString();

        assertEquals(expected, actual);
    }
}