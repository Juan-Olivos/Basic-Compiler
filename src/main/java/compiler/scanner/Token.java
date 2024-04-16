package compiler.scanner;

import java.util.Map;
import java.util.HashMap;

/**
 * Represents a token in the input stream.
 */
public class Token {
    private final TokenType type; // The type of the token
    private final String literal; // The literal value of the token
    public static final Map<String, TokenType> keywords = new HashMap<>(); // Map of keywords to token types

    // Static block to initialize the keyword map
    static {
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("for", TokenType.FOR);
        keywords.put("while", TokenType.WHILE);
        keywords.put("let", TokenType.LET);
        keywords.put("return", TokenType.RETURN);
        keywords.put("void", TokenType.VOID);
        keywords.put("continue", TokenType.CONTINUE);
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("break", TokenType.BREAK);
        keywords.put("print", TokenType.PRINT);
        keywords.put("function", TokenType.FUNCTION);
        keywords.put("input", TokenType.INPUT);
    }

    /**
     * Constructs a new Token object with the specified type and literal value.
     * @param type The type of the token.
     * @param literal The literal value of the token.
     */
    public Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    /**
     * Gets the type of the token.
     * @return The type of the token.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Gets the literal value of the token.
     * @return The literal value of the token.
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * Returns a string representation of the token.
     * @return A string representation of the token.
     */
    @Override
    public String toString() {
        return "Type: " + getType() + " | Literal: " + getLiteral();
    }
}
