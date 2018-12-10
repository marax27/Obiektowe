package ClientPackage;

import Shapes.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class ClientController {
    @FXML private Canvas shape_canvas;
    @FXML private TextArea chat_textarea;
    @FXML private TextArea userlist_textarea;

    @FXML private GridPane draw_hbox;
    @FXML private Button change_shape_button;
    @FXML private TextField shape_param1;
    @FXML private TextField shape_param2;
    @FXML private TextField shape_paramX;
    @FXML private TextField shape_paramY;

    @FXML private HBox message_hbox;
    @FXML private TextField message_textfield;

    @FXML private HBox connect_hbox;
    @FXML private TextField hostname_textfield;
    @FXML private TextField port_textfield;
    @FXML private TextField nickname_textfield;

    //--------------------

    private GraphicsContext gc;

    private Socket server;
    private BufferedReader input;
    private PrintWriter output;

    private Read read;

    //--------------------

    @FXML private void initialize(){
        gc = shape_canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
    }

    @FXML private void connectToServer(ActionEvent e){
        try{
            // Obtain necessary data from text fields.
            String host = hostname_textfield.getText();
            int port = Integer.parseInt(port_textfield.getText());
            String nick = nickname_textfield.getText();

            // Connect to server.
            displayMessage("Connecting to " + host + " on port " + port + "...");
            server = new Socket(host, port);
            displayMessage("Connected to " + server.getRemoteSocketAddress());

            // Construct input & output.
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);

            // Send nickname.
            output.println(nick);

            // Create and start Read thread.
            read = new Read();
            read.setDaemon(true);
            read.start();

            // Rearrange window layout.
            draw_hbox.setVisible(true);
            message_hbox.setVisible(true);
            connect_hbox.setVisible(false);

        }catch(UnknownHostException exc){
            displayMessage("Error: unknown host.");
        }catch(IOException exc){
            displayMessage("Error: I/O error occured.");
        }
    }

    @FXML private void sendTextMessage(ActionEvent e){
        try{
            String message = message_textfield.getText().trim();
            if(message.equals(""))
                return;

            sendMessage(message);
        }catch(Exception exc){}
    }

    //************************************************************

    @FXML private void changeShape(ActionEvent e){
        String current = change_shape_button.getText();

        switch(current){
            case "Circle":
                change_shape_button.setText("Rectangle");
                shape_param2.setVisible(true);
                shape_param1.setPromptText("a");
                break;
            case "Rectangle":
                change_shape_button.setText("Circle");
                shape_param2.setVisible(false);
                shape_param1.setPromptText("radius");
                break;
            default:
                break;
        }
    }

    @FXML private void sendShape(ActionEvent e){
        Shape shape = null;

        try {
            // General shape position.
            Pos2D pos = new Pos2D(
                    Integer.parseInt(shape_paramX.getText()),
                    Integer.parseInt(shape_paramY.getText())
            );

            // Build a specific shape instance.
            switch (change_shape_button.getText()) {
                case "Circle":
                    Double r = Double.parseDouble(shape_param1.getText());
                    shape = new Circle(r, pos);
                    break;
                case "Rectangle":
                    Double a = Double.parseDouble(shape_param1.getText());
                    Double b = Double.parseDouble(shape_param2.getText());
                    shape = new Rectangle(a, b, pos);
                    break;
            }
        }catch(Exception exc){
            displayMessage("Error: failed to build a shape.");
        }

        // Serialize and send the shape.
        if(shape != null){
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(shape);
                oos.close();
                sendMessage(Base64.getEncoder().encodeToString(baos.toByteArray()));

            }catch(Exception exc){
                displayMessage("Error: failed to send a shape.");
            }
        }
    }

    //************************************************************

    private void displayMessage(String message){
        chat_textarea.setText(chat_textarea.getText() + message);
    }

    private void sendMessage(String message){
        try {
            output.println(message);
        }catch(Exception exc){
            displayMessage("Error: failed to send a message.");
        }
    }

    private void drawShape(Shape shape){
        Color c = new Color(Math.random(), Math.random(), Math.random(), 1.0);
        shape.draw(gc, c);
    }

    //************************************************************

    class Read extends Thread {
        public void run(){
            String message;

            while(!Thread.currentThread().isInterrupted()) {
                String users_label_text = "";
                String chat_addition_text = "";

                try{
                    message = input.readLine();

                    if(message != null) {
                        if (message.charAt(0) == '[') {
                            // Decode array of users.
                            message = message.substring(1, message.length() - 1);
                            ArrayList<String> ListUser = new ArrayList<String>(
                                    Arrays.asList(message.split(", "))
                            );

                            for (String user : ListUser)
                                users_label_text = String.format("%s\n@%s", users_label_text, user);

                            users_label_text = users_label_text.trim();
                            chat_addition_text = null;

                        }else{
                            // Try to decode and obtain Shape. If it fails for some reason,
                            // treat as regular text message.
                            try{
                                String base64_str = message.substring(message.indexOf(": ") + 2);
                                byte[] data = Base64.getDecoder().decode(base64_str);
                                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                                Shape shape = (Shape)ois.readObject();
                                drawShape(shape);
                                ois.close();
                            }catch(Exception exc){
                                // Treat as text message.
                                chat_addition_text = String.format("%s\n%s", chat_addition_text, message);
                            }

                            users_label_text = null;
                        }
                    }

                    final String f_users_label = users_label_text;
                    final String f_chat_label = chat_addition_text;

                    Platform.runLater(
                            () -> {
                                // Update UI.
                                if(f_chat_label != null)  displayMessage(f_chat_label);
                                if(f_users_label != null)  userlist_textarea.setText(f_users_label);
                            }
                    );

                }catch(IOException exc){
                    System.err.println("Failed to parse incoming message.");
                }
            }
        }
    }

}
