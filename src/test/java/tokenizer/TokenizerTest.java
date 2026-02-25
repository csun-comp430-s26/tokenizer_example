package tokenizer;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
    @Test
    public void testEmpty() throws TokenizerException {
        assertEquals(Arrays.asList(),
                     Tokenizer.tokenize(""));
    }

    @Test
    public void testOnlyWhitespace() throws TokenizerException {
        assertEquals(Arrays.asList(),
                     Tokenizer.tokenize("   "));
    }

    @Test
    public void testIdentifier() throws TokenizerException {
        assertEquals(Arrays.asList(new IdentifierToken("x")),
                     Tokenizer.tokenize("x"));
    }

    @Test
    public void testIdentifierWhitespace() throws TokenizerException {
        assertEquals(Arrays.asList(new IdentifierToken("x")),
                     Tokenizer.tokenize("x "));
    }
    
    @Test
    public void testInteger() throws TokenizerException {
        assertEquals(Arrays.asList(new IntegerToken(1)),
                     Tokenizer.tokenize("1"));
    }
    
    @Test
    public void testLeftParen() throws TokenizerException {
        assertEquals(Arrays.asList(new LeftParenToken()),
                     Tokenizer.tokenize("("));
    }

    @Test
    public void testRightParen() throws TokenizerException {
        assertEquals(Arrays.asList(new RightParenToken()),
                     Tokenizer.tokenize(")"));
    }

    @Test
    public void testPlus() throws TokenizerException {
        assertEquals(Arrays.asList(new PlusToken()),
                     Tokenizer.tokenize("+"));
    }

    @Test
    public void testMinus() throws TokenizerException {
        assertEquals(Arrays.asList(new MinusToken()),
                     Tokenizer.tokenize("-"));
    }

    @Test
    public void testLet() throws TokenizerException {
        assertEquals(Arrays.asList(new LetToken()),
                     Tokenizer.tokenize("let"));
    }

    @Test
    public void testSingleEquals() throws TokenizerException {
        assertEquals(Arrays.asList(new SingleEqualsToken()),
                     Tokenizer.tokenize("="));
    }

    @Test
    public void testSemicolon() throws TokenizerException {
        assertEquals(Arrays.asList(new SemicolonToken()),
                     Tokenizer.tokenize(";"));
    }

    @Test
    public void testInvalid() throws TokenizerException {
        assertThrows(TokenizerException.class,
                     () -> Tokenizer.tokenize("$"));
    }
}

