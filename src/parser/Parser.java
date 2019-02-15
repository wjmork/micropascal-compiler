package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
.1;
import scanner.ExpToken;
import scanner.ExpTokenType;

public class Parser {
    private ExpToken lookahead;
    private ExpScanner scanner;

    public Parser(String text, boolean isFilename) {
        if (isFilename) {
            FileInputStream fis = null;

            try {
                fis = new FileInputStream("expressions/simplest.pas");
            } catch (FileNotFoundException var6) {
                this.error("No file");
            }

            InputStreamReader isr = new InputStreamReader(fis);
            this.scanner = new ExpScanner(isr);
        } else {
            this.scanner = new ExpScanner(new StringReader(text));
        }

        try {
            this.lookahead = this.scanner.nextToken();
        } catch (IOException var5) {
            this.error("Scan error");
        }

    }

    public void exp() {
        this.term();
        this.exp_prime();
    }

    public void exp_prime() {
        if (this.lookahead.getType() == ExpTokenType.PLUS || this.lookahead.getType() == ExpTokenType.MINUS) {
            this.addop();
            this.term();
            this.exp_prime();
        }

    }

    public void addop() {
        if (this.lookahead.getType() == ExpTokenType.PLUS) {
            this.match(ExpTokenType.PLUS);
        } else if (this.lookahead.getType() == ExpTokenType.MINUS) {
            this.match(ExpTokenType.MINUS);
        } else {
            this.error("Addop");
        }

    }

    public void term() {
        this.factor();
        this.term_prime();
    }

    public void term_prime() {
        if (this.isMulop(this.lookahead)) {
            this.mulop();
            this.factor();
            this.term_prime();
        }

    }

    private boolean isMulop(ExpToken token) {
        boolean answer = false;
        if (token.getType() == ExpTokenType.MULTIPLY || token.getType() == ExpTokenType.DIVIDE) {
            answer = true;
        }

        return answer;
    }

    public void mulop() {
        if (this.lookahead.getType() == ExpTokenType.MULTIPLY) {
            this.match(ExpTokenType.MULTIPLY);
        } else if (this.lookahead.getType() == ExpTokenType.DIVIDE) {
            this.match(ExpTokenType.DIVIDE);
        } else {
            this.error("Mulop");
        }

    }

    public void factor() {
        switch(1.$SwitchMap$scanner$ExpTokenType[this.lookahead.getType().ordinal()]) {
            case 1:
                this.match(ExpTokenType.LEFT_PAREN);
                this.exp();
                this.match(ExpTokenType.RIGHT_PAREN);
                break;
            case 2:
                this.match(ExpTokenType.NUMBER);
                break;
            default:
                this.error("Factor");
        }

    }

    public void match(ExpTokenType expected) {
        System.out.println("match( " + expected + ")");
        if (this.lookahead.getType() == expected) {
            try {
                this.lookahead = this.scanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new ExpToken("End of File", (ExpTokenType)null);
                }
            } catch (IOException var3) {
                this.error("Scanner exception");
            }
        } else {
            this.error("Match of " + expected + " found " + this.lookahead.getType() + " instead.");
        }

    }

    public void error(String message) {
        System.out.println("Error " + message + " at line " + this.scanner.getLine() + " column " + this.scanner.getColumn());
    }
}
