public class IdentifierToken implements Token {
    public final String name;
    public IdentifierToken(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof IdentifierToken) {
            final IdentifierToken asToken = (IdentifierToken)other;
            return name.equals(asToken.name);
        } else {
            return false;
        }
    }

    @Override
    public boolean toString() {
        return "IdentifierToken(" + name + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
