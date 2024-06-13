package io.github.hacktheoxidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of a json parser using the classic recursive
 * descent parsing technique. It makes use of dependency injection such
 * that the user is free to choose whatever lexer they see fit.
 */
public class RecursiveDescentParser implements Parser {
    private final Lexer lexer;

    public RecursiveDescentParser(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public List<Lexer.Pair> getTokens() {
        return lexer.tokenize();
    }

    /**
     * Attempts to parse a string and wrap it in a JSON object.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for strings (indicating an error in the lexer).
     * @return a parsed string wrapped in a JSON object.
     */
    private JSONString parseString(List<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.STRING) {
            throw new ParserException();
        }
        return new JSONString(cursor.value());
    }

    /**
     * Attempts to parse a number and wrap it in a JSON object.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for numbers (indicating an error in the lexer).
     * @return a parsed number wrapped in a JSON object.
     */
    private JSONNumber parseNumber(List<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.NUMBER) {
            throw new ParserException();
        }
        return new JSONNumber(Double.parseDouble(cursor.value()));
    }

    /**
     * Attempts to parse a boolean and wrap it in a JSON object.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for booleans (indicating an error in the lexer).
     * @return a parsed boolean wrapped in a JSON object.
     */
    private JSONBool parseBool(List<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.BOOL) {
            throw new ParserException();
        }
        return new JSONBool(cursor.value().equals("true"));
    }

    /**
     * Attempts to parse a null and wrap it in a JSON object.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for null (indicating an error in the lexer).
     * @return a parsed null wrapped in a JSON object.
     */
    private JSONNull parseNull(List<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.NULL) {
            throw new ParserException();
        }
        return new JSONNull();
    }

    /**
     * Attempts to parse an array and wrap it in a JSON object. It uses other parser
     * utility functions to recursively parse the elements of the array.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for arrays (indicating an error in the lexer).
     * @return a parsed array wrapped in a JSON object.
     */
    private JSONArray parseArray(List<Lexer.Pair> tokenPairs) {
        if (tokenPairs.isEmpty()) {
            throw new ParserException();
        }

        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.LBRACE) {
            throw new ParserException();
        }

        var arrayElements = new ArrayList<JSON>();
        cursor = tokenPairs.getFirst();
        while (Objects.requireNonNull(cursor.token()) != Tokens.RBRACE) {
            if (tokenPairs.isEmpty()) {
                throw new ParserException();
            }

            arrayElements.add(this.parseAnyValue(tokenPairs));

            if (Objects.requireNonNull(tokenPairs.getFirst().token()) == Tokens.COMMA) {
                tokenPairs.removeFirst();
            }

            cursor = tokenPairs.getFirst();
        }

        tokenPairs.removeFirst();

        return new JSONArray(arrayElements);
    }

    /**
     * Attempts to parse an object and wrap it in a JSON object. It uses other parser
     * utility functions to recursively parse the key-value pairs of the object.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules for objects (indicating an error in the lexer).
     * @return a parsed object wrapped in a JSON object.
     */
    private JSONObject parseObject(List<Lexer.Pair> tokenPairs) {
        if (tokenPairs.isEmpty()) {
            throw new ParserException();
        }

        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.LCURLY) {
            throw new ParserException();
        }

        var objectFields = new HashMap<String, JSON>();
        cursor = tokenPairs.getFirst();
        while (Objects.requireNonNull(cursor.token()) != Tokens.RCURLY) {
            if (tokenPairs.isEmpty()) {
                throw new ParserException();
            }

            var fieldName = this.parseString(tokenPairs);
            cursor = tokenPairs.removeFirst();
            if (Objects.requireNonNull(cursor.token()) != Tokens.COLON) {
                throw new ParserException();
            }

            var fieldValue = this.parseAnyValue(tokenPairs);
            objectFields.put(fieldName.value(), fieldValue);

            if (Objects.requireNonNull(tokenPairs.getFirst().token()) == Tokens.COMMA) {
                tokenPairs.removeFirst();
            }

            cursor = tokenPairs.getFirst();
        }

        tokenPairs.removeFirst();

        return new JSONObject(objectFields);
    }

    /**
     * Attempts to parse the next token in the sequence as a json value by acting as
     * a dispatcher to the other parser utility methods. It has the responsibility to
     * determine what value is most appropriate to try and parse.
     * @param tokenPairs tokenizer result to consume.
     * @throws ParserException if the sequence of tokens does not match the json syntax rules (indicating an error in the lexer).
     * @return a parsed value wrapped in a JSON object.
     */
    private JSON parseAnyValue(List<Lexer.Pair> tokenPairs) {
        return switch (tokenPairs.getFirst().token()) {
            case BOOL -> this.parseBool(tokenPairs);
            case NULL -> this.parseNull(tokenPairs);
            case NUMBER -> this.parseNumber(tokenPairs);
            case STRING -> this.parseString(tokenPairs);
            case LBRACE -> this.parseArray(tokenPairs);
            case LCURLY -> this.parseObject(tokenPairs);
            default -> throw new ParserException();
        };
    }

    @Override
    public JSON parse() {
        return this.parseObject(this.getTokens());
    }
}