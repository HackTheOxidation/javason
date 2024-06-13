package io.github.hacktheoxidation;

/**
 * Runtime exception that indicates a syntax error in the input.
 * This exception is user-facing and as such it is meant for the user to handle.
 */
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
