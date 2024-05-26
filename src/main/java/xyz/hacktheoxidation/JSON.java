package xyz.hacktheoxidation;

public sealed interface JSON permits JSONBool, JSONString, JSONNumber, JSONNull, JSONArray, JSONObject {
    JSONType getType();

    @Override
    String toString();

    String serialize();

    static JSON of(Object value) {
        return switch (value) {
            case null -> new JSONNull();
            case Boolean b -> new JSONBool(b);
            case Integer i -> new JSONNumber(i);
            case Double d -> new JSONNumber(d);
            case String s -> new JSONString(s);
            default -> throw new IllegalArgumentException("Cannot convert unexpected value to JSON: " + value);
        };
    }
}
