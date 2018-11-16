import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class GraphCanvas extends Canvas{
	public GraphCanvas(){
		setBackground(Color.WHITE);
	}

	public GraphCanvas(Dimension dim){
		this();
		setPreferredSize(dim);
	}

	@Override
	public void paint(Graphics g){
		g.setColor(new Color(0x99, 0x99, 0x99));
		Dimension dim = getSize();

		Graphics2D g2d = (Graphics2D)g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);
		g2d.setStroke(dashed);

		for(int i=1; i!=10; ++i){
			g2d.drawLine(0, i*dim.height/10, dim.width, i*dim.height/10);
			g2d.drawLine(i*dim.width/10, 0, i*dim.width/10, dim.height);
		}
	}
}