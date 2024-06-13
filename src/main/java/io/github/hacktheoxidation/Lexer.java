package io.github.hacktheoxidation;

import java.util.List;

/**
 * Defines what a lexer is in the context of javason.
 * All lexers must be able to tokenize some source of input and produce an internal representation
 * in the form of a list of pairs of tokens and the string from which the token was inferred.
 */
public interface Lexer {
    record Pair(Tokens token, String value) {}
    List<Pair> tokenize();
}
