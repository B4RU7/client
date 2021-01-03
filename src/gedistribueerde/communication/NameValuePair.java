package gedistribueerde.communication;

/**
 * Convenience class to represent a pair of a name and a value.
 * Implemented as an immutable class.
 */
final class NameValuePair {
    private final String name;
    private final String value;

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
