package PolynomialViewer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UnsignedIntegerPaneController {
    public UnsignedIntegerPaneController(UnsignedIntegerPane pane){
        pane.getDecrementButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int value = Integer.parseInt(pane.getParameter());
                if(value > 0)
                    pane.setParameter(Integer.toString(value-1));
            }
        });

        pane.getIncrementButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int value = Integer.parseInt(pane.getParameter());
                pane.setParameter(Integer.toString(value+1));
            }
        });
    }
}
