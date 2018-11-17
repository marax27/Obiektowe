import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

import java.util.Vector;

public class GraphCanvas extends Canvas{
	public GraphCanvas(){
		setBackground(Color.WHITE);
		graph_data = null;
	}

	public GraphCanvas(Dimension dim){
		this();
		setPreferredSize(dim);
	}

	// ************************************************************

	@Override
	public void paint(Graphics g){
		paintGrid(g);
		paintPlot(g);
	}

	private void paintGrid(Graphics g){
		g.setColor(new Color(0x99, 0x99, 0x99));
		Dimension dim = getSize();
		Graphics2D g2d = (Graphics2D)g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);
		g2d.setStroke(dashed);

		for(int i = 1; i != 10; ++i){
			g2d.drawLine(0, i*dim.height/10, dim.width, i*dim.height/10);
			g2d.drawLine(i*dim.width/10, 0, i*dim.width/10, dim.height);
		}
	}

	public void paintPlot(Graphics g){
		if(graph_data == null)
			return;

		System.out.println("Drawing...");
		g.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D)g.create();

		GraphMath.Point2D previous_point = graph_data.elementAt(0);
		for(GraphMath.Point2D point : graph_data){
			if(point == previous_point)
				continue;
			g2d.drawLine((int)previous_point.x, (int)previous_point.y, (int)point.x, (int)point.y);
			previous_point = point;
		}
	}

	// ************************************************************

	public void updatePlotData(Vector<GraphMath.Point2D> points,
		double x_min, double x_max, double y_min, double y_max){

		graph_data = points;
		this.x_min = x_min;
		this.x_max = x_max;
		this.y_min = y_min;
		this.y_max = y_max;

		// Most 2D libraries put (0,0) point of coordinate system
		// in top left corner. We'll take that into consideration.
		for(int i = 0; i != graph_data.size(); ++i){
			// graph_data.elementAt(i).y -= y_min;
			graph_data.elementAt(i).y = getHeight() - graph_data.elementAt(i).y;
			graph_data.elementAt(i).x -= x_min;
		}

		repaint();
	}

	private Vector<GraphMath.Point2D> graph_data;
	private double x_min, x_max, y_min, y_max;
}