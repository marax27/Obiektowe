package ServerPackage;

import java.util.Scanner;

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

		server.broadcastUserList();
		sc.close();
	}

	public String getUsername(){
		return user.getUsername();
	}

	public User getUser(){
		return user;
	}
}
