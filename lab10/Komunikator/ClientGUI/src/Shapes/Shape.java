package Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public interface Shape extends Serializable {
	public abstract void draw(GraphicsContext g, Color c);

	public void setPos(Pos2D _pos);
	public Pos2D getPos();
}

