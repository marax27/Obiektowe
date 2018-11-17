import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.Vector;
import java.lang.Math;

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

			if(minx >= maxx)
				throw new RuntimeException("Invalid x range. Max. x must be greater than min. x.");
			if(miny >= maxy)
				throw new RuntimeException("Invalid y range. Max. y must be greater than min. y.");

			x_min = minx;
			x_max = maxx;
			y_min = miny;
			y_max = maxy;

			updateScale();

			if(points != null){
				x_data = new Vector<Double>();
				y_data = new Vector<Double>();
				for(GraphMath.Point2D p : points){
					x_data.add(p.x);
					y_data.add(p.y);
				}
			}
	}

	//--------------------------------------------------

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.white);
		updateScale();

		paintGrid(g);
		paintGraph(g);
		paintValues(g);
	}

	private void paintGrid(Graphics g){
		Dimension dim = getSize();
		Graphics2D g2d = (Graphics2D)g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);
		
		updateScale();

		g2d.setColor(new Color(0x99, 0x99, 0x99));
		g2d.setStroke(dashed);

		// In order to keep number of gridlines under control,
		// some magic is involved.
		int exponent = (int)Math.log10((x_max - x_min)/5.0);
		double grid_step = Math.pow(10, exponent);

		// Vertical grid lines.
		double x = x_min + (grid_step - x_min % grid_step);
		while(x <= x_max){
			drawVertical(g2d, x);
			x += grid_step;
		}

		exponent = (int)Math.log10((y_max - y_min)/5.0);
		grid_step = Math.pow(10, exponent);

		// Horizontal grid lines.
		double y = y_min + (grid_step - y_min % grid_step);
		while(y <= y_max){
			drawHorizontal(g2d, y);
			y += grid_step;
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

		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(2));

		for(int i = 1; i != sz; ++i){
			g2d.drawLine(
				toScreenX(x_data.get(i-1)), toScreenY(y_data.get(i-1)),
				toScreenX(x_data.get(i)), toScreenY(y_data.get(i))
			);
		}
	}

	private void paintValues(Graphics g){
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.black);
		g2d.setFont(g.getFont().deriveFont(Font.BOLD));

		FontMetrics metrics = getFontMetrics(g2d.getFont());

		g2d.drawString(Double.toString(y_min), 5, getHeight()-15);
		g2d.drawString(Double.toString(y_max), 5, 10);
		g2d.drawString(Double.toString(x_min), 15, getHeight()-5);
		String x_max_str = Double.toString(x_max);
		g2d.drawString(x_max_str, getWidth()-5-metrics.stringWidth(x_max_str), getHeight()-5);
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
	private Vector<Double> x_data;
	private Vector<Double> y_data;
}
