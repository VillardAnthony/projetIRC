package client;

import IHM.ClientPanel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import server.ConnectedClients;
import util.Code;
import util.Message;

public class ClientReceive implements Runnable {

    private Client client;//une référence vers le client qui l’a créé
    private ObjectInputStream in; //va permettre d'envoyer des messages au client
    private ClientPanel panelC;

    public ClientReceive(Client c, Socket sock, ClientPanel cp) {
        try {
            //Son constructeur initialise ces deux attributs grâces aux paramètres reçus.
            this.client = c;
            this.panelC = cp;
            this.in = new ObjectInputStream(sock.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        boolean isActive = true;
        while (isActive) {
            try {
                System.out.println("en attente de message");
                Message mess = null;
                //ConnectedClients cli = null;
                try {
                    mess = (Message)in.readObject();
                    //cli = (ConnectedClients) in.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("message recu du serveur : " + mess);
                if (mess != null) {
                    switch (mess.getCode()) {
                        case MESSAGE:
                            client.messageRecu(mess);
                            break;
                    //panelC.setReceivedText(l);
                    //System.out.println("\nMessage reçu : " + message);                         
                        case CONNECTED_CLIENT:
                            client.addConnected(mess.getContent());
                            
                            break;
                        case DISCONNECTED_CLIENT:
                            client.removeConnected(mess.getContent());
                            break;
                        default:
                            break;
                    }
                } else {
                    isActive = false;
                }
            } catch (IOException ex) {
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
