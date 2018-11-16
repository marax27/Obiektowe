import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class GBConstraintsFactory{
	public static GridBagConstraints getGBConstraints(int x_pos, int y_pos, float weight_x, float weight_y){
		GridBagConstraints result = getGBConstraints(x_pos, y_pos);
		result.weightx = weight_x;
		result.weightx = weight_y;
		return result;
	}

	public static GridBagConstraints getGBConstraints(int x_pos, int y_pos){
		GridBagConstraints result = new GridBagConstraints();
		result.gridx = x_pos;
		result.gridy = y_pos;

		result.ipadx = 5;
		result.ipady = 5;
		return result;
	}
}