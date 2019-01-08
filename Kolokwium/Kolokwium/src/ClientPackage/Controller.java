package ClientPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {

    @FXML private Label info_label;
    @FXML private Label top_label;
    @FXML private TextField username_textfield;

    @FXML Button b1;
    @FXML Button b2;
    @FXML Button b3;
    @FXML Button b4;
    @FXML Button b5;
    @FXML Button b6;
    @FXML Button b7;
    @FXML Button b8;
    @FXML Button b9;

    private Socket server;
    private BufferedReader input;
    private PrintWriter output;

    private boolean in_game = false;
    private boolean x_turn = true;

    public void initialize(){

    }

    @FXML private void connectToServer(ActionEvent e){
        String host = "localhost";
        int port = 19999;
        String username = username_textfield.getText();

        try {
            server = new Socket(host, port);

            // Construct input & output.
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);

            output.println("");

            display("Connected");
        }catch(Exception exc) {
            display("Failed to connect");
        }

    }

    //--------------------------

    @FXML private void handleClick(ActionEvent e){
        if(!in_game)
            return;

        Button clicked_button = (Button)e.getTarget();
        String label = clicked_button.getText();

        if(label.equals("")) {
            clicked_button.setText(x_turn ? "X" : "O");

            if( getWinner().isEmpty() ) {
                x_turn = !x_turn;
                updateTopLabel();
            }else{
                endGame();
            }
        }
    }

    //--------------------------

    @FXML private void playGame(ActionEvent e){
        clearBoard();
        x_turn = true;
        in_game = true;
        updateTopLabel();
    }

    private void updateTopLabel(){
        String msg = "Player " + (x_turn ? "X" : "O") + " turn";
        top_label.setText(msg);
    }

    //--------------------------

    private void clearBoard(){
        b1.setText(""); b2.setText(""); b3.setText("");
        b4.setText(""); b5.setText(""); b6.setText("");
        b7.setText(""); b8.setText(""); b9.setText("");
    }

    //--------------------------

    private void endGame(){
        in_game = false;
        top_label.setText("Player " + getWinner() + " has won!");
    }

    private String getWinner(){
        String[] d = {
            b1.getText(),
            b2.getText(),
            b3.getText(),
            b4.getText(),
            b5.getText(),
            b6.getText(),
            b7.getText(),
            b8.getText(),
            b9.getText()
        };
        // 0 1 2
        // 3 4 5
        // 6 7 8
        // Horizontal.
        if(equal3(d[0], d[1], d[2]))
            return d[0];
        if(equal3(d[3], d[4], d[5]))
            return d[3];
        if(equal3(d[6], d[7], d[8]))
            return d[6];
        // Vertical.
        if(equal3(d[0], d[3], d[6]))
            return d[0];
        if(equal3(d[1], d[4], d[7]))
            return d[1];
        if(equal3(d[2], d[5], d[8]))
            return d[2];
        // Diagonal.
        if(equal3(d[0], d[4], d[8]))
            return d[0];
        if(equal3(d[2], d[4], d[6]))
            return d[2];
        return "";
    }

    private boolean equal3(String a, String b, String c){
        return a.equals(b) && b.equals(c);
    }

    //--------------------------

    public void display(String message){
        info_label.setText(message);
    }

    private void sendMessage(String message){
        try{
            output.println(message);
        }catch(Exception exc){
            display("Error: failed to send a message");
        }
    }

}


