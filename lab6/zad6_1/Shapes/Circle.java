package Shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle implements Shape {
	public Circle(double _r) {
		setR(_r);
	}
	
	public void draw(Graphics g, Pos2D pos) {
		Graphics2D g2 = (Graphics2D)g;
		Ellipse2D ellipse = new Ellipse2D.Double(pos.x, pos.y, radius, radius);
		
//        g2.fill(ellipse);
        g2.draw(ellipse);

	}

	public void setR(double _r) {
		if(_r <= 0.0)
			throw new IllegalArgumentException();
		radius = _r;
	}
	
	public double getR() { return radius; }
	
	private double radius;

}
