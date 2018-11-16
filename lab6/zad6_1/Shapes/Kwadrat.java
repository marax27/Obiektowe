package Shapes;

import java.awt.*;

public class Kwadrat implements Shape{
	public Kwadrat(double _a){
		setA(_a);
	}
	
	public void draw(Graphics g, Pos2D pos) {
		int side = (int)a;
		g.drawRect(pos.x, pos.y, side, side);
	}

	public void setA(double _a){
		if(_a <= 0.0)
			throw new IllegalArgumentException();
		a = _a;
	}
	public double getA(){ return a; }

	protected double a;
}