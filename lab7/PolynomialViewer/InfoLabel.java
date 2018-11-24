package PolynomialViewer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class InfoLabel extends Label {
    public InfoLabel(){
        super();
    }

    public InfoLabel(String text){
        super(text);
    }

    public void displayInformation(String text){
        setTextFill(Color.BLACK);
        setText(text);
    }

    public void displayError(String text){
        setTextFill(Color.FIREBRICK);
        setText("[Error] " + text);
    }
}
