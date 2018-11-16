package Shapes;

import java.lang.*;

public class Prostokat extends Kwadrat{
	Prostokat(double _a, double _b){
		super(_a);
		setB(_b);
	}

	public void setB(double _b){
		if(_b <= 0.0)
			throw new IllegalArgumentException();
		b = _b;
	}
	public double getB(){ return b; }

	private double b;
}