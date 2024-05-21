package xyz.hacktheoxidation;

public record JSONString(String value) implements JSON {
    @Override
    public JSONType getType() {
        return JSONType.STRING;
    }

    @Override
    public String toString() {
        return "JSONString{" +
                "value=\"" + value + '"' +
                '}';
    }

    @Override
    public String serialize() {
        return "\"" + this.value + "\"";
    }
}
