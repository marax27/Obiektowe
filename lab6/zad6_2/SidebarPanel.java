import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class SidebarPanel extends JPanel {
	private JPanel upper_panel;
	private JPanel lower_panel;

	public SidebarPanel(){
		initialize();
	}

	private void initialize(){
		this.setBorder(BorderFactory.createEtchedBorder());

		upper_panel = new JPanel();
		upper_panel.setLayout(new BoxLayout(upper_panel, BoxLayout.Y_AXIS));
		upper_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "General"
		));
		lower_panel = new JPanel();
		lower_panel.setLayout(new BoxLayout(lower_panel, BoxLayout.Y_AXIS));
		lower_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Coefficients"
		));

		add(upper_panel);
		add(lower_panel);
	}

	// Add a component to upper panel.
	public Component addToUpper(Component c){
		upper_panel.add(c);
		return c;
	}

	// Add a component to lower panel.
	public Component addToLower(Component c){
		lower_panel.add(c);
		return c;
	}

	@Override
	public Component add(Component c){
		System.out.println("[WARNING] SidebarPanel.add(): unrecommended function.");
		return super.add(c);
	}
}