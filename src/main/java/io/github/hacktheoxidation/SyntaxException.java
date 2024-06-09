package io.github.hacktheoxidation;

public class SyntaxException extends RuntimeException {
    private final String message;

    public SyntaxException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
