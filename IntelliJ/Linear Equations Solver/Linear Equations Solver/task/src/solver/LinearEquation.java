package solver;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LinearEquation {

    private final String inputFile;
    private final String outputFile;
    private Matrix matrix;
    private ComplexNumber[] solution;

    public LinearEquation(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void solve() {
        System.out.println("Start solving the equation.");

        read();

        solution = GaussJordanElimination.solve(matrix);

        if (solution == null) {
            System.out.println("No solutions");
        } else if (solution.length == 0) {
            System.out.println("Infinitely many solutions");
        } else {
            System.out.printf("The solution is: (%s)%n", Arrays.stream(solution)
                    .map(ComplexNumber::toString)
                    .collect(Collectors.joining(", ")));
        }

        write();
    }

    private void read() {

        File file = new File(inputFile);
        try (Scanner scanner = new Scanner(file)) {

            int numberOfVariables = scanner.nextInt();
            int numberOfEquations = scanner.nextInt();
            String[] values = new String[numberOfEquations * (numberOfVariables + 1)];
            for (int i = 0; i < values.length; i++) {
                values[i] = scanner.next().trim();
            }

//            System.out.println(String.join(" ", values));
            // TODO
            matrix = new Matrix(numberOfVariables, numberOfEquations, values);

            System.out.println(matrix.toString());

        } catch (Exception ex) {
            System.out.printf("ERROR: %", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void write() {
        File file = new File(outputFile);
        try (PrintWriter writer = new PrintWriter(file)) {
            if (solution == null) {
                writer.println("No Solutions");
            } else if (solution.length == 0) {
                writer.println("Infinitely many solutions");
            } else {
                for (ComplexNumber d : solution) {
                    writer.println(d);
                }
            }
            System.out.printf("Saved to file %s%n", outputFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
