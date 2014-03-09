package org.Chatter.Server.src;

import java.util.ArrayList;

class ChatRoom{//Class to keep track of clients in different rooms
        private ArrayList<Client> clients;//Handles to client threads
        private Client owner;//The user with admin rights in the room
       
        public ChatRoom(Client owner){
                this.owner=owner;
				if(owner != null)
					this.clients.add(owner);
        }
               
		public synchronized boolean changeOwner(Client newOwner, Client currentOwner){
			if(currentOwner==null || newOwner==null)
				return false;
			else if(currentOwner == this.owner){//Check if the change is allowed
				this.owner=newOwner;
				return true;
				}
			else
				return false;
		}
		
		public synchronized void addClient(Client client){
			this.clients.add(client);
		}
		
        public synchronized Client getOwner(){
            return this.owner;
        }
 
        public synchronized ArrayList<Client> getClients(){
            return this.clients;
        }
}