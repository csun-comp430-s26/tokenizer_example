# Tokenizer Example #

```
exp ::= IDENTIFIER | INTEGER | `(` exp `)` | exp op exp
op ::= `+` | `-`
stmt ::= `let` IDENTIFIER `=` exp `;`
program ::= stmt*
```
