package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable{  
    private Server server;
    private ServerSocket serverSocket;
    //constucteur de la classe connection avec en paramètre le serveur qui l'a instancié
    public Connection(Server a) throws IOException{
       // this.server= new Server();
        this.server=a; //initialise l'attribut server avec ce paramètre 
        this.serverSocket = new ServerSocket(server.getPort()); //initalise l'attribut serverSocket à l'aide du port du serveur
    }
    
    @Override
    public void run() {
        while(true){//boucle infinie attendant la connexion de nouveau clients et récupère un socket lorsqu'un client se connecte
            try {
                Socket sockNewClient = serverSocket.accept(); //Lorsqu’une connexion est acceptée (la méthode accept() est bloquante
                //l’exécution de l’application est stoppée tant qu’il n’y a pas de connexion)
                ConnectedClients newClient = new ConnectedClients(server,sockNewClient);//crée un nouvel objet ConnectedClient en lui spécifiant deux paramètres
                server.addClient(newClient);//ajoute au serveur ce nouveu client 
                Thread threadNewClient = new Thread((Runnable) newClient); //lance un thread 
                threadNewClient.start();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
