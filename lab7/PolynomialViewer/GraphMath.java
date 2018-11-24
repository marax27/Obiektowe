package PolynomialViewer;

import java.text.DecimalFormat;
import javafx.scene.chart.XYChart;

// The class responsible for graph-related mathematics.
public class GraphMath {

    public static XYChart.Series<Double, Double> plotPolynomial(
            double[] coefficients, double x_min, double x_max, double dx) {

        // Compute all necessary points of a polynomial
        // in range [x_min, x_max], and return in a form
        // of XYChart.
        // -----
        // To evaluate a polynomial, Horner's method is used:
        // https://en.wikipedia.org/wiki/Horner%27s_method

        XYChart.Series<Double, Double> result = new XYChart.Series();

        int degree = coefficients.length - 1;
        double x = x_min;
        int i = 0;
        while(x <= x_max){

            // Compute y(x) for given x.
            int j = degree;
            double y = coefficients[j];
            while(j > 0){
                y = x*y + coefficients[j-1];
                --j;
            }

            // Update.
            result.getData().add(new XYChart.Data(x, y));
            ++i;
            x = x_min + i*dx;
        }

        return result;
    }

    public static String polynomialToString(double[] coefficients){
        StringBuilder sb = new StringBuilder();
        boolean largest = true;
        DecimalFormat df = new DecimalFormat("0.###");

        for(int i = coefficients.length-1; i >= 0; --i){
            double a_i = coefficients[i];
            if(a_i == 0.0)
                continue;

            // Coefficient a_i, with sign.
            if(largest){
                if(Math.abs(a_i) != 1.0 || i == 0)
                    sb.append(df.format(a_i));
                else if(a_i == -1)
                    sb.append('-');
                largest = false;
            }else{
                sb.append(" " + (a_i > 0 ? "+ " : "- "));
                if(Math.abs(a_i) != 1.0 || i == 0)
                    sb.append( df.format(Math.abs(a_i)) );
            }

            // x^i.
            switch(i){
                case 0:
                    break;
                case 1:
                    sb.append("x");
                    break;
                default:
                    sb.append("x^" + i);
                    break;
            }
        }

        String result = sb.toString();
        return result.isEmpty() ? "0" : result;
    }

    //--------------------------------------------------

}
