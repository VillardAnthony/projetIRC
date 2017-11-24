/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author p1614217
 */
public class Server {
    private int port;
    private List<ConnectedClients> clients;
    
     public Server() throws IOException{
        //this.port = 2005;
        this.clients = new ArrayList<ConnectedClients>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }
    public Server(int a) throws IOException{
        this.port = a;
        this.clients = new ArrayList<ConnectedClients>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }
    
    public void addClient(ConnectedClients newClient){
		for (ConnectedClients client : clients) { //envoie d’abord un message à tous les clients pour annoncer la nouvelle connexion 
			client.sendMessage("Le client "+newClient.getId()+" vient de se connecter");
		}
		this.clients.add(newClient); //ajoute le client connecté, passé en paramètre, à notre liste clients 
	}
    
    public void broadcastMessage(String message, int id) {//envoie le message m à tous les clients sauf
			//celui dont l’identifiant est id. Pour cela, on appellera la méthode sendMessage de la classe ConnectedClient 
		for (ConnectedClients client : clients) { 
			if (client.getId() != id) {
			client.sendMessage("Message de "+id+" : "+message);
			}
		 }
	}
    
    public void disconnectedClient(ConnectedClients discClient) { // appele la méthode
		//closeClient() du client qui se déconnecte, le supprimer de la liste clients, et enfin prévenir les
		//clients restants que discClient vient de se déconnecter, en leur envoyant un message 
		for (ConnectedClients client : clients) {
			client.sendMessage("Le client "+discClient.getId()+" nous a quitté");
		}
	}
    
    public int getPort(){
        return port;
    }
    public void setPort(int a) {
        this.port=a;
    }
}

