package org.Chatter.Server.src;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class Server{
	
	private int port;
	private LinkedBlockingQueue<String> messageQueue;//queue for incoming messages
	
	public Server(int port) throws IOException{
		
		this.port=port;
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		ArrayList<ChatRoom> chatRooms = new ArrayList<ChatRoom>();//.get(0) will be equal to an client list
		chatRooms.add(new ChatRoom(null));//public room, no owner
		Client client = null;
		
		messageQueue = new LinkedBlockingQueue<String>();
		
		MessageSenderThread messageSenderThread = new MessageSenderThread(chatRooms,messageQueue);
		new Thread(messageSenderThread).start();
		
		try{//Try to set up a serversocket, close it somewhere?
			serverSocket = new ServerSocket(port);
	    }
		catch(IOException e){
            System.err.println("Serversocket failed.");
			System.err.println(e);
            System.exit(-1);
        }
		
		System.out.println("Server is running on port " + this.port + "." );
		
		while(true){
			try{//Try to accept clients
				clientSocket = serverSocket.accept();//Blocks until client is connected
				}
				
			catch (IOException e){
				System.err.println("Accept failed.");
				System.err.println(e);
				//System.exit(-1);//Exit?
			}

			ClientHandlerThread clientHandlerThread = new ClientHandlerThread(clientSocket,messageQueue);//Setup new thread for client socket
			client = new Client(clientHandlerThread);//Create new client
			chatRooms.get(0).addClient(client);//Add the client to the 'client list' and public chat room
			new Thread(clientHandlerThread).start();//Start new thread
		}
    }
     
     public static void main(String[] args) throws IOException{
    		if(args.length != 1)
    			System.err.println("Usage 'java Server portnumber'");
    		else
    			new Server(Integer.parseInt(args[0]));
    	}
}