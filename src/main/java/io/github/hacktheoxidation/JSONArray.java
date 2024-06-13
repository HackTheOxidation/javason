package io.github.hacktheoxidation;

import java.util.Arrays;
import java.util.List;

public record JSONArray(List<JSON> value) implements JSON {

    @Override
    public JSONType getType() {
        return JSONType.ARRAY;
    }

    @Override
    public String toString() {
        return "JSONArray{" +
                "value=" + value +
                '}';
    }

    @Override
    public String serialize() {
        StringBuilder acc = new StringBuilder();

        for (JSON json : this.value) {
            acc.append(json.serialize()).append(", ");
        }

        if (!acc.isEmpty()) {
            acc.delete(acc.length() - 2, acc.length());
        }

        return "[" + acc + "]";
    }

    public static JSON of(Object... values) {
        return new JSONArray(Arrays.stream(values).map(JSON::of).toList());
    }
}
