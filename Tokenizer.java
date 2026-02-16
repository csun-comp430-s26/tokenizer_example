import java.util.List;

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

    // Wednesday: symbols, tie everything together
    
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

    public static List<Token> tokenize(final String input)
        throws TokenizerException {
        int position = skipWhitespace(input);
        if (position < input.length()) {
    }
}
