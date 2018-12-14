package ServerPackage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//************************************************************

public class Server {

	private int port;
	private ThreadPool pool;
	private ServerSocket server;

	private static Logger logger = null;

	public Server(int _port){
		port = _port;
		pool = new ThreadPool(3);
	}

	public void run() throws IOException {
		server = new ServerSocket(port){
			protected void finalize() throws IOException {
				this.close();
			}
		};
		logger.info("The port " + port + " is now open.");

		while(true){
			// Accept a new client.
			Socket client = server.accept();

			// Get their username.
			Scanner sc = new Scanner(client.getInputStream());
			String username = sc.nextLine();
			username = username.replace(",", "");
			username = username.replace(" ", "_");
			logger.info("New client: '" + username + "'\n\tHost: " + client.getInetAddress().getHostAddress());

			// Check if username already exists.
			if(pool.usernameInPool(username)){
				logger.info("Rejecting client: '" + username + "'\n\tHost: " + client.getInetAddress().getHostAddress());
				(new PrintWriter(client.getOutputStream())).println("User already exists.");
				client.close();
			}
			

			// Create new User.
			User new_user = new User(client, username);
			
			// Welcome message.
			new_user.getOutputStream().println("Hello, " + username + "!");
			// Create a new thread for new_user to handle incoming messages.
			UserHandler handler = new UserHandler(this, new_user);
			pool.execute(handler);
		}
	}

	// Send incoming messages to all users.
	public void broadcastMessage(String message, User user_sender){
		String sender_name = user_sender == null ? "Server" : user_sender.getUsername();
		logger.info(String.format("'%s' sent a message: %s\n", sender_name, message));
		pool.broadcastToAll(sender_name + ": " + message);
	}

	// Send list of clients to all users.
	public void broadcastUserList(){
		pool.broadcastList();
	}

	public static void main(String[] args){
		try {
			initLogger();
			new Server(0x60d5).run();
		}catch(IOException exc){
			logger.severe("An IO error occured: " + exc.getMessage());
		}catch(Exception exc){
			logger.severe(exc.getClass().getName() + " error occured: " + exc.getMessage());
		}
	}


	private static void initLogger() throws IOException {
		logger = Logger.getLogger(Server.class.getName());
		FileHandler fh = new FileHandler(".config/log.log");
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
	}

}

//************************************************************