package solver;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LinearEquation equation = new LinearEquation(args[1], args[3]);
        equation.solve();
    }
}
