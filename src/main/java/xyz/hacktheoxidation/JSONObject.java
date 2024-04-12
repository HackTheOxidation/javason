package xyz.hacktheoxidation;

import java.util.Map;

public record JSONObject(Map<String, JSON> value) implements JSON {

    @Override
    public JSONType getType() {
        return JSONType.OBJECT;
    }

    @Override
    public String toString() {
        return "JSONObject{" +
                "value=" + value +
                '}';
    }

    @Override
    public String serialize() {
        StringBuilder acc = new StringBuilder();
        for (Map.Entry<String, JSON> entry : this.value.entrySet()) {
            acc.append("\"").append(entry.getKey()).append("\"").append(": ").append(entry.getKey());
        }
        return "{" + acc + "}";
    }
}
