import java.awt.EventQueue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Main{
	private JFrame frame;
	private JPanel main_panel;
	private GraphCanvas canvas;
	private SidebarPanel sidebar_panel;

	private JTextField txtfield_xmin,
	                   txtfield_xmax,
	                   txtfield_ymin,
	                   txtfield_ymax,
	                   txtfield_stepsize,
	                   txtfield_polydegree;

	private JLabel label_infodump;

	Main(){
		initialize();
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

		canvas = new GraphCanvas(new Dimension(400, 400));
		main_panel.add(canvas, GBConstraintsFactory.getGBConstraints(0, 0));

		sidebar_panel = new SidebarPanel();
		sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS));

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
		txtfield_polydegree = ipp.getTextField();
		sidebar_panel.addToUpper(ipp);

		String[] s = {"abc", "129312", "r0iu9ajs", "+"};
		for(int i=0; i!=4; ++i){
			// JButton btn = new JButton("Message" + i);
			ParameterPanel pp1 = new ParameterPanel(s[i]);
			sidebar_panel.addToLower(pp1);
		}
		main_panel.add(sidebar_panel, GBConstraintsFactory.getGBConstraints(1, 0));

		// InfoDump label.
		label_infodump = new JLabel("Hello!", SwingConstants.LEFT);
		GridBagConstraints c = GBConstraintsFactory.getGBConstraints(0, 1);
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, 4, 2, 4);
		main_panel.add(label_infodump, c);

		frame.add(main_panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}