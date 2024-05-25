package xyz.hacktheoxidation;

import java.util.ArrayList;

public interface Lexer {
    record Pair(Tokens token, String value) {}
    ArrayList<Pair> tokenize();
}
