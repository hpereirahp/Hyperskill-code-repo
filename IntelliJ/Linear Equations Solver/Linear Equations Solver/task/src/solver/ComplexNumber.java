package solver;

public class ComplexNumber {

    private double real;
    private double imaginary;

    public ComplexNumber(ComplexNumber number) {
        this.real = number.real;
        this.imaginary = number.imaginary;
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(String value) {
        int i = value.indexOf('i');
        if (i == -1) {
            real = Double.parseDouble(value);
            imaginary = 0;
        } else if (i == 0) {
            real = 0;
            imaginary = 1;
        } else if (value.equals("-i")) {
            real = 0;
            imaginary = -1;
        } else {
            int index = Math.max(value.lastIndexOf('+'), value.lastIndexOf('-'));
            if (index <= 0) {
                real = 0;
                imaginary = Double.parseDouble(value.replaceAll("i", ""));
            } else {
                real = Double.parseDouble(value.substring(0, index));
                String val = value.substring(index).replaceAll("i", "");
                if (val.equals("+") || val.equals("-")) {
                    val += "1";
                }
                imaginary = Double.parseDouble(val);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (real != 0.0 || imaginary == 0.0) {
            sb.append(format(real));
        }
        if (imaginary != 0.0) {
            if (imaginary > 0.0 && real != 0.0) {
                sb.append("+");
            }
            if (imaginary == -1.0) {
                sb.append("-");
            } else if (imaginary != 1.0) {
                sb.append(format(imaginary));
            }
            sb.append("i");
        }
        return sb.toString();
    }

    public void add(ComplexNumber number) {
        real = real + number.real;
        imaginary = imaginary + number.imaginary;
    }

    public void multiply(ComplexNumber number) {
        double oldReal = real;
        double oldImaginary = imaginary;
        real = oldReal * number.real - oldImaginary * number.imaginary;
        imaginary = oldImaginary * number.real + number.imaginary * oldReal;
    }

    public void multiply(int scalar) {
        real *= scalar;
        imaginary *= scalar;
    }

    public void divide(ComplexNumber number) {
//        System.out.printf("Values: %f %f %f %f%n", real, imaginary, number.real, number.imaginary);

        double oldReal = real;
        double oldImaginary = imaginary;
        double divisor = number.getModule();
        real = ((oldReal * number.real) + (oldImaginary * number.imaginary)) / divisor;
        imaginary = ((oldImaginary * number.real) - (oldReal * number.imaginary)) / divisor;

//        System.out.printf("After: %f %f %f %f%n", real, imaginary, number.real, number.imaginary);
    }

    public double getModule() {
        return real * real + imaginary * imaginary;
    }

    public boolean isZero() {
        return real == 0.0 && imaginary == 0.0;
    }
    public boolean isOne() {
        return real == 1.0 && imaginary == 0.0;
    }

    public String format(double d) {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
