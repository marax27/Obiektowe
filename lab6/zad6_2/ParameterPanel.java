import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ParameterPanel extends JPanel {
	public ParameterPanel(String left_message, String right_message){
		super(new GridBagLayout());
		initialize(left_message, right_message);
	}

	private void initialize(String left_message, String right_message){
		// GridBagConstraints c = new GridBagConstraints();

		// Create a label on the left.
		JLabel lbl_left = new JLabel(left_message);
		GridBagConstraints c = GBConstraintsFactory.getGBConstraints(0, 0);
		c.anchor = GridBagConstraints.WEST;
		add(lbl_left, c);

		// Create a text field. Some cheating is involved: passed string
		// helps set a proper preferred size of a component, but isn't
		// used in runtime.
		JTextField text_field = new JTextField("1234.12345");
		text_field.setPreferredSize(text_field.getPreferredSize());
		text_field.setText("");
		c = GBConstraintsFactory.getGBConstraints(1, 0);
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1.0;
		add(text_field, c);
	}
}