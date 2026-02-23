import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class Tokenizer {
    public final String input;
    private int position;

    public Tokenizer(final String input) {
        this.input = input;
        position = 0;
    }
    
    public void skipWhitespace() {
        while (position < input.length() &&
               Character.isWhitespace(input.charAt(position))) {
            position++;
        }
    }

    // assumes position is initially in bounds
    public Optional<Token> readSymbol() {
        char c = input.charAt(position);
        if (c == '(') {
            position++;
            return Optional.of(new LeftParenToken());
        } else if (c == ')') {
            position++;
            return Optional.of(new RightParenToken());
        } else if (c == '+') {
            position++;
            return Optional.of(new PlusToken());
        } else if (c == '-') {
            position++;
            return Optional.of(new MinusToken());
        } else if (c == '=') {
            position++;
            return Optional.of(new SingleEqualsToken());
        } else if (c == ';') {
            position++;
            return Optional.of(new SemicolonToken());
        } else {
            return Optional.empty();
        }
    } // readSymbol
    
    // assumes position is initially in bounds
    public Optional<Token> readInteger() {
        String digits = "";
        char c = ' ';
        while (position < input.length() &&
               Character.isDigit(c = input.charAt(position))) {
            digits += c;
            position++;
        }

        if (digits.length() > 0) {
            return Optional.of(new IntegerToken(Integer.parseInt(digits)));
        } else {
            return Optional.empty();
        }
    }
    
    // assumes position is initially in bounds
    public Optional<Token> readIdentifierOrReservedWord() {
        char c = input.charAt(position);
        if (Character.isLetter(c)) {
            String identifierOrReservedWord = "" + c;
            position++;

            while (position < input.length() &&
                   Character.isLetterOrDigit(c = input.charAt(position))) {
                identifierOrReservedWord += c;
                position++;
            }

            if (identifierOrReservedWord.equals("let")) {
                return Optional.of(new LetToken());
            } else {
                return Optional.of(new IdentifierToken(identifierOrReservedWord));
            }
        } else {
            return Optional.empty();
        }
    }

    // returns empty if there are no remaining tokens
    public Optional<Token> readToken() throws TokenizerException {
        skipWhitespace();
        if (position >= input.length()) {
            return Optional.empty();
        } else {
            Optional<Token> retval = readSymbol();
            if (retval.isPresent()) {
                return retval;
            }
            retval = readInteger();
            if (retval.isPresent()) {
                return retval;
            }
            retval = readIdentifierOrReservedWord();
            if (retval.isPresent()) {
                return retval;
            } else {
                throw new TokenizerException("Found unrecognized character: " +
                                             input.charAt(position) + " at position " +
                                             position);
            }
        }
    }
                    

    public List<Token> tokenize() throws TokenizerException {
        final List<Token> retval = new ArrayList<Token>();
        Optional<Token> token = readToken();
        while (token.isPresent()) {
            retval.add(token.get());
            token = readToken();
        }
        return retval;
    }
    
    public static List<Token> tokenize(final String input)
        throws TokenizerException {
        return new Tokenizer(input).tokenize();
    }

    public static void main(String[] args) throws TokenizerException {
        if (args.length == 1) {
            System.out.println(tokenize(args[0]));
        } else {
            System.out.println("Needs a string to tokenize");
        }
    }

    // FOR MONDAY: build tools, unit testing, code coverage
}
