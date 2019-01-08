package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {
    DatabaseModel database;

    private int port;
    private ServerSocket server;

    public Server(int _port){
        port = _port;
        database = new DatabaseModel();
        database.connect();
    }

    public void run() throws IOException {
        server = new ServerSocket(port){
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("The port " + port + " is open.");

        while(true){
            // Accept a new client.
            Socket client = server.accept();
            
            // Obtain username of the winner.
            Scanner sc = new Scanner(client.getInputStream());
            String winner_name = sc.nextLine();
            System.out.println("New winner: " + winner_name);

            // Save to database.
            boolean res = database.addRecord(new Winner(winner_name));
            System.out.println("Success: " + res);

            // Confirm success and close connection.
            (new PrintStream(client.getOutputStream())).println("OK");
            sc.close();
            client.close();
        }
    }

    public static void main(String[] args){
        try{
            new Server(19999).run();
        }catch(Exception e){
            System.err.println("Exception caught: " + e.getMessage());
        }
    }
}
