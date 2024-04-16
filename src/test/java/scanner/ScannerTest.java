package scanner;

import compiler.scanner.Scanner;
import compiler.scanner.Token;
import compiler.scanner.TokenType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScannerTest {
    private Scanner scanner;
    private List<Token> tokens;
    private int lastTokenIndex = 0;

    @Before
    public void setUp() throws IOException {
        scanner = new Scanner("src/test/resources/testFile1.txt");
        tokens = new ArrayList<>();
        Token token = scanner.nextToken();
        while (token.getType() != TokenType.EOF) {
            tokens.add(token);
            token = scanner.nextToken();
            lastTokenIndex++;
        }
        tokens.add(token);
    }


    @After
    public void tearDown() throws IOException {
        scanner.close();
    }

    @Test
    public void testEOFToken() {
        Token token = tokens.get(lastTokenIndex);
        assertEquals(TokenType.EOF, token.getType());
        assertEquals("", token.getLiteral());
    }

    @Test
    public void testSingleCharacterTokens() {
        int i = 0;
        Token token = tokens.get(i++);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.MINUS, token.getType());
        assertEquals("-", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.COMMA, token.getType());
        assertEquals(",", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LBRACE, token.getType());
        assertEquals("{", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RBRACE, token.getType());
        assertEquals("}", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LT, token.getType());
        assertEquals("<", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.GT, token.getType());
        assertEquals(">", token.getLiteral());
    }

    @Test
    public void testKeywordIdentifierTokens() {
        int i = 11;
        Token token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("x", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("5", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());
    }

    @Test
    public void testIgnoreSingleLineComment() {
        int i = 16;
        Token token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());
    }

    @Test
    public void testIgnoreMultiLineComment() {
        int i = 17;
        Token token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());
    }

    @Test
    public void testPrintStringTokens() {
        int i = 18;
        Token token = tokens.get(i++);
        assertEquals(TokenType.PRINT, token.getType());
        assertEquals("print", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.STRING, token.getType());
        assertEquals("How many fibonacci numbers do you want?", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());
    }

    @Test
    public void testInputIdentifierTokens() {
        int i = 23;
        Token token = tokens.get(i++);
        assertEquals(TokenType.INPUT, token.getType());
        assertEquals("input", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("nums", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());
    }

    @Test
    public void testEmptyPrint() {
        int i = 28;
        Token token = tokens.get(i++);
        assertEquals(TokenType.PRINT, token.getType());
        assertEquals("print", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.STRING, token.getType());
        assertEquals("", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());
    }

    @Test
    public void testFunctionDefinition() {
        int i = 33;
        Token token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("fibonacci", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.FUNCTION, token.getType());
        assertEquals("function", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("nums", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LBRACE, token.getType());
        assertEquals("{", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("a", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("0", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("b", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("1", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.WHILE, token.getType());
        assertEquals("while", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("nums", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.GT, token.getType());
        assertEquals(">", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.NUMBER, token.getType());
        assertEquals("0", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LBRACE, token.getType());
        assertEquals("{", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.PRINT, token.getType());
        assertEquals("print", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("a", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        // let c = a + b;
        token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("c", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("a", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.PLUS, token.getType());
        assertEquals("+", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("b", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        // let a = b;
        token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("a", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("b", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        // let b = c;
        token = tokens.get(i++);
        assertEquals(TokenType.LET, token.getType());
        assertEquals("let", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("b", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.ASSIGN, token.getType());
        assertEquals("=", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("c", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        // nums--;
        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("nums", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.DECRE, token.getType());
        assertEquals("--", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RBRACE, token.getType());
        assertEquals("}", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RBRACE, token.getType());
        assertEquals("}", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("fibonacci", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.LPAREN, token.getType());
        assertEquals("(", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.IDENTIFIER, token.getType());
        assertEquals("nums", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.RPAREN, token.getType());
        assertEquals(")", token.getLiteral());

        token = tokens.get(i++);
        assertEquals(TokenType.SEMICOLON, token.getType());
        assertEquals(";", token.getLiteral());


    }
}


