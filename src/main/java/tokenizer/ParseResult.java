package tokenizer;

public record ParseResult<A>(A result, int nextPos) {}
