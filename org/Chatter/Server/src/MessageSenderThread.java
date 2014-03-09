package org.Chatter.Server.src;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

class MessageSenderThread implements Runnable{

	private ArrayList<ChatRoom> chatRooms;
	private LinkedBlockingQueue<String> messageQueue;
	
	MessageSenderThread(ArrayList<ChatRoom> chatRooms, LinkedBlockingQueue<String> messageQueue){
		this.chatRooms=chatRooms;
		this.messageQueue=messageQueue;
	}

	public void run() {
		String message;
		while(true){
			message = messageQueue.poll();
			if(message != null){
				handleMessage(decodeMessage(message));//Decode Json and handle the message
			}
		}
	}
	 
	private decodeMessage(String message){
		//Insert code to deserialize json
	}
	
	private boolean handleMessage(){//Insert json values and safety checks
		switch (0){
			case 0://message to a chat room
					for (int i=0; i<this.chatRooms.get(1).getClients().size(); i++){
						this.chatRooms.get(1).getClients().get(i).getPrintWriter().println();
					}
				break;
			default:
				System.out.println("Message with unimplemented message type received.");
				break;
		}
	}
}