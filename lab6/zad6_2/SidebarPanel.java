import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.lang.Math;

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

	public void updatePolynomialDegree(int degree){
		// Note: number of coefficient fields = degree + 1
		int new_number_of_fields = degree + 1;
		int current_number_of_fields = lower_panel.getComponents().length;
		
		if(new_number_of_fields < 1){
			// That's not supposed to happen. Throw?
			System.out.format("[WARNING] updatePolynomialDegree(): received invalid degree: %d.\n", degree);
		}

		int diff = Math.abs(new_number_of_fields - current_number_of_fields);
		if(new_number_of_fields > current_number_of_fields){
			// Add some ParameterPanels.
			while(diff-- > 0){
				ParameterPanel pp = new ParameterPanel(
					"a_" + (new_number_of_fields - diff - 1)
				);
				addToLower(pp);
			}
		}
		else if(new_number_of_fields < current_number_of_fields){
			// Remove excess ParameterPanels.
			Component[] clist = lower_panel.getComponents();
			for(int i = current_number_of_fields-1; i > current_number_of_fields-1-diff; --i)
				lower_panel.remove(clist[i]);
		}
		else return;

		lower_panel.revalidate();
		lower_panel.repaint();
	}
}