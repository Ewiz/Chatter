

import java.io.*;
import java.net.*;

//public class Listener extends Thread

//public class Sender extends Thread





public class Client{

	private Socket socket		   = null;
	private DataInputStream  console   = null;
	private DataOutputStream streamOut = null;


	public Client(String serverName, int serverPort){
	System.out.println("Establishing connection to the server. Please wait...");
	try{
			socket= new Socket(serverName, serverPort);
			console= new DataInputStream(socket.getInputStream());
			streamOut= new DataOutputStream(socket.getOutputStream());
			System.out.println("Connected to server " + serverName + ":" + serverPort);
			}catch(Exception e){		
			}
}



public static void main(String[] args){

System.out.println("(^.^)");

	if (args.length != 2) {
		System.err.println(
				"Usage: java EchoClient <host name> <port number>");
		System.exit(1);
	}

Client chatClient = new Client(args[0], Integer.parseInt(args[1]));
}
}
