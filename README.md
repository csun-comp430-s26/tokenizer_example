# Tokenizer Example #

```
exp ::= IDENTIFIER | INTEGER | `(` exp `)` | exp op exp
op ::= `+` | `-`
stmt ::= `let` IDENTIFIER `=` exp `;`
program ::= stmt*
```

Tokens:

- `IDENTIFIER` (`IdentifierToken`)
- `INTEGER` (`IntegerToken`)
- `(` (`LeftParenToken`)
- `)` (`RightParenToken`)
- `+` (`PlusToken`)
- `-` (`MinusToken`)
- `let` (`LetToken`)
- `=` (`SingleEqualsToken`)
- `;` (`SemicolonToken`)
