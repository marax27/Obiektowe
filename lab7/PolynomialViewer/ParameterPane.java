package PolynomialViewer;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ParameterPane extends HBox {
    @FXML protected Label label;
    @FXML protected TextField text_field;

    public ParameterPane(){
        super();
        initialize("");
        setSpacing(10.0);
    }

    // ----------------------------------------
    public String getParameter(){
        return parameterTextProperty().get();
    }
    public void setParameter(String text){
        parameterTextProperty().set(text);
    }

    public StringProperty parameterTextProperty(){
        return text_field.textProperty();
    }
    // ----------------------------------------
    public String getLabel(){
        return labelTextProperty().get();
    }
    public void setLabel(String text){
        labelTextProperty().set(text);
    }
    public StringProperty labelTextProperty(){
        return label.textProperty();
    }
    // ----------------------------------------

    public TextField getTextField(){
        return text_field;
    }

    protected void initialize(String text){
        label = new Label(text);
        text_field = new TextField("0");

        text_field.setPrefColumnCount("123.4567".length());

        setHgrow(label, Priority.ALWAYS);
        getChildren().addAll(label, text_field);
    }
}
