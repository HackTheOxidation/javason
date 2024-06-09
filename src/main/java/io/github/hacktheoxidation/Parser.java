package io.github.hacktheoxidation;

import java.util.ArrayList;

public interface Parser {
    ArrayList<Lexer.Pair> getTokens();
    JSON parse();
}
