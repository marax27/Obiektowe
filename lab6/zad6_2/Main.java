import java.awt.EventQueue;

import java.awt.Frame;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Main{
	private JFrame frame;
	private JPanel main_panel;
	private GraphCanvas canvas;
	private JPanel sidebar_panel;

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
		frame = new JFrame();
		frame.setTitle("Zadanie 6.2 ~Kacper Tonia");
		// frame.setSize(450, 450);
		// frame.setBounds(200, 100, 450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		main_panel = new JPanel();
		main_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Main Panel"
		));
		// main_panel.setPreferredSize(new Dimension(300, 150));

		canvas = new GraphCanvas(new Dimension(400, 400));
		main_panel.add(canvas);

		sidebar_panel = new JPanel();
		sidebar_panel.setLayout(new BoxLayout(sidebar_panel, BoxLayout.Y_AXIS));

		String[] s = {"abc", "129312", "r0iu9ajs", "+"};
		for(int i=0; i!=4; ++i){
			// JButton btn = new JButton("Message" + i);
			ParameterPanel pp = new ParameterPanel(s[i], "cm");
			sidebar_panel.add(pp);
		}
		main_panel.add(sidebar_panel);

		frame.add(main_panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
}