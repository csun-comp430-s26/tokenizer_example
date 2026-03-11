package tokenizer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void identifierIsPrimaryExpression() throws ParseException {
        // foo
        assertEquals(new ParseResult<Exp>(new IdentifierExp("foo"), 1),
                     new Parser(Arrays.asList(new IdentifierToken("foo"))).parsePrimaryExp(0));
    }

    @Test
    public void integerIsPrimaryExpression() throws ParseException {
        // 123
        assertEquals(new ParseResult<Exp>(new IntegerExp(123), 1),
                     new Parser(Arrays.asList(new IntegerToken(123))).parsePrimaryExp(0));
    }

    @Test
    public void parenExpressionIsExpression() throws ParseException, TokenizerException {
        // (4)
        assertEquals(new ParseResult<Exp>(new ParenExp(new IntegerExp(4)), 3),
                     new Parser(Tokenizer.tokenize("(4)")).parsePrimaryExp(0));
    }
    // @Test
    // public void bigExample() throws ParseException, TokenizerException {
    //     final String input = "let foo = 1 + 2;\nlet bar = 3 - 4 + 5;";
    //     final List<Token> tokens = Tokenizer.tokenize(input);
    //     assertEquals(new ParseResult<Program>(new Program(Arrays.asList(new LetStatement(
}
