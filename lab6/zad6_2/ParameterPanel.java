import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ParameterPanel extends JPanel {
	protected JLabel label;
	protected JTextField text_field;

	public ParameterPanel(String label_message){
		super(new GridBagLayout());
		initialize(label_message);
	}

	protected void initialize(String label_message){
		// Create a label.
		label = new JLabel(label_message);
		GridBagConstraints c = GBConstraintsFactory.getGBConstraints(0, 0);
		c.anchor = GridBagConstraints.WEST;
		add(label, c);

		// Create a text field. Some cheating is involved: passed string
		// helps set a proper preferred size of a component, but isn't
		// used in runtime.
		text_field = new JTextField("1234.12345");
		text_field.setPreferredSize(text_field.getPreferredSize());
		text_field.setText("0");
		c = GBConstraintsFactory.getGBConstraints(1, 0);
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1.0;
		add(text_field, c);
	}

	public JTextField getTextField(){
		return text_field;
	}
}