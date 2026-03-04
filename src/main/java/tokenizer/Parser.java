package tokenizer;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token getToken(final int position) throws TokenizerException {
        if (position < 0 || position >= tokens.size()) {
            throw new TokenizerException("Tried to read token at out-of-bounds position: " + position);
        } else {
            return tokens.get(position);
        }
    }
                                          
    // op ::= `+` | `-`
    public ParseResult<Op> parseOp(final int startPosition) throws ParseException {
        final Token token = getToken(startPosition);
        if (token instanceof PlusToken) {
            return new ParseResult<Op>(new PlusOp(), startPosition + 1);
        } else if (token instanceof MinusToken) {
            return new ParseResult<Op>(new MinusOp(), startPosition + 1);
        } else {
            throw new ParseException("Expected op, got: " + token.toString());
        }
    } // parseOp

    // exp ::= IDENTIFIER | INTEGER | `(` exp `)` | exp op exp
    public ParseResult<Exp> parseExp(final int startPosition) throws ParseException { ... }
    
    // stmt ::= `let` IDENTIFIER `=` exp `;`
    public ParseResult<Stmt> parseStmt(final int startPosition) throws ParseException {
        final Token letToken = getToken(startPosition);
        if (!letToken instanceof LetToken) {
            throw new ParseException("Expected let, got: " + letToken.toString());
        } else {
            final Token idToken = getToken(startPosition + 1);
            if (!idToken instanceof IdentifierToken) {
                throw new ParseException("Expected identifier, got: " + idToken.toString());
            } else {
                final Token equalsToken = getToken(startPosition + 2);
                if (!equalsToken instanceof SingleEqualsToken) {
                    throw new ParseException("Expected =, got: " + equalsToken.toString());
                } else {
                    final ParseResult<Exp> exp = parseExp(startPosition + 3);
                    final Token semicolonToken = getToken(exp.nextPos);
                    if (!semicolonToken instanceof SemicolonToken) {
                        throw new ParseException("Expected ;, got: " + semicolonToken.toString());
                    } else {
                        return new ParseResult<Stmt>(new LetStmt(((IdentifierToken)idToken).name,
                                                                 exp.result),
                                                     exp.nextPos + 1);
                    }
                }
            }
        }
    } // parseStmt
        
    public static Program parseProgram(final List<Token> tokens)
        throws ParseException { ... }
}
