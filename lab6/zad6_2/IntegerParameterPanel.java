import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class IntegerParameterPanel extends JPanel {
	protected JLabel label;
	protected JTextField text_field;
	protected JButton button_decrement;
	protected JButton button_increment;
	
	private static final Dimension BUTTON_SIZE;
	static{
		BUTTON_SIZE = new Dimension(40, 20);
	}

	public IntegerParameterPanel(String label_message) {
		super(new GridBagLayout());
		initialize(label_message);
		setupEvents();
	}

	protected void initialize(String label_message){
		// Create a label.
		label = new JLabel(label_message);
		GridBagConstraints c = GBConstraintsFactory.getGBConstraints(0, 0);
		c.anchor = GridBagConstraints.WEST;
		add(label, c);

		button_decrement = new JButton("-");
		button_decrement.setPreferredSize(BUTTON_SIZE);
		add(button_decrement, GBConstraintsFactory.getGBConstraints(1, 0));

		text_field = new JTextField("12");
		text_field.setPreferredSize(new Dimension(
			text_field.getPreferredSize().width, BUTTON_SIZE.height
		));
		text_field.setText("0");
		text_field.setEditable(false);
		text_field.setBorder(null);
		add(text_field, GBConstraintsFactory.getGBConstraints(2, 0, 1.0f, 1.0f));

		button_increment = new JButton("+");
		button_increment.setPreferredSize(BUTTON_SIZE);
		add(button_increment, GBConstraintsFactory.getGBConstraints(3, 0));

		/*button_decrement = new JButton("-");
		c = GBConstraintsFactory.getGBConstraints(1, 0);
		button_decrement.setMinimumSize(button_decrement.getPreferredSize());
		add(button_decrement, c);

		text_field = new JTextField("12");
		text_field.setEditable(false);  //user can't directly change value
		text_field.setPreferredSize(text_field.getPreferredSize());
		text_field.setText("");
		c = GBConstraintsFactory.getGBConstraints(2, 0);
		// c.anchor = GridBagConstraints.EAST;
		// c.weightx = 1.0;
		add(text_field, c);

		button_increment = new JButton("-");
		c = GBConstraintsFactory.getGBConstraints(3, 0);
		button_increment.setMinimumSize(button_increment.getPreferredSize());
		add(button_decrement, c);*/
	}

	protected void setupEvents(){
		// Value decrementation.
		button_decrement.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int value = Integer.parseInt(text_field.getText());
				if(value > 0)
					text_field.setText(Integer.toString(value-1));
			}
		});

		// Value incrementation.
		button_increment.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int value = Integer.parseInt(text_field.getText());
				text_field.setText(Integer.toString(value+1));
			}
		});
	}

	public JTextField getTextField(){
		return text_field;
	}
	public JButton getDecrementButton(){
		return button_decrement;
	}
	public JButton getIncrementButton(){
		return button_increment;
	}
}
