package LibraryDB;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.sql.SQLException;
import java.util.LinkedList;

public class Controller {

    @FXML private Label message_label;
    @FXML private HBox button_container;
    @FXML private Button connect_button;
    @FXML private Button disconnect_button;
    @FXML private Button read_table_button;

    @FXML private HBox search_pane;
    @FXML private TextField search_textfield;
    @FXML private ComboBox search_combobox;

    @FXML private BookTableView table_view;

    @FXML private HBox adder_pane;
    @FXML private TextField adder_title_textfield;
    @FXML private TextField adder_author_textfield;
    @FXML private TextField adder_year_textfield;
    @FXML private TextField adder_isbn_textfield;

    DatabaseModel database;

    public Controller(){
        database = new DatabaseModel();
    }

    public void initialize(){
        // Button HBox.
    }

    //************************************************************

    @FXML private void connectToDatabase(ActionEvent e){
        boolean db_result = false;

        // Connect to database; no more than 3 attempts.
        int tries = 0;
        while(tries++ < 3) {
            displayMessage("Connecting to database (" + tries + "/3).");

            if(database.connect())
                break;
        }

        if(tries == 4){
            // 3 attempts failed.
            displayMessage("Failed to establish database connection.");
            displayErrorAndExit(database.getError());
        }

        connect_button.setDisable(true);
        read_table_button.setDisable(false);
        disconnect_button.setDisable(false);
        search_pane.setDisable(false);
        adder_pane.setDisable(false);

        displayMessage("Connection established succesfully.");
    }

    @FXML private void disconnectFromDatabase(ActionEvent e){
        database.close();

        connect_button.setDisable(false);
        read_table_button.setDisable(true);
        disconnect_button.setDisable(true);
        search_pane.setDisable(true);
        adder_pane.setDisable(true);

        displayMessage("Disconnected");
    }

    //************************************************************

    @FXML private void readTable(ActionEvent e){
        table_view.setBooks(database.getBooks());
    }

    @FXML private void searchBooks(ActionEvent e){
        String value = search_textfield.getText();

        String selected = (String)search_combobox.getValue();
        switch(selected){
            case "Author's surname":
                selected = "author";
                break;
            case "ISBN":
                selected = "isbn";
                break;
            default:
                return;
        }

        try {
            table_view.setBooks(database.searchBooks(value, selected));
        }catch(NullPointerException exc){
            exc.printStackTrace();
        }
    }

    @FXML private void addBook(ActionEvent e){
        // Obtain information about the book.
        Book book = null;
        try {
            book = new Book(
                    adder_title_textfield.getText(),
                    adder_author_textfield.getText(),
                    Integer.parseInt(adder_year_textfield.getText()),
                    adder_isbn_textfield.getText()
            );
        }catch(Exception exc){ displayMessage("Incorrect book data."); }

        // Update database.
        if(!database.addBook(book))
            displayMessage("Failed to update database.");
    }

    //************************************************************

    private void displayErrorAndExit(SQLException error){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection error");
        alert.setHeaderText("Failed to establish database connection");
        alert.setContentText(
                "Error details:"
                        + "\nSQLException: " + error.getMessage()
                        + "\nSQLState: " + error.getSQLState()
                        + "\nVendorError: " + error.getErrorCode()
        );

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
        Platform.exit();
    }

    //************************************************************

    private void displayMessage(String message){
        message_label.setText(message);
    }
}
