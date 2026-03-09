# Tokenizer Example #

## Concrete Grammar ##

```
primaryExp ::= IDENTIFIER | INTEGER | `(` exp `)`
addExp ::= primaryExp ((`+` | `-`) primaryExp)*
exp ::= addExp
stmt ::= `let` IDENTIFIER `=` exp `;`
program ::= stmt*
```


Hypothetical: we had multiplication and division.

```
primaryExp ::= IDENTIFIER | INTEGER | `(` exp `)`
multExp ::= primaryExp ((`*` | `/`) primaryExp)*
addExp ::= multExp ((`+` | `-`) multExp)*
exp ::= addExp
stmt ::= `let` IDENTIFIER `=` exp `;`
program ::= stmt*
```

## Abstract Grammar ##

```
exp ::= IDENTIFIER | INTEGER | `(` exp `)` | exp op exp
op ::= `+` | `-` | `*` | `/`
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
