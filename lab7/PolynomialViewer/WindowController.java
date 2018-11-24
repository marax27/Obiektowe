package PolynomialViewer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.chart.XYChart;

import java.text.NumberFormat;

public class WindowController {
    @FXML private NumberAxis x_axis;
    @FXML private NumberAxis y_axis;
    @FXML private LineChart line_chart;

    @FXML private VBox upper_sidebar;
    @FXML private VBox lower_sidebar;
    @FXML private InfoLabel info_label;

    @FXML private ParameterPane min_x_parameter;
    @FXML private ParameterPane max_x_parameter;
    @FXML private ParameterPane min_y_parameter;
    @FXML private ParameterPane max_y_parameter;
    @FXML private ParameterPane step_size_parameter;
    @FXML private UnsignedIntegerPane poly_degree_parameter;

    @FXML private Button draw_button;

    private UnsignedIntegerPaneController poly_degree_controller;


    @FXML public void initialize(){
        poly_degree_controller = new UnsignedIntegerPaneController(poly_degree_parameter);

        poly_degree_parameter.parameterTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String old_value, String new_value) {
                // Update the list of coefficients, according to value of "Polynomial degree".
                updateCoefficientList(Integer.parseInt(new_value));
            }
        });

        draw_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateGraph();
            }
        });

        x_axis.setAutoRanging(false);
        y_axis.setAutoRanging(false);


        // Default polynomial degree.
        poly_degree_parameter.setParameter("2");
        updateCoefficientList(2);
    }

    //---------------------------------------------------------------------

    private void updateCoefficientList(int degree){
        // Note: number of coefficient fields = degree + 1
        int new_number_of_fields = degree + 1;
        int current_number_of_fields = lower_sidebar.getChildren().size();

        if(new_number_of_fields < 1){
            // That's not supposed to happen. Throw?
            System.out.format("[WARNING] updatePolynomialDegree(): received invalid degree: %d.\n", degree);
        }

        int diff =  Math.abs(new_number_of_fields - current_number_of_fields);
        if(new_number_of_fields > current_number_of_fields){
            // Add some ParameterPanels.
            while(diff-- > 0){
                ParameterPane pp = new ParameterPane();
                pp.setLabel("a_" + (new_number_of_fields - diff - 1));
                lower_sidebar.getChildren().add(pp);
            }
        }
        else if(new_number_of_fields < current_number_of_fields){
            // Remove excess ParameterPanels.
            ObservableList components = lower_sidebar.getChildren();
            for(int i = current_number_of_fields-1; i > current_number_of_fields-1-diff; --i)
                lower_sidebar.getChildren().remove(components.get(i));
        }
    }

    //---------------------------------------------------------------------

    private void updateGraph(){
        // Check if all fields were filled properly.
        if(!validateAllFields())
            return;

        // Gather necessary data.
        XYChart.Series data_series = new XYChart.Series();
        double x_min, x_max, y_min, y_max, step;
        double coefficients[] = getCoefficientArray();

        x_min = Double.parseDouble(min_x_parameter.getParameter());
        x_max = Double.parseDouble(max_x_parameter.getParameter());
        y_min = Double.parseDouble(min_y_parameter.getParameter());
        y_max = Double.parseDouble(max_y_parameter.getParameter());
        step = Double.parseDouble(step_size_parameter.getParameter());

        // Compute graph points.
        data_series = GraphMath.plotPolynomial(coefficients, x_min, x_max, step);

        // Send data to the chart.
        line_chart.getData().clear();
        line_chart.getData().add(data_series);

        // Modify ranges.
        x_axis.setLowerBound(x_min);
        x_axis.setUpperBound(x_max);
        y_axis.setLowerBound(y_min);
        y_axis.setUpperBound(y_max);
    }

    //---------------------------------------------------------------------

    private double[] getCoefficientArray(){
        // Read coefficients from all lower_sidebar fields.

        int n = lower_sidebar.getChildren().size();
        double[] result = new double[n];

        for(int i = 0; i != n; ++i){
            ParameterPane pp = (ParameterPane)(lower_sidebar.getChildren().get(i));
            String txt = pp.getParameter();
            try{
                result[i] = Double.parseDouble(txt);
            }catch(Exception exc){
                throw new RuntimeException(
                        String.format("Invalid coefficient (not a valid number: '%s').", txt
                ));
            }
        }

        return result;
    }

    //---------------------------------------------------------------------

    private boolean validateAllFields(){
        // Check if values in all TextFields are valid.
        // If not, display an appropriate message in the label on the bottom of the window
        // and return false.

        // Helper variables.
        double a, b;

        // min_x, max_x
        try{
            a = Double.parseDouble(min_x_parameter.getParameter());
        }catch(NumberFormatException exc){
            info_label.displayError("'min x' is not a valid number");
            return false;
        }

        try{
            b = Double.parseDouble(max_x_parameter.getParameter());
        }catch(NumberFormatException exc){
            info_label.displayError("'max x' is not a valid number");
            return false;
        }

        if(a >= b){
            info_label.displayError("'max x' must be greater than 'min x'");
            return false;
        }

        // min_y, max_y
        try{
            a = Double.parseDouble(min_y_parameter.getParameter());
        }catch(NumberFormatException exc){
            info_label.displayError("'min y' is not a valid number");
            return false;
        }

        try{
            b = Double.parseDouble(max_y_parameter.getParameter());
        }catch(NumberFormatException exc){
            info_label.displayError("'max y' is not a valid number");
            return false;
        }

        if(a >= b){
            info_label.displayError("'max y' must be greater than 'min y'");
            return false;
        }

        // Step.
        try{
            a = Double.parseDouble(step_size_parameter.getParameter());
        }catch(NumberFormatException exc){
            info_label.displayError("step size is not a valid number");
            return false;
        }

        if(a <= 0.0){
            info_label.displayError("step size must be greater than zero");
            return false;
        }

        // Verify coefficients.
        try{
            getCoefficientArray();
        }catch(RuntimeException exc){
            info_label.displayError("Not all coefficients have been entered correctly");
            return false;
        }

        return true;
    }

    //---------------------------------------------------------------------
}
