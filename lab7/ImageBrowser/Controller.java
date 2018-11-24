package ImageBrowser;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Vector;

public class Controller {

    @FXML private VBox main_container;
    @FXML private FlowPane content_pane;
    @FXML private ScrollPane scroll_content;
    @FXML private Menu options_menu;

    private Vector<ImageTileController> image_tile_controllers;

    private Slider tile_size_slider;

    public void initialize(){
        content_pane.setPrefWrapLength(90*3);
        image_tile_controllers = new Vector<ImageTileController>();

        scroll_content.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                updateWrapLength(null);
            }
        });
    }

    @FXML private void chooseDir(ActionEvent e){
        DirectoryChooser directory_chooser = new DirectoryChooser();
        File selected_directory = directory_chooser.showDialog(getStage());

        if(selected_directory != null){
            File[] directory_content = selected_directory.listFiles();

            content_pane.getChildren().clear();
            image_tile_controllers.clear();

            updateWrapLength(null);

            for(File f : directory_content){
                ImageTile tile = new ImageTile(f, 80);
                image_tile_controllers.add(new ImageTileController(tile));
                content_pane.getChildren().add(tile);
            }
        }

    }

    @FXML private void exitApp(ActionEvent e){
        Platform.exit();
    }

    private void updateWrapLength(ActionEvent e){
        content_pane.setPrefWrapLength(getStage().getWidth());
    }

    private Stage getStage() {
        return (Stage)main_container.getScene().getWindow();
    }
}
