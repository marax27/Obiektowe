package Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape{
	public Rectangle(double _a, double _b, Pos2D _pos){
		setA(_a);
		setB(_b);
		setPos(_pos);
	}
	
	public void draw(GraphicsContext g, Color c) {
	    g.setFill(c);
	    g.fillRect(pos.x,  pos.y,  (int)a, (int)b);
	}

	public void setA(double _a){
		if(_a <= 0.0)
			throw new IllegalArgumentException();
		a = _a;
	}
	public void setB(double _b){
		if(_b <= 0.0)
			throw new IllegalArgumentException();
		b = _b;
	}
	public void setPos(Pos2D _pos){
		pos = _pos;
	}
	public double getA(){ return a; }
	public double getB(){ return b; }
	public Pos2D getPos(){ return pos; }

	private double a, b;
	private Pos2D pos;
}