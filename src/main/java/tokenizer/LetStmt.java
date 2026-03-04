package tokenizer;

public record LetStmt(String identifier, Exp exp) implements Stmt {}
