package xyz.hacktheoxidation;

public sealed interface JSON permits JSONBool, JSONString, JSONNumber, JSONNull, JSONArray, JSONObject {
    public JSONType getType();

    @Override
    public String toString();

    public String serialize();
}
