package xyz.hacktheoxidation;

import java.util.HashMap;
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
            acc.append("\"").append(entry.getKey()).append("\"").append(": ").append(entry.getValue().serialize()).append(", ");
        }

        if (!acc.isEmpty()) {
            acc.delete(acc.length() - 2, acc.length());
        }

        return "{" + acc + "}";
    }

    public static JSONObject of() {
        return new JSONObject(new HashMap<>());
    }

    public static JSONObject of(String key, Object value, Object... rest) {
        if (rest.length % 2 != 0) {
            throw new IllegalArgumentException("JSONObject.of() only takes an even number of arguments, got: " + rest.length);
        }

        if (rest.length == 0) {
            return new JSONObject(new HashMap<>(Map.of(key, JSON.of(value))));
        } else {
            Object[] tail = new Object[rest.length - 2];
            System.arraycopy(rest, 2, tail, 0, rest.length - 2);

            var jsonObject = JSONObject.of((String) rest[0], rest[1], tail);
            jsonObject.value().put(key, JSON.of(value));

            return jsonObject;
        }
    }
}
