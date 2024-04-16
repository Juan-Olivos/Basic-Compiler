package compiler;

import compiler.scanner.Scanner;
import compiler.scanner.Token;
import compiler.scanner.TokenType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/targetCode.txt"))) {

            Scanner scanner = new Scanner("src/main/resources/sourceCode.txt");

            for (Token tok = scanner.nextToken(); tok.getType() != TokenType.EOF; tok = scanner.nextToken()) {
                writer.write(tok.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
