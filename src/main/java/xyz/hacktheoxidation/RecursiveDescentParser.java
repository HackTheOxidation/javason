package xyz.hacktheoxidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RecursiveDescentParser implements Parser {
    private final Lexer lexer;

    public RecursiveDescentParser(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public ArrayList<Lexer.Pair> getTokens() {
        return lexer.tokenize();
    }

    private JSONString parseString(ArrayList<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.STRING) {
            throw new ParserException();
        }
        return new JSONString(cursor.value());
    }

    private JSONNumber parseNumber(ArrayList<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.NUMBER) {
            throw new ParserException();
        }
        return new JSONNumber(Double.parseDouble(cursor.value()));
    }

    private JSONBool parseBool(ArrayList<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.BOOL) {
            throw new ParserException();
        }
        return new JSONBool(cursor.value().equals("true"));
    }

    private JSONNull parseNull(ArrayList<Lexer.Pair> tokenPairs) {
        var cursor = tokenPairs.removeFirst();
        if (Objects.requireNonNull(cursor.token()) != Tokens.NULL) {
            throw new ParserException();
        }
        return new JSONNull();
    }

    private JSONArray parseArray(ArrayList<Lexer.Pair> tokenPairs) {
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

    private JSONObject parseObject(ArrayList<Lexer.Pair> tokenPairs) {
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

    private JSON parseAnyValue(ArrayList<Lexer.Pair> tokenPairs) {
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
