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

    public static JSON of(Object... args) {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("JSONObject.of() only takes an even number of parameters, got: " + args.length);
        }

        var mappings = new HashMap<String, JSON>();
        for (int i = 0; i < args.length; i += 2) {
            var key = args[i];
            var value = args[i + 1];

            if (!(key instanceof String)) {
                throw new IllegalArgumentException("JSONObject.of() expects every odd argument to be a string.");
            }

            mappings.put((String) key, JSON.of(value));
        }

        return new JSONObject(mappings);
    }
}
