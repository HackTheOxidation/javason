package io.github.hacktheoxidation;

public record JSONBool(boolean value) implements JSON {
    @Override
    public JSONType getType() {
        return JSONType.BOOL;
    }

    @Override
    public String toString() {
        return "JSONBool{" +
                "value=" + value +
                '}';
    }

    @Override
    public String serialize() {
        return String.valueOf(value);
    }
}
