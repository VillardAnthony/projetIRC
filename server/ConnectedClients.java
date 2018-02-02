package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static util.Code.*;
import util.Message;

public class ConnectedClients implements Runnable {

    //Variable d'instance
    private static int idCounter = 0; //permet d'attribuer des id uniques a chaque client (compteur d'instances crées)
    private int id; //id du client
    private Server server; //reference vers notre serveur
    private Socket socket; // le socket utilisé par le serveur pour communiquer avec le client
    private ObjectInputStream in; //va permettre d'envoyer des messages au client
    private ObjectOutputStream out; //va permettre de réceptionner les messages du client	

    //Constructeur avec en paramètre une référence vers le server et le socket
    public ConnectedClients(Server serv, Socket sock) throws IOException {

        this.server = new Server(); //initialise les attributs correspondants
        this.server = serv;
        this.socket = new Socket();
        this.socket = sock;
        this.id = idCounter++; //initialise l'attribut id avec le valeur courante de l'attribut idCounter tout en l'incrémentant
        in = new ObjectInputStream(socket.getInputStream());//créer les objets in et out à partir du socket
        out = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Nouvelle connexion, id = " + id);//message indiquant qu'un nouveau client vient de se connecter
    }

    @Override
    public void run() { //réceptionne les messages du clients
        //on va d'abord lui envoyer la liste de tous les clients connectés
        for (ConnectedClients client : this.server.getListClients()) {
            if (client.getId() != this.getId()) {
                try {
                    sendMessage(new Message(CONNECTED_CLIENT, String.valueOf(client.getId())));
                } catch (IOException ex) {
                    Logger.getLogger(ConnectedClients.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
        
        boolean isActive = true;
        while (isActive) { //tant que la connecxon est active
            try {
                System.out.println("en attente de message de " + this.getId());
                Message mess = null;
                try {
                    mess = (Message) in.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ConnectedClients.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("message recu de " + this.getId()+ " : " + mess);

                if (mess != null) {//si le message est non null 	
                    server.broadcastMessage(mess, id); //alors on indique au serveur de diffuser ce message à tous les
                    //clients, en indiquant que ce message vient du client courant (en lui passant l’indentifiant)
                } else {
                    server.disconnectedClient(this);
                    isActive = false;
                }

            } catch (IOException ex) {
                try {
                    server.disconnectedClient(this); //alors on indique au serveur que le client courant vient de se déconnecter
                } catch (IOException ex1) {
                    Logger.getLogger(ConnectedClients.class.getName()).log(Level.SEVERE, null, ex1);
                }
                isActive = false;
            }
        }
    }

    public void sendMessage(Message m) throws IOException {//envoie le message au client avec out
        this.out.writeObject(m);
        //this.out.writeObject(this);
        this.out.flush(); //pour s'assurer que le tampon se vide  
    }

    public void closeClient() throws IOException {//ferme les deux tampons in et out ainsi que le socket
        this.in.close();
        this.out.close();
        this.socket.close();
    }

    public int getId() {
        return this.id;
    }
}
