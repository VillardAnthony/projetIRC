package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
    
public class ConnectedClients implements Runnable {
    //Variable d'instance
    private static int idCounter=0; //permet d'attribuer des id uniques a chaque client (compteur d'instances crées)
    private int id; //id du client
    private Server server; //reference vers notre serveur
    private Socket socket; // le socket utilisé par le serveur pour communiquer avec le client
    private BufferedReader in; //va permettre d'envoyer des messages au client
    private PrintWriter out; //va permettre de réceptionner les messages du client	
   
    //Constructeur avec en paramètre une référence vers le server et le socket
    public ConnectedClients(Server serv,Socket sock) throws IOException {    
       
        this.server = new Server(); //initialise les attributs correspondants
        this.server = serv;
        this.socket = new Socket();
        this.socket = sock;
        this.id=idCounter++; //initialise l'attribut id avec le valeur courante de l'attribut idCounter tout en l'incrémentant
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//créer les objets in et out à partir du socket
	out = new PrintWriter(socket.getOutputStream());
	System.out.println("Nouvelle connexion, id = " + id);//message indiquant qu'un nouveau client vient de se connecter
    }
    @Override
    public void run(){ //réceptionne les messages du clients
        boolean isActive = true; 
        while (isActive) { //tant que la connecxon est active
           try {
                String message;
                message = in.readLine(); //on attend un nouveau messae grâce à l'attribut in
	        if (message != null) {//si le message est non null 	
                    server.broadcastMessage(message, id); //alors on indique au serveur de diffuser ce message à tous les
		  //clients, en indiquant que ce message vient du client courant (en lui passant l’indentifiant)
	        }
	        else //Si au contraire le message est null
	        {
                    server.disconnectedClient(this); //alors on indique au serveur que le client courant vient de se déconnecter
	            isActive = false; //et on met fin à la boucle while
                }            
               } catch (IOException ex) {
                    Logger.getLogger(ConnectedClients.class.getName()).log(Level.SEVERE, null, ex);
                 }
        } 
    }             
    public void sendMessage(String m){//envoie le message au client avec out
        this.out.println(m);
        this.out.flush(); //pour s'assurer que le tampon se vide  
    }
    public void closeClient() throws IOException{//ferme les deux tampons in et out ainsi que le socket
        this.in.close();
        this.out.close();
        this.socket.close();
    }	
    public int getId(){
        return this.id;
    }	
}