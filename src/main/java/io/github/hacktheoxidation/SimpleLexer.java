package io.github.hacktheoxidation;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple lexer is a basic implementation of a lexer that accepts json as a string
 * and tokenizes it. The lexer is immutable and deterministic in the sense that
 * it retains a copy of the input which cannot be changed and the tokenization
 * process does not change the internal state of the lexer. To "change" the input,
 * one must create a new instance of the lexer.
 */
public class SimpleLexer implements Lexer {
    /**
     * This is the input that will be tokenized.
     */
    private final String jsonAsString;

    public SimpleLexer(String jsonAsString) {
        this.jsonAsString = jsonAsString;
    }

    /**
     * Utility method that moves the cursor of the lexer to the index of the next non-whitespace
     * character. Whitespace is defined as the space, tab, newline and carriage return characters.
     * @param cursor current index of the lexer in the input.
     * @return the new position of the cursor.
     */
    private int skipWhitespace(int cursor) {
        while (cursor < this.jsonAsString.length()) {
            switch (this.jsonAsString.charAt(cursor)) {
                case '\n', '\t', '\r', ' ':
                    cursor++;
                    break;
                default:
                    return cursor;
            }
        }

        return cursor;
    }

    /**
     * Utility method that attempts to parse a json string.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed a string.
     */
    private int tokenizeString(ArrayList<Pair> tokenPairs, int cursor) {
        int begin = ++cursor;
        while (this.jsonAsString.charAt(cursor++) != '"') {
            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected: \"");
            }
        }
        tokenPairs.add(new Pair(Tokens.STRING, this.jsonAsString.substring(begin, cursor - 1)));
        return cursor;
    }

    /**
     * Utility method that attempts to parse a json object.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed an object.
     */
    private int tokenizeObject(ArrayList<Pair> tokenPairs, int cursor) {
        tokenPairs.add(new Pair(Tokens.LCURLY, "{"));
        cursor++;
        while (this.jsonAsString.charAt(cursor) != '}') {
            cursor = this.tokenizeJSON(tokenPairs, cursor, '}');

            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected }");
            }
        }
        tokenPairs.add(new Pair(Tokens.RCURLY, "}"));
        return cursor + 1;
    }

    /**
     * Utility method that attempts to parse a json array.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed an array.
     */
    private int tokenizeArray(ArrayList<Pair> tokenPairs, int cursor) {
        tokenPairs.add(new Pair(Tokens.LBRACE, "["));
        cursor++;
        while (this.jsonAsString.charAt(cursor) != ']') {
            cursor = this.tokenizeJSON(tokenPairs, cursor, ']');

            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected: ]");
            }
        }
        tokenPairs.add(new Pair(Tokens.RBRACE, "]"));
        return cursor + 1;
    }

    /**
     * Utility method that attempts to parse a json number.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed a number.
     */
    private int tokenizeNumber(ArrayList<Pair> tokenPairs, int cursor) {
        boolean isDecimal = false;
        int begin = cursor;

        if (this.jsonAsString.charAt(cursor) == '-') {
            cursor++;
        }

        for (;Character.isDigit(this.jsonAsString.charAt(cursor)) || (!isDecimal && this.jsonAsString.charAt(cursor) == '.'); cursor++) {
            if (this.jsonAsString.charAt(cursor) == '.') {
                isDecimal = true;
            }
        }

        tokenPairs.add(new Pair(Tokens.NUMBER, this.jsonAsString.substring(begin, cursor)));
        return cursor;
    }

    /**
     * Utility method that attempts to parse a json bool.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed a bool.
     */
    private int tokenizeBool(ArrayList<Pair> tokenPairs, final int cursor) {
        if (cursor + 4 >= this.jsonAsString.length()) {
            throw new SyntaxException("Syntax error - Reached EOF, attempted to scan bool: true");
        }

        if (this.jsonAsString.substring(cursor, cursor + 4).equalsIgnoreCase("true")) {
            tokenPairs.add(new Pair(Tokens.BOOL, "true"));
            return cursor + 4;
        }

        if (cursor + 5 >= this.jsonAsString.length()) {
            throw new SyntaxException("Syntax error - Reached EOF, attempted to scan bool: false");
        }

        if (this.jsonAsString.substring(cursor, cursor + 5).equalsIgnoreCase("false")) {
            tokenPairs.add(new Pair(Tokens.BOOL, "false"));
            return cursor + 5;
        }

        throw new SyntaxException("Syntax error - Failed to match value, expected a bool");
    }

    /**
     * Utility method that attempts to parse a json null.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @return The new position of the cursor having successfully parsed a null.
     */
    private int tokenizeNull(ArrayList<Pair> tokenPairs, final int cursor) {
        if (cursor + 4 >= this.jsonAsString.length()) {
            throw new SyntaxException("Syntax error - Failed to match value, expected null");
        }

        if (!this.jsonAsString.substring(cursor, cursor + 4).equalsIgnoreCase("null")) {
            throw new SyntaxException("Syntax error - Reached EOF, attempted to scan null");
        }

        tokenPairs.add(new Pair(Tokens.NULL, "null"));
        return cursor + 4;
    }

    /**
     * Utility method that acts as a dispatcher to the other utility methods. It is
     * responsible for determining what type of json value to try and parse next.
     * @param tokenPairs internal representation of tokenized input.
     * @param cursor current index of the lexer in the input.
     * @throws SyntaxException if the input does not match the syntax rules.
     * @param delimiter specifies what character to stop at.
     * @return The new position of the cursor having successfully parsed some json value.
     */
    private int tokenizeJSON(ArrayList<Pair> tokenPairs, int cursor, char delimiter) {
        while (cursor < this.jsonAsString.length()) {
            if (this.jsonAsString.charAt(cursor) == delimiter)
                break;

            cursor = switch (this.jsonAsString.charAt(cursor)) {
                case '{' -> this.tokenizeObject(tokenPairs, cursor);
                case '"' -> this.tokenizeString(tokenPairs, cursor);
                case '[' -> this.tokenizeArray(tokenPairs, cursor);
                case 't', 'f' -> this.tokenizeBool(tokenPairs, cursor);
                case 'n' -> this.tokenizeNull(tokenPairs, cursor);
                case ':' -> {
                    tokenPairs.add(new Pair(Tokens.COLON, ":"));
                    yield cursor + 1;
                }
                case ',' -> {
                    tokenPairs.add(new Pair(Tokens.COMMA, ","));
                    yield cursor + 1;
                }
                case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> this.tokenizeNumber(tokenPairs, cursor);
                case '\n', '\r', '\t', ' ' -> this.skipWhitespace(cursor);
                default -> throw new SyntaxException("Syntax error - Unexpected token: " + this.jsonAsString.charAt(cursor));
            };
        }
        return cursor;
    }

    @Override
    public List<Pair> tokenize() {
        var tokenPairs = new ArrayList<Pair>();
        if (!this.jsonAsString.isEmpty()) {
            final int cursor = this.skipWhitespace(0);
            if (this.jsonAsString.charAt(cursor) != '{') {
                throw new SyntaxException("Syntax error - Expected beginning of object '{', got: " + this.jsonAsString.charAt(cursor));
            }
            this.tokenizeObject(tokenPairs, cursor);
        }
        return tokenPairs;
    }
}
