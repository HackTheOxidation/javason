package xyz.hacktheoxidation;

public class Main {
    public static void main(String[] args) {
        String content = "{}";
        JSON json = new RecursiveDescentParser(new SimpleLexer(content)).parse();
    }
}