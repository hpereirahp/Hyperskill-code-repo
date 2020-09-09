package solver;

public class GaussJordanElimination {

    public static ComplexNumber[] solve(Matrix matrix) {
        matrix.buildEchelon();
        if (matrix.isImpossible()) {
            return null;
        }
        if (matrix.hasInfiniteSolutions()) {
            return new ComplexNumber[0];
        }
        matrix.backSolve();
        return matrix.getSolution();
    }
}
