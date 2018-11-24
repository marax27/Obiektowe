package ImageBrowser;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ImageTileController {

    public ImageTileController(ImageTile tile){
        tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Image image = new Image(tile.getFile().toURI().toString(), false);
                ImageView image_view = new ImageView(image);

                StackPane secondary_layout = new StackPane();
                secondary_layout.getChildren().add(image_view);

                Scene secondary_scene = new Scene(secondary_layout, image.getWidth(), image.getHeight());
                Stage new_window = new Stage();
                new_window.setTitle(tile.getFile().getName());
                new_window.setScene(secondary_scene);

                new_window.show();
            }
        });
    }

}
