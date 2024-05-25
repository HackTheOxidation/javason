package xyz.hacktheoxidation;

public sealed interface JSON permits JSONBool, JSONString, JSONNumber, JSONNull, JSONArray, JSONObject {
    JSONType getType();

    @Override
    String toString();

    String serialize();
}
