import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.Vector;

public class GraphCanvas extends JPanel {
	public GraphCanvas() {
		// Initial setup.
		setPreferredSize(new Dimension(400, 400));
		scale_x = scale_y = 40;
		x_min = y_min = 0.0;
		x_max = y_max = 10.0;
		x_data = null;
		y_data = null;
	}

	public void updateGraphData(Vector<GraphMath.Point2D> points,
		double minx, double maxx, double miny, double maxy){
		
			x_min = minx;
			x_max = maxx;
			y_min = miny;
			y_max = maxy;

			updateScale();

			if(points != null){
				x_data = new Vector<Integer>();
				y_data = new Vector<Integer>();
				for(GraphMath.Point2D p : points){
					x_data.add(toScreenX(p.x));
					y_data.add(toScreenY(p.y));
				}
			}
	}

	//--------------------------------------------------

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.white);
		updateScale();

		/*System.out.format("-> v(%d, %d), x[%f, %f], y[%f, %f]",
			x_data == null ? -1 : x_data.size(),
			y_data == null ? -1 : y_data.size(),
			x_min, x_max, y_min, y_max
		);
		if(x_data != null && y_data != null){
		System.out.format(" x: {%d, %d, ...}, y: {%d, %d, ...}\n",
			x_data.get(0), x_data.get(1), x_data.get(2),
			y_data.get(0), y_data.get(1), y_data.get(2)
		);
		}*/

		paintGrid(g);
		paintGraph(g);
	}

	private void paintGrid(Graphics g){
		Dimension dim = getSize();
		Graphics2D g2d = (Graphics2D)g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);
		
		updateScale();

		g2d.setColor(new Color(0x99, 0x99, 0x99));
		g2d.setStroke(dashed);

		// Vertical grid lines.
		double x = x_min + (1.0 - x_min % 1.0);
		while(x <= x_max){
			drawVertical(g2d, x);
			x += 1.0;
		}

		// Horizontal grid lines.
		double y = y_min + (1.0 - y_min % 1.0);
		while(y <= y_max){
			drawHorizontal(g2d, y);
			y += 1.0;
		}

		// Coordinate system axes.
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.black);
		drawHorizontal(g2d, 0);
		drawVertical(g2d, 0);
	}

	private void paintGraph(Graphics g){
		if(x_data == null || y_data == null)
			return;
		
		int sz = x_data.size();
		if(sz != y_data.size()){
			System.out.println("[WARNING] X_data & Y_data mismatch.");
			return;
		}

		g.setColor(Color.RED);
		Graphics2D g2d = (Graphics2D)g.create();

		for(int i = 1; i != sz; ++i)
			g.drawLine(x_data.get(i-1), y_data.get(i-1), x_data.get(i), y_data.get(i));
	}

	private void drawHorizontal(Graphics2D g2d, double y){
		int a = toScreenY(y);
		g2d.drawLine(0, a, getWidth(), a);
	}

	private void drawVertical(Graphics2D g2d, double x){
		int a = toScreenX(x);
		g2d.drawLine(a, 0, a, getHeight());
	}

	private int toScreenX(double x){
		return (int)(scale_x * (x - x_min));
	}

	private int toScreenY(double y){
		return (int)(scale_y * (y_max - y));
	}

	//--------------------------------------------------

	private void updateScale(){
		scale_x = getWidth() / (x_max - x_min);
		scale_y = getHeight() / (y_max - y_min);
	}

	private double scale_x, scale_y;
	private double x_min, x_max, y_min, y_max;
	private Vector<Integer> x_data;
	private Vector<Integer> y_data;
}


/*public class GraphCanvas extends Canvas{
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
}*/