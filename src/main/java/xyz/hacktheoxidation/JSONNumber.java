package xyz.hacktheoxidation;

public record JSONNumber(double value) implements JSON {
    @Override
    public JSONType getType() {
        return JSONType.NUMBER;
    }

    @Override
    public String toString() {
        return "JSONNumber{" +
                "value=" + value +
                '}';
    }

    @Override
    public String serialize() {
        return String.valueOf(this.value);
    }
}
