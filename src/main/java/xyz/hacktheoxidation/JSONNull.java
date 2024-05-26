package xyz.hacktheoxidation;

public final class JSONNull implements JSON {
    @Override
    public JSONType getType() {
        return JSONType.NULL;
    }

    @Override
    public String toString() {
        return "JSONNull{}";
    }

    @Override
    public String serialize() {
        return "null";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof JSONNull;
    }
}
