package xyz.hacktheoxidation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SimpleLexerTest {

    @Test
    public void testTokenizeEmptyString() {
        var lexer = new SimpleLexer("");
        var expected = new ArrayList<Lexer.Pair>();
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleNull() {
        var lexer = new SimpleLexer("{ \"foo\": null }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NULL, "null"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleTrue() {
        var lexer = new SimpleLexer("{ \"foo\": true }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.BOOL, "true"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleFalse() {
        var lexer = new SimpleLexer("{ \"foo\": false }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.BOOL, "false"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleIntNumber() {
        var lexer = new SimpleLexer("{ \"foo\": 0 }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "0"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeNegativeIntNumber() {
        var lexer = new SimpleLexer("{ \"foo\": -1 }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "-1"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleFloatNumber() {
        var lexer = new SimpleLexer("{ \"foo\": 42.3 }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "42.3"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeNegativeFloatNumber() {
        var lexer = new SimpleLexer("{ \"foo\": -42.3 }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "-42.3"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleEmptyString() {
        var lexer = new SimpleLexer("{ \"foo\": \"\" }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.STRING, ""),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleString() {
        var lexer = new SimpleLexer("{ \"foo\": \"bar\" }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.STRING, "bar"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyArray() {
        var lexer = new SimpleLexer("{ \"foo\": [] }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.LBRACE, "["),
                        new Lexer.Pair(Tokens.RBRACE, "]"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeSimpleArray() {
        var lexer = new SimpleLexer("{ \"foo\": [42.3] }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.LBRACE, "["),
                        new Lexer.Pair(Tokens.NUMBER, "42.3"),
                        new Lexer.Pair(Tokens.RBRACE, "]"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeComplicatedArray() {
        var lexer = new SimpleLexer("{ \"foo\": [42.3, true] }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.LBRACE, "["),
                        new Lexer.Pair(Tokens.NUMBER, "42.3"),
                        new Lexer.Pair(Tokens.COMMA, ","),
                        new Lexer.Pair(Tokens.BOOL, "true"),
                        new Lexer.Pair(Tokens.RBRACE, "]"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeEmptyObject() {
        var lexer = new SimpleLexer("{}");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeNestedObject() {
        var lexer = new SimpleLexer("{ \"obj\": { \"foo\": 42.3 } }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "obj"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "42.3"),
                        new Lexer.Pair(Tokens.RCURLY, "}"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }

    @Test
    public void testTokenizeFlatObject() {
        var lexer = new SimpleLexer("{ \"foo\": true, \"bar\": 42.3 }");
        var expected = new ArrayList<>(
                Arrays.asList(
                        new Lexer.Pair(Tokens.LCURLY, "{"),
                        new Lexer.Pair(Tokens.STRING, "foo"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.BOOL, "true"),
                        new Lexer.Pair(Tokens.COMMA, ","),
                        new Lexer.Pair(Tokens.STRING, "bar"),
                        new Lexer.Pair(Tokens.COLON, ":"),
                        new Lexer.Pair(Tokens.NUMBER, "42.3"),
                        new Lexer.Pair(Tokens.RCURLY, "}")));
        var actual = lexer.tokenize();

        assertEquals(expected, actual);
    }
}