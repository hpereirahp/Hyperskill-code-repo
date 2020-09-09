package contacts.controller;

import java.util.Scanner;

public class Controller {

    private Scanner scanner;

    public Controller(Scanner scanner) {
        this.scanner = scanner;
    }

    public void print(String text) {
        System.out.print(text);
    }

    public String getInput() {
        return scanner.nextLine();
    }
}
