package PolynomialViewer;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class UnsignedIntegerPane extends ParameterPane {
    @FXML protected Button decrement_button,
                         increment_button;

    public UnsignedIntegerPane(){
        super();
    }

    public Button getDecrementButton(){ return decrement_button; }
    public Button getIncrementButton(){ return increment_button; }

    protected void initialize(String text){
        label = new Label(text);
        text_field = new TextField("0");
        decrement_button = new Button("-");
        increment_button = new Button("+");

        text_field.setEditable(false);  //text field may only be edited by using buttons.
        text_field.setPrefColumnCount("123".length());

        setHgrow(text_field, Priority.ALWAYS);
        getChildren().addAll(label, decrement_button, text_field, increment_button);
    }
}
