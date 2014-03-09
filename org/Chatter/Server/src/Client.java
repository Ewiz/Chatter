package org.Chatter.Server.src;

import java.io.PrintWriter;

class Client{
	private ClientHandlerThread clientHandlerThread;
    private String name="Unnamed";//Default value
	private PrintWriter printWriter;
 
    public Client(ClientHandlerThread clientHandlerThread){
		this.clientHandlerThread = clientHandlerThread;
		try{
			printWriter = new PrintWriter(clientHandlerThread.getClientSocket().getOutputStream(), true);    	
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
       
    public String getName(){
		return this.name;
    }
	
	public PrintWriter getPrintWriter(){
		return this.printWriter;
	}
 
    public ClientHandlerThread getClientHandlerThread(){
        return this.clientHandlerThread;
    }
}
