package solver;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Row {

    private ComplexNumber[] values;

    public Row(String[] values) {
        this.values = Arrays.stream(values)
                .map(ComplexNumber::new)
                .toArray(ComplexNumber[]::new);
    }

    @Override
    public String toString() {
        return String.format("[%s]", Arrays.stream(values)
                .map(ComplexNumber::toString)
                .collect(Collectors.joining(" ")));
    }

    public ComplexNumber getSolution() {
        return values[values.length - 1];
    }

    public ComplexNumber normalize (int index) {
        if (values[index].isZero()) {
            return new ComplexNumber(1.0, 0);
        }
        ComplexNumber factor = new ComplexNumber(values[index]);
//        System.out.printf("Factor Normalize: %s, %d%n", factor, index);

//        System.out.printf("Before Normalize: %s%n", toString());

        for (int i = index; i < values.length; i++) {
            values[i].divide(factor);
        }

//        System.out.printf("After Normalize: %s%n", toString());
        return factor;
    }

    public ComplexNumber reduce (Row row, int index) {
        ComplexNumber factor = new ComplexNumber(values[index]);
        factor.divide(row.values[index]);
        factor.multiply(-1);
        for (int i = index; i < values.length; i++) {
            ComplexNumber toAdd = new ComplexNumber(row.values[i]);
            toAdd.multiply(factor);
            values[i].add(toAdd);
        }
        return factor;
    }

    public boolean isZero(int column) {
        return values[column].isZero();
    }

    public void swapColumns(int column1, int column2) {
        ComplexNumber value = values[column1];
        values[column1] = values[column2];
        values[column2] = value;
    }

    public boolean isImpossible() {
        if (values[values.length - 1].isZero()) {
            return false;
        }
        for (int i = 0; i < values.length - 1; i++) {
            if (!values[i].isZero()) {
                return false;
            }
        }
        return true;
    }
}
