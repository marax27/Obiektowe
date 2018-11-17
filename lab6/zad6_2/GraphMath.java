import java.util.Vector;


// The class responsible for graph-related computations.
public class GraphMath {

	public static Vector<Point2D> plotPolynomial(
		double[] coefficients, double x_min, double x_max, double dx) {
		
		// Compute all necessary points of a polynomial
		// in range [x_min, x_max], and return in a form
		// of Vector of 2D points.
		// -----
		// To evaluate a polynomial, Horner's method is used:
		// https://en.wikipedia.org/wiki/Horner%27s_method
		
		Vector<Point2D> result = new Vector<Point2D>();

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
			result.add(new Point2D(x, y));
			++i;
			x = x_min + i*dx;
		}

		return result;
	}


	//--------------------------------------------------
	public static class Point2D{
		public double x, y;

		public Point2D(){
			x = 0;
			y = 0;
		}

		public Point2D(double _x, double _y){
			x = _x;
			y = _y;
		}
	}
	//--------------------------------------------------

}