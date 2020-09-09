package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {

    private int numberOfVariables;
    private int numberOfEquations;
    private Row[] rows;
    private List<int[]> swaps;

    public Matrix(int numberOfVariables, int numberOfEquations, String[] values) {
        this.numberOfVariables = numberOfVariables;
        this.numberOfEquations = numberOfEquations;
        this.rows = new Row[numberOfEquations];
        for (int i = 0; i < numberOfEquations; i++) {
            rows[i] = new Row(Arrays.copyOfRange(values, 0 + i * (numberOfVariables + 1), numberOfVariables + 1 + i * (numberOfVariables + 1)));
        }
        this.swaps = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%nMatrix%n%s", Arrays.stream(rows)
                .map(Row::toString)
                .collect(Collectors.joining("\n")));
    }

    public void buildEchelon() {
        for (int i = 0; i < Math.min(numberOfVariables, numberOfEquations); i++) {

            if (isZero(i, i)) {
                boolean swapped = false;

                for (int j = i + 1; j < numberOfEquations; j++) {
                    if (!isZero(j, i)) {
                        swapRows(i, j);
                        swapped = true;
                        break;
                    }
                }

                if (!swapped) {
                    for (int j = i + 1; j < numberOfVariables; j++) {
                        if (!isZero(i, j)) {
                            swapColumns(i, j);
                            swapped = true;
                            break;
                        }
                    }

                    if (!swapped) {
                        for (int row = i + 1; row < numberOfEquations; row++) {
                            for (int column = i + 1; column < numberOfVariables; column++) {
                                if (!isZero(row, column)) {
                                    swapRows(i, row);
                                    swapColumns(i, column);
                                    swapped = true;
                                    break;
                                }
                            }
                            if (swapped) {
                                break;
                            }
                        }
                    }

                    if (!swapped) {
                        return;
                    }
                }
            }

            normalize(i);
            for (int j = i + 1; j < numberOfEquations; j++) {
                reduce(i, j);
            }
        }
        normalize(rows.length - 1);
    }

    public void backSolve() {
        for (int i = Math.min(numberOfEquations, numberOfVariables) - 1; i > 0; i--) {
            normalize(i);
            for (int j = i - 1; j >= 0; j--) {
                reduce(i, j);
            }
        }
        normalize(0);
    }

    private boolean isZero(int row, int column) {
        return rows[row].isZero(column);
    }

    private void swapRows(int row1, int row2) {
        Row row = rows[row1];
        rows[row1] = rows[row2];
        rows[row2] = row;

        System.out.printf("R%d <-> R%d%n", row1 + 1, row2 + 1);
    }

    private void swapColumns(int column1, int column2) {
        for (Row row : rows) {
            row.swapColumns(column1, column2);
        }
        swaps.add(new int[] {column1, column2});
        System.out.printf("C%d <-> C%d%n", column1 + 1, column2 + 1);
    }

    private void normalize(int i) {
        ComplexNumber factor = rows[i].normalize(i);
        if (!factor.isOne()) {
            System.out.printf("1/%s * R%d -> R%d%n", factor, i + 1, i + 1);
        }
    }

    private void reduce(int i, int j) {
        ComplexNumber factor = rows[j].reduce(rows[i], i);
        if (!factor.isZero()) {
            System.out.printf("%s * R%d + R%d -> R%d%n", factor, i + 1, j + 1, j + 1);
        }
    }

    public ComplexNumber[] getSolution() {
        ComplexNumber[] solution = Arrays.stream(rows)
                .limit(numberOfVariables)
                .map(Row::getSolution)
                .toArray(ComplexNumber[]::new);

        for (int i = swaps.size() - 1; i >= 0; i++) {
            int[] swap = swaps.get(i);
            ComplexNumber value = solution[swap[0]];
            solution[swap[0]] = solution[swap[1]];
            solution[swap[1]] = value;
        }

        return solution;
    }

    public boolean isImpossible() {
        System.out.println(toString());
        return Arrays.stream(rows).anyMatch(Row::isImpossible);
    }

    public boolean hasInfiniteSolutions() {
        if (numberOfVariables > numberOfEquations) {
            return true;
        }
        if (isZero(numberOfVariables - 1, numberOfVariables - 1)) {
            return true;
        }

        return false;
    }
}
