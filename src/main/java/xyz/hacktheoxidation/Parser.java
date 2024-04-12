package xyz.hacktheoxidation;

import java.util.ArrayList;

public interface Parser {
    public ArrayList<Lexer.Pair> getTokens();
    public JSON parse();
}
