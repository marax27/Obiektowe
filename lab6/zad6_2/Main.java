import java.awt.EventQueue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.util.Vector;

public class Main {
	private JFrame frame;
	private JPanel main_panel;
	private GraphCanvas canvas;
	private SidebarPanel sidebar_panel;

	private JTextField txtfield_xmin,
	                   txtfield_xmax,
	                   txtfield_ymin,
	                   txtfield_ymax,
	                   txtfield_stepsize,
	                   txtfield_polynomial_degree;
	
	private JButton button_draw;

	private JLabel label_infodump;

	Main(){
		initialize();
		setupEvents();
	}

	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main main_window = new Main();
					main_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialize(){
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e){
			JFrame.setDefaultLookAndFeelDecorated(true);
		}

		frame = new JFrame("Zadanie 6.2 ~Kacper Tonia");
		// frame.setSize(450, 450);
		// frame.setBounds(200, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		main_panel = new JPanel(new GridBagLayout());
		/*main_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Main Panel"
		));
		main_panel.setPreferredSize(new Dimension(300, 150));*/

		canvas = new GraphCanvas();
		GridBagConstraints c = GBConstraintsFactory.getGBConstraints(0, 0);
		c.weightx = c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		main_panel.add(canvas, c);

		sidebar_panel = new SidebarPanel();
		// sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS));

		// Upper part of sidebar.
		ParameterPanel pp;
		pp = new ParameterPanel("min x:");
		txtfield_xmin = pp.getTextField();
		sidebar_panel.addToUpper(pp);

		pp = new ParameterPanel("max x:");
		txtfield_xmax = pp.getTextField();
		sidebar_panel.addToUpper(pp);

		pp = new ParameterPanel("min y:");
		txtfield_ymin = pp.getTextField();
		sidebar_panel.addToUpper(pp);

		pp = new ParameterPanel("max y:");
		txtfield_ymax = pp.getTextField();
		sidebar_panel.addToUpper(pp);

		pp = new ParameterPanel("step size:");
		txtfield_stepsize = pp.getTextField();
		sidebar_panel.addToUpper(pp);

		IntegerParameterPanel ipp = new IntegerParameterPanel("polynomial degree:");
		txtfield_polynomial_degree = ipp.getTextField();
		sidebar_panel.addToUpper(ipp);

		c = GBConstraintsFactory.getGBConstraints(1, 0);
		c.fill = GridBagConstraints.VERTICAL;
		main_panel.add(sidebar_panel, c);

		button_draw = new JButton("Draw");
		sidebar_panel.addToControl(button_draw);

		// InfoDump label.
		label_infodump = new JLabel("Hello!", SwingConstants.LEFT);
		c = GBConstraintsFactory.getGBConstraints(0, 1);
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, 4, 2, 4);
		main_panel.add(label_infodump, c);

		frame.add(main_panel);
		frame.pack();
		frame.setLocationRelativeTo(null);

		// Some extra work.
		txtfield_polynomial_degree.setText("2");
		sidebar_panel.updatePolynomialDegree(2);
	}

	private void setupEvents(){
		txtfield_polynomial_degree.getDocument().addDocumentListener(new DocumentListener() {
			@Override public void changedUpdate(DocumentEvent e) {}
			@Override public void removeUpdate(DocumentEvent e) {}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// Update number of coefficient fields in the Coefficient panel.
				int value = Integer.parseInt(txtfield_polynomial_degree.getText());
				sidebar_panel.updatePolynomialDegree(value);
			}
		});

		// Graph plotting.
		button_draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					// Gather necessary data.
					double[] coefficients = sidebar_panel.getCoefficients();
					double x_min, x_max, step, y_min, y_max;
					
					// Min x.
					try{
						x_min = Double.parseDouble(txtfield_xmin.getText());
					}catch(NumberFormatException exc){
						throw new RuntimeException("Min. x is not a valid number: '" + txtfield_xmin.getText() + "'.");
					}

					// Max x.
					try{
						x_max = Double.parseDouble(txtfield_xmax.getText());
					}catch(NumberFormatException exc){
						throw new RuntimeException("Max. x is not a valid number: '" + txtfield_xmax.getText() + "'.");
					}

					// Step size.
					try{
						step = Double.parseDouble(txtfield_stepsize.getText());
					}catch(NumberFormatException exc){
						throw new RuntimeException("Step size is not a valid number: '" + txtfield_stepsize.getText() + "'.");
					}
					if(step <= 0.0)
						throw new RuntimeException("Invalid step size: " + step + ". It must be greater than 0.");

					// Compute graph points.
					Vector<GraphMath.Point2D> xy = GraphMath.plotPolynomial(
						coefficients, x_min, x_max, step
					);

					// Min y.
					try{
						y_min = Double.parseDouble(txtfield_ymin.getText());
					}catch(NumberFormatException exc){
						throw new RuntimeException("Min. y is not a valid number: '" + txtfield_ymin.getText() + "'.");
					}

					// Max y.
					try{
						y_max = Double.parseDouble(txtfield_ymax.getText());
					}catch(NumberFormatException exc){
						throw new RuntimeException("Max. y is not a valid number: '" + txtfield_ymax.getText() + "'.");
					}

					// Send data to canvas.
					canvas.updateGraphData(xy, x_min, x_max, y_min, y_max);

					canvas.revalidate();
					canvas.repaint();

					String polynomial = GraphMath.polynomialToString(coefficients);
					label_infodump.setText("Plotted W(x) = " + polynomial);

				}catch(RuntimeException exc){
					label_infodump.setText("[ERROR] " + exc.getMessage());
				}
			}
		});
	}
}