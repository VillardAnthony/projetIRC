package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port; //port sur lequel le serveur va écouter les nouvelles connexions
    private List<ConnectedClients> clients;//list qui va stocker la liste des des clients connectés
    //constructeur par défaut
    public Server() throws IOException{
        //this.port = 2005;
        this.clients = new ArrayList<ConnectedClients>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }
    
    //constructeur qui prend en entrée un port
    public Server(int a) throws IOException{
        this.port = a; //initialiser l'attribut port
        this.clients = new ArrayList<ConnectedClients>();//initaliser la liste clients(vide au départ)
        Thread threadConnection = new Thread(new Connection(this));//lance un thread à partir de la classe connection
        threadConnection.start();
    }
    
    public void addClient(ConnectedClients newClient){
	for (ConnectedClients client : clients) { //envoie un message à tous les clients pour annoncer la nouvelle connexion 
		client.sendMessage("Le client "+newClient.getId()+" vient de se connecter");
	}
        this.clients.add(newClient); //ajoute le client connecté, passé en paramètre, à notre liste clients 
    }
    
    public void broadcastMessage(String message, int id) {//envoie le message à tous les clients sauf
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