package io.github.hacktheoxidation;

/**
 * This runtime exception indicates a failure during parsing.
 * However, this is a library-facing error for detecting failures when
 * implementing parsers and during testing. It is not intended to be leaked
 * to the user for them to handle.
 */
public class ParserException extends RuntimeException {
}
