package org.Chatter.Server.src;

import java.net.*;
import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;


public class Server{
	
	private int port;
	private Hashtable<String, ClientHandlerThread> clientList;
	private LinkedBlockingQueue<String> messageQueue;
	
	public Server(int port) throws IOException{
		
		this.port=port;
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		clientList = new Hashtable<String, ClientHandlerThread>();//Collections.synchronizedMap(HashMap)
		messageQueue = new LinkedBlockingQueue<String>();
		
		MessageSenderThread messageSenderThread = new MessageSenderThread(clientList,messageQueue);
		new Thread(messageSenderThread).start();
		
		try{//Try to set up a serversocket
			serverSocket = new ServerSocket(port);
	    }
		catch(IOException e){
            System.err.println("Serversocket failed.");
			System.err.println(e);
            System.exit(-1);
        }
		
		while(true){
			try{//Try to accept clients
				clientSocket = serverSocket.accept();
				}
				
			catch (IOException e){
				System.err.println("Accept failed.");
				System.err.println(e);
				//System.exit(-1);//Exit?
			}

			ClientHandlerThread clientHandlerThread = new ClientHandlerThread(clientSocket,messageQueue);
			clientList.put((clientSocket.getInetAddress() + ":" + clientSocket.getPort()),clientHandlerThread);
			new Thread(clientHandlerThread).start();
		}
    }
     
     public static void main(String[] args) throws IOException{
    		if(args.length != 1)
    			System.err.println("Usage 'java Server portnumber'");
    		else
    			new Server(Integer.parseInt(args[0]));
    	}  


private class MessageSenderThread implements Runnable{

	private Hashtable<String, ClientHandlerThread> clientList;
	private LinkedBlockingQueue<String> messageQueue;
	
	MessageSenderThread(Hashtable<String, ClientHandlerThread> clientList,LinkedBlockingQueue<String> messageQueue){
	
		this.clientList=clientList;
		this.messageQueue=messageQueue;
	}

	public void run() {
	
		while(true){
			String message = messageQueue.poll();
			if(message != null){
				
				Collection<ClientHandlerThread> clients = clientList.values();
	
				PrintWriter out = null;
				
		        for(ClientHandlerThread client: clients){
		        	try{
		        	out = new PrintWriter(client.getClientSocket().getOutputStream(), true);
		        	}
		        	catch(Exception e){
		        		e.printStackTrace();
		        	}
		        	out.println(message);
		        }

				//DecodeMessage(message);
				//switch(content[0])
				//case message:
				//if PrintwriterExists==true?
				//do something
				//else
				//out = new PrintWriter(clientSocket.getOutputStream(), true);
			}
		}
	}
}
}