package xyz.hacktheoxidation;

import java.util.ArrayList;

public class SimpleLexer implements Lexer {
    private final String jsonAsString;

    public SimpleLexer(String jsonAsString) {
        this.jsonAsString = jsonAsString;
    }

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

    private int tokenizeString(ArrayList<Pair> tokenPairs, int cursor) {
        int begin = ++cursor;
        while (this.jsonAsString.charAt(cursor++) != '"') {
            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected: \"");
            }
        }
        tokenPairs.add(new Pair(Tokens.STRING, this.jsonAsString.substring(begin, cursor)));
        return cursor + 1;
    }

    private int tokenizeObject(ArrayList<Pair> tokenPairs, int cursor) {
        tokenPairs.add(new Pair(Tokens.LCURLY, "{"));
        cursor++;
        while (this.jsonAsString.charAt(cursor) != '}') {
            cursor = this.tokenizeJSON(tokenPairs, cursor);

            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected }");
            }
        }
        tokenPairs.add(new Pair(Tokens.RCURLY, "}"));
        return cursor + 1;
    }

    private int tokenizeArray(ArrayList<Pair> tokenPairs, int cursor) {
        tokenPairs.add(new Pair(Tokens.LBRACE, "["));
        while (this.jsonAsString.charAt(++cursor) != ']') {
            cursor = this.tokenizeJSON(tokenPairs, cursor);

            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, expected: ]");
            }
        }
        tokenPairs.add(new Pair(Tokens.RBRACE, "]"));
        return cursor + 1;
    }

    private int tokenizeNumber(ArrayList<Pair> tokenPairs, int cursor) {
        boolean isDecimal = false;
        int begin = cursor;
        for (;Character.isDigit(this.jsonAsString.charAt(cursor)) || (!isDecimal && this.jsonAsString.charAt(cursor) == '.'); cursor++) {
            if (this.jsonAsString.charAt(cursor) == '.') {
                isDecimal = true;
            }

            if (cursor >= this.jsonAsString.length()) {
                throw new SyntaxException("Syntax error - Reached EOF, attempted to scan a number");
            }
        }

        tokenPairs.add(new Pair(Tokens.NUMBER, this.jsonAsString.substring(begin, cursor)));
        return cursor;
    }

    private int tokenizeBool(ArrayList<Pair> tokenPairs, int cursor) {
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

    private int tokenizeNull(ArrayList<Pair> tokenPairs, int cursor) {
        if (cursor + 4 >= this.jsonAsString.length()) {
            throw new SyntaxException("Syntax error - Failed to match value, expected null");
        }

        if (!this.jsonAsString.substring(cursor, cursor + 4).equalsIgnoreCase("null")) {
            throw new SyntaxException("Syntax error - Reached EOF, attempted to scan null");
        }

        tokenPairs.add(new Pair(Tokens.NULL, "null"));
        return cursor + 4;
    }

    private int tokenizeJSON(ArrayList<Pair> tokenPairs, int cursor) {
        for (; cursor < this.jsonAsString.length(); cursor++) {
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
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> this.tokenizeNumber(tokenPairs, cursor);
                case '\n', '\r', '\t', ' ' -> this.skipWhitespace(cursor);
                default -> throw new SyntaxException("Syntax error - Unexpected token: " + this.jsonAsString.charAt(cursor));
            };
        }
        return cursor;
    }

    @Override
    public ArrayList<Pair> tokenize() {
        var tokenPairs = new ArrayList<Pair>();
        int cursor = this.skipWhitespace(0);
        if (this.jsonAsString.charAt(cursor) != '{') {
            throw new SyntaxException("Syntax error - Expected beginning of object '{', got: " + this.jsonAsString.charAt(cursor));
        }
        this.tokenizeObject(tokenPairs, cursor);
        return tokenPairs;
    }
}
