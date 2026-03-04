package tokenizer;

public class ParseResult<A> {
    public final A result;
    public final int nextPos;
    public ParseResult(final A result,
                       final int nextPos) {
        this.result = result;
        this.nextPos = nextPos;
    }
}
