package org.Chatter.Server.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

class ClientHandlerThread implements Runnable{
	private Socket clientSocket = null;
    private BufferedReader in = null;
    private LinkedBlockingQueue<String> messageQueue = null;

	ClientHandlerThread(Socket clientSocket, LinkedBlockingQueue<String> messageQueue){
        this.clientSocket = clientSocket;
		this.messageQueue = messageQueue;
        
        try{
		
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
        }
		catch(IOException e){
			System.err.println("Something wrong!");
            System.err.println(e);
			e.printStackTrace();
            return;
        }
        
    }

    public void run() {
    	while(true){
    		String message=null;
			try {
				message = in.readLine();
			}catch (IOException e) {
				e.printStackTrace();
			}
    		try {
    			messageQueue.put(message);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
	}
    
    public Socket getClientSocket(){
    	return this.clientSocket;
    }
}
