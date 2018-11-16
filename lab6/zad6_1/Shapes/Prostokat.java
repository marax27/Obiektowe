package Shapes;

import java.awt.*;

public class Prostokat extends Kwadrat{
	public Prostokat(double _a, double _b){
		super(_a);
		setB(_b);
	}
	
	@Override
	public void draw(Graphics g, Pos2D pos) {
		g.drawRect(pos.x,  pos.y,  (int)a, (int)b);
	}

	public void setB(double _b){
		if(_b <= 0.0)
			throw new IllegalArgumentException();
		b = _b;
	}
	public double getB(){ return b; }

	private double b;
}