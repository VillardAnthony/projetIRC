package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ConnectedClients;

public class ClientReceive implements Runnable{
	private Client client;//une référence vers le client qui l’a créé
	private BufferedReader in; //va permettre d'envoyer des messages au client
	
	public ClientReceive(Client c,BufferedReader i){
        //Son constructeur initialise ces deux attributs grâces aux paramètres reçus.
            this.client=c;
            this.in=i;
	}
	@Override
        public void run() {           
            boolean isActive = true ;
                while(isActive) {
                    try{
                      String message = in.readLine();
                       if (message != null) {
                         System.out.println("\nMessage reçu : " + message);
                       } 
                       else {
                        isActive = false;
                       }
                     }catch (IOException ex) {
                            Logger.getLogger(ConnectedClients.class.getName()).log(Level.SEVERE, null, ex);
                      }
                 }                  
                 try {   
                    client.disconnectedServer();
                 } catch (IOException ex) {
                      Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
                   }
        }	
}
