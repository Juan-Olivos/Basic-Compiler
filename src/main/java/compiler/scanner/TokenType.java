package compiler.scanner;

public enum TokenType {
    IDENTIFIER,
    NUMBER,
    STRING,

    // Keywords
    LET,
    FUNCTION,
    FOR,
    WHILE,
    IF,
    ELSE,
    VOID,
    TRUE,
    FALSE,
    RETURN,
    CONTINUE,
    BREAK,
    PRINT,
    INPUT,


    // Operators
    PLUS,
    MINUS,
    ASSIGN,
    INCRE,
    DECRE,
    EQ,
    NOTEQ,
    GT,
    LT,
    GTEQ,
    LTEQ,

    // Delimiters
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    COMMA,
    SEMICOLON,


    ILLEGAL,
    EOF
}
