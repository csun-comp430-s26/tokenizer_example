package tokenizer;

import java.util.List;
import java.util.ArrayList;

public class Parser {
    private final List<Token> tokens;
    public Parser(final List<Token> tokens) {
        this.tokens = tokens;
    }

    public Token getToken(final int position) throws ParseException {
        if (position < 0 || position >= tokens.size()) {
            throw new ParseException("Tried to read token at out-of-bounds position: " + position);
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

    // primaryExp ::= IDENTIFIER | INTEGER | `(` exp `)`
    public ParseResult<Exp> parsePrimaryExp(final int startPosition) throws ParseException {
        final Token firstToken = getToken(startPosition);
        if (firstToken instanceof IdentifierToken idToken) {
            return new ParseResult<Exp>(new IdentifierExp(idToken.name()),
                                        startPosition + 1);
        } else if (firstToken instanceof IntegerToken intToken) {
            return new ParseResult<Exp>(new IntegerExp(intToken.value()),
                                        startPosition + 1);
        } else if (firstToken instanceof LeftParenToken) {
            final ParseResult<Exp> exp = parseExp(startPosition + 1);
            assertTokenHereIs(exp.nextPos, new RightParenToken());
            return new ParseResult<Exp>(new ParenExp(exp.result),
                                        exp.nextPos + 1);
        } else {
            throw new ParseException("Expected primary exp; found: " + firstToken.toString());
        }
    }

    // addExp ::= primaryExp ((`+` | `-`) primaryExp)*
    public ParseResult<Exp> parseAddExp(final int startPosition) throws ParseException {
        final ParseResult<Exp> initialPrimaryExp = parsePrimaryExp(startPosition);
        Exp currentExp = initialPrimaryExp.result;
        int currentPosition = initialPrimaryExp.nextPos;
        while (true) {
            try {
                final Token curToken = getToken(currentPosition);
                if (curToken instanceof PlusToken) {
                    final ParseResult<Exp> nextExp = parsePrimaryExp(currentPosition + 1);
                    currentExp = new BinopExp(currentExp, new PlusOp(), nextExp.result);
                    currentPosition = nextExp.nextPos;
                } else if (curToken instanceof MinusToken) {
                    final ParseResult<Exp> nextExp = parsePrimaryExp(currentPosition + 1);
                    currentExp = new BinopExp(currentExp, new MinusOp(), nextExp.result);
                    currentPosition = nextExp.nextPos;
                } else {
                    throw new ParseException("Expected + or -, got: " + curToken.toString());
                }
            } catch (final ParseException e) {
                return new ParseResult<Exp>(currentExp, currentPosition);
            }
        }
    }
    
    // exp ::= addExp
    public ParseResult<Exp> parseExp(final int startPosition) throws ParseException {
        return parseAddExp(startPosition);
    }

    public void assertTokenHereIs(final int position, final Token expected) throws ParseException {
        final Token received = getToken(position);
        if (!expected.equals(received)) {
            throw new ParseException("Expected token: " + expected.toString() +
                                     "; received token: " + received.toString());
        }
    }
                                     
    // stmt ::= `let` IDENTIFIER `=` exp `;`
    public ParseResult<Stmt> parseStmt(final int startPosition) throws ParseException {
        assertTokenHereIs(startPosition, new LetToken());
        final Token maybeIdToken = getToken(startPosition + 1);
        if (maybeIdToken instanceof IdentifierToken idToken) {
            assertTokenHereIs(startPosition + 2, new SingleEqualsToken());
            final ParseResult<Exp> exp = parseExp(startPosition + 3);
            assertTokenHereIs(exp.nextPos, new SemicolonToken());
            return new ParseResult<Stmt>(new LetStmt(idToken.name(), exp.result),
                                         exp.nextPos + 1);
        } else {
            throw new ParseException("Expected identifier, got: " + maybeIdToken.toString());
        }
    } // parseStmt

    // program ::= stmt*
    public Program parseProgram() throws ParseException {
        List<Stmt> stmts = new ArrayList<Stmt>();
        int currentPosition = 0;
        while (currentPosition < tokens.size()) {
            final ParseResult<Stmt> stmt = parseStmt(currentPosition);
            stmts.add(stmt.result);
            currentPosition = stmt.nextPos;
        }

        if (currentPosition == tokens.size()) {
            return new Program(stmts);
        } else {
            throw new ParseException("Tokens remaining at end: " + currentPosition);
        }
    }

    public static Program parseProgram(final List<Token> tokens)
        throws ParseException {
        return new Parser(tokens).parseProgram();
    }
}
