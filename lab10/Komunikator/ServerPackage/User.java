package ServerPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class User{
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