package Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {
	public Circle(double _r, Pos2D _pos) {
		setR(_r);
		setPos(_pos);
	}
	
	public void draw(GraphicsContext g, Color c) {
	    g.setFill(c);
		g.fillOval(pos.x, pos.y, radius, radius);
	}

	public void setR(double _r) {
		if(_r <= 0.0)
			throw new IllegalArgumentException();
		radius = _r;
	}
	public void setPos(Pos2D _pos){
		pos = _pos;
	}
	public double getR() { return radius; }
	public Pos2D getPos(){ return pos; }
	
	private double radius;
	private Pos2D pos;
}
