import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import java.lang.Math;

public class SidebarPanel extends JPanel {
	private JPanel upper_panel,
	               lower_panel,
	               control_panel;

	public SidebarPanel(){
		setLayout(new GridBagLayout());
		initialize();
	}

	private void initialize(){
		this.setBorder(BorderFactory.createEtchedBorder());

		// Upper panel.
		upper_panel = new JPanel();
		upper_panel.setLayout(new BoxLayout(upper_panel, BoxLayout.Y_AXIS));
		upper_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "General"
		));

		// Lower panel.
		lower_panel = new JPanel();
		lower_panel.setLayout(new BoxLayout(lower_panel, BoxLayout.Y_AXIS));
		lower_panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createEtchedBorder(), "Coefficients"
		));

		JScrollPane scroll_lower = new JScrollPane(lower_panel);
		scroll_lower.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));

		// Control panel.
		control_panel = new JPanel(new GridLayout(1, 0));
		control_panel.setBorder(BorderFactory.createEtchedBorder());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.gridx = 0;
		c.anchor = GridBagConstraints.NORTH;

		add(upper_panel, c);
		c.weighty = 1;
		// add(lower_panel, c);
		add(scroll_lower, c);
		c.weighty = 0;
		add(control_panel, c);
	}

	// Add a component to upper_panel.
	public Component addToUpper(Component c){
		upper_panel.add(c);
		return c;
	}

	// Add a component to lower_panel.
	public Component addToLower(Component c){
		lower_panel.add(c);
		return c;
	}

	// Add a component to control_panel.
	public Component addToControl(Component c){
		control_panel.add(c);
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

	public double[] getCoefficients(){
		// Read coefficients from all lower_panel fields.

		int n = lower_panel.getComponents().length;
		double[] result = new double[n];

		for(int i = 0; i != n; ++i){
			String txt = "";
				ParameterPanel pp = (ParameterPanel)(lower_panel.getComponent(i));
			try{
				txt = pp.getTextField().getText();
				result[i] = Double.parseDouble(txt);
			}catch(Exception exc){
				throw new RuntimeException(
					String.format("Invalid coefficient (not a valid number: '%s').", txt
				));
			}
		}

		return result;
	}
}