// FOR MONDAY: redo as record
public class LeftParenToken implements Token {
    public LeftParenToken() {}

    @Override
    public boolean equals(final Object other) {
        return other instanceof LeftParenToken;
    }

    @Override
    public String toString() {
        return "LeftParenToken";
    }

    // integer representation of the object
    // obj1.equals(obj2) => obj1.hashCode() == obj2.hashCode()
    @Override
    public int hashCode() {
        return 0;
    }
}
