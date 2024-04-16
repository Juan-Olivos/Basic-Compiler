package compiler.scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * A scanner class for lexical analysis of source code.
 */
public class Scanner {
    private BufferedReader reader;
    private String fileName;
    private int row = 1;
    private int col = 1;
    private char ch;

    /**
     * Constructor for creating a new Scanner object.
     * @param fileName The name of the file to be scanned.
     * @throws IOException If an I/O error occurs.
     */
    public Scanner(String fileName) throws IOException {
        this.fileName = fileName;
        this.reader = new BufferedReader(new FileReader(fileName));
        readChar();
    }

    /**
     * Reads the next character from the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private void readChar() throws IOException{
        int readChar = reader.read();
        if (readChar == -1) {
            ch = '\0'; // Indicates EOF
        } else {
            ch = (char) readChar;
        }

        if (ch == ' ') {
            col++;
        } else if (ch == '\t') {
            // Assuming a tab is 4 spaces
            col += 4;
        } else if (ch == '\n') {
            col = 1;
            row += 1;
        } else if (ch == '\r') {
            col = 1;
        } else {
            col++;
        }
    }

    /**
     * Returns the next token from the input stream.
     * @return The next token.
     * @throws IOException If an I/O error occurs.
     */
    public Token nextToken() throws IOException {
        Token token;
        skipWhiteSpace();
        skipComments();


        switch (ch) {
            case '+':
                if (peekChar() == '+') {
                    readChar();
                    token = new Token(TokenType.INCRE, "++");
                } else {
                    token = new Token(TokenType.PLUS, "+");
                }
                break;
            case '-':
                if (peekChar() == '-') {
                    readChar();
                    token = new Token(TokenType.DECRE, "--");
                } else {
                    token = new Token(TokenType.MINUS, "-");
                }
                break;
            case '=':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.EQ, "==");
                } else {
                    token = new Token(TokenType.ASSIGN, "=");
                }
                break;
            case '<':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.LTEQ, "<=");
                } else {
                    token = new Token(TokenType.LT, "<");
                }
                break;
            case '>':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.GTEQ, ">=");
                } else {
                    token = new Token(TokenType.GT, ">");
                }
                break;
            case '!':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.NOTEQ, "!=");
                } else {
                    token = new Token(TokenType.ILLEGAL, "!");
                }
                break;
            case '\"':
                String text = readString();
                if (text != null) {
                    return new Token(TokenType.STRING, text);
                } else {
                    token = new Token(TokenType.ILLEGAL, "\"");
                }
                break;
            case '{':
                token = new Token(TokenType.LBRACE, "{");
                break;
            case '}':
                token = new Token(TokenType.RBRACE, "}");
                break;
            case '(':
                token = new Token(TokenType.LPAREN, "(");
                break;
            case ')':
                token = new Token(TokenType.RPAREN, ")");
                break;
            case ';':
                token = new Token(TokenType.SEMICOLON, ";");
                break;
            case ',':
                token = new Token(TokenType.COMMA, ",");
                break;
            case '\0':
                token = new Token(TokenType.EOF, "");
                break;
            default:
                if (isLetterOrUnderscore(ch)) {
                    String identifier = readIdentifier();
                    return new Token(Token.keywords.getOrDefault(identifier, TokenType.IDENTIFIER), identifier);
                } else if (Character.isDigit(ch)) {
                    String digit = readDigit();
                    return new Token(TokenType.NUMBER, digit);
                } else {
                    token = new Token(TokenType.ILLEGAL, String.valueOf(ch));
                }
        }

        readChar();

        return token;
    }

    /**
     * Checks if the given character is a letter or underscore.
     * @param ch The character to check.
     * @return True if the character is a letter or underscore, false otherwise.
     */
    private boolean isLetterOrUnderscore(char ch) {
        return Character.isLetter(ch) || ch == '_';
    }

    /**
     * Checks if the given character is a letter, underscore, or digit.
     * @param ch The character to check.
     * @return True if the character is a letter, underscore, or digit, false otherwise.
     */
    private boolean isLetterOrUnderscoreOrDigit(char ch) {
        return isLetterOrUnderscore(ch) || Character.isDigit(ch);
    }

    /**
     * Reads an identifier from the input stream.
     * @return The identifier read.
     * @throws IOException If an I/O error occurs.
     */
    private String readIdentifier() throws IOException {
        StringBuilder identifier = new StringBuilder();

        identifier.append(ch);
        readChar();

        while (isLetterOrUnderscoreOrDigit(ch)) {
            identifier.append(ch);
            readChar();
        }

        return identifier.toString();
    }

    /**
     * Reads a sequence of digits from the input stream.
     * @return The sequence of digits read.
     * @throws IOException If an I/O error occurs.
     */
    private String readDigit() throws IOException {
        StringBuilder digit = new StringBuilder();

        digit.append(ch);
        readChar();

        while (Character.isDigit(ch)) {
            digit.append(ch);
            readChar();
        }

        return digit.toString();
    }

    /**
     * Peeks the next character in the input stream without consuming it.
     * @return The next character.
     * @throws IOException If an I/O error occurs.
     */
    private char peekChar() throws IOException {
        reader.mark(1);
        int nextCharInt = reader.read();
        if (nextCharInt != -1) {
            reader.reset();
            return (char) nextCharInt;
        } else {
            return '\0'; // Indicates EOF
        }
    }

    /**
     * Skips whitespace characters in the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private void skipWhiteSpace() throws IOException {
        while (ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
            readChar();
        }
    }

    /**
     * Skips single-line and multi-line comments in the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private void skipComments() throws  IOException {
        skipMultiLineComment();
        skipSingleLineComment();
    }

    /**
     * Skips single-line comments in the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private void skipSingleLineComment() throws IOException {
        if (ch == '/' && peekChar() == '/') {
            readChar();
            readChar();

            while (ch != '\0') {
                if (ch == '\n') {
                    readChar();
                    skipWhiteSpace();
                    return;
                }
                readChar();
            }
        }
    }

    /**
     * Skips multi-line comments in the input stream.
     * @throws IOException If an I/O error occurs.
     */
    private void skipMultiLineComment() throws IOException {
        if (ch == '/' && peekChar() == '*') {
            readChar();
            readChar();

            char c = peekChar();
            while (c != '\0') {
                if (ch == '*' && c == '/') {
                    readChar();
                    readChar();
                    skipWhiteSpace();
                    return;
                }
                readChar();
                c = peekChar();
            }
        }
    }

    /**
     * Reads a string from the input stream.
     * @return The string read.
     * @throws IOException If an I/O error occurs.
     */
    private String readString() throws IOException {
        StringBuilder text = new StringBuilder();
        readChar();

        while (ch != '\0') {
            if (ch == '\"') {
                readChar();
                return text.toString();
            }
            text.append(ch);
            readChar();
        }
        return null;
    }

    /**
     * Closes the input stream.
     * @throws IOException If an I/O error occurs.
     */
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }


}
