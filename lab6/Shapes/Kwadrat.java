package Shapes;

import java.lang.*;

public class Kwadrat implements Shape{
	Kwadrat(double _a){
		setA(_a);
	}

	public void setA(double _a){
		if(_a <= 0.0)
			throw new IllegalArgumentException();
		a = _a;
	}
	public double getA(){ return a; }

	private double a;
}