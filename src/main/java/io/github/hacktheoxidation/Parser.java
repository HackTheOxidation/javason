package io.github.hacktheoxidation;

import java.util.List;

/**
 * A parser takes the result of the lexers tokenization and converts it
 * to a JSON representation (java objects) if the tokenized input satisfies
 * the rules for json syntax.
 * The Parser should ideally wrap some form of lexer rather than having the user manage
 * multiple objects.
 */
public interface Parser {
    /**
     * Retrieves the tokenization result that the parser is working on.
     * @return tokenization result from a lexer.
     */
    List<Lexer.Pair> getTokens();

    /**
     * Parses the internal representation (tokens) to produce JSON (java object).
     * @return JSON upon successful parsing.
     */
    JSON parse();
}
