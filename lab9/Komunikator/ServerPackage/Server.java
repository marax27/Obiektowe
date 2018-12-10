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
	private List<User> clients;
	private ServerSocket server;

	private static Logger logger = null;

	public Server(int _port){
		port = _port;
		clients = new ArrayList<User>();
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
			for(User u : clients){
				if(u.getUsername().equals(username)){
					logger.info("Rejecting client: '" + username + "'\n\tHost: " + client.getInetAddress().getHostAddress());
					client.close();
				}
			}

			// Create new User.
			User new_user = new User(client, username);

			// Add new_user to the list.
			clients.add(new_user);

			// Welcome message.
			new_user.getOutputStream().println("Hello, " + username + "!");

			// Create a new thread for new_user to handle incoming messages.
			new Thread(new UserHandler(this, new_user)).start();
		}
	}

	// Delete a user from the list.
	public void removeUser(User user){
		logger.info("Removing user: '" + user.getUsername() + "'.");
		broadcastMessage(user.getUsername() + " left the chat.", null);
		clients.remove(user);
	}

	// Send incoming messages to all users.
	public void broadcastMessage(String message, User user_sender){
		String sender_name = user_sender == null ? "Server" : user_sender.getUsername();
		logger.info(String.format("'%s' sent a message: %s\n", sender_name, message));
		for(User client : clients){
			client.getOutputStream().println(
					sender_name + ": " + message
			);
		}
	}

	// Send list of clients to all users.
	public void broadcastUserList(){
		for(User client : clients){
			client.getOutputStream().println(clients);
		}
	}

	public static void main(String[] args){
		try {
			initLogger();
			new Server(0x60d5).run();
		}catch(IOException exc){
			logger.severe("An IO error occured: " + exc.getMessage());
		}catch(Exception exc){
			logger.severe("An unknown error occured: " + exc.getMessage());
		}
	}


	private static void initLogger() throws IOException {
		logger = Logger.getLogger(Server.class.getName());
		FileHandler fh = new FileHandler("log.log");
		logger.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
	}

}

//************************************************************

class User{
	private static int nb_user = 0;
	
	private int user_id;
	private PrintStream stream_out;
	private InputStream stream_in;
	private String username;
	private Socket client;

	// Constructor.
	public User(Socket client, String name) throws IOException {
		stream_out = new PrintStream(client.getOutputStream());
		stream_in = client.getInputStream();
		this.client = client;
		username = name;
		user_id = nb_user;

		++nb_user;
	}

	public PrintStream getOutputStream() {
		return stream_out;
	}

	public InputStream getInputStream(){
		return stream_in;
	}

	public String getUsername(){
		return username;
	}

	public String toString(){
		return "User " + username;
	}
}

//************************************************************

class UserHandler implements Runnable {
	private Server server;
	private User user;

	public UserHandler(Server _server, User _user){
		server = _server;
		user = _user;
		server.broadcastUserList();
	}

	public void run(){
		String message;

		// If there's a new message, broadcast it to everyone.
		Scanner sc = new Scanner(user.getInputStream());
		while(sc.hasNextLine()){
			message = sc.nextLine();

			server.broadcastMessage(message, user);
		}

		// End of thread.
		server.removeUser(user);
		server.broadcastUserList();
		sc.close();
	}
}

//************************************************************
