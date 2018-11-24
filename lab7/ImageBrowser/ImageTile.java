package ImageBrowser;

import javafx.fxml.FXML;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ImageTile extends ImageView {
    @FXML Tooltip tooltip;

    private File file;

    public ImageTile(File img_file, double width){
        super();
        this.file = img_file;
        initialize(width);
    }


    public File getFile(){ return file; }

    private void loadImage(double width){
        Image image = new Image(file.toURI().toString(), width, 0, true, true, true);
        setImage(image);
    }

    private void initialize(double width){
        loadImage(width);
        loadTooltip();
    }

    private void loadTooltip(){
        tooltip = new Tooltip(file.getName());
        Tooltip.install(this, tooltip);
    }

}
