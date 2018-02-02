package client;

import IHM.ClientPanel;
import server.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.control.Label;
import util.Code;
import util.Message;

public class Client {
	private String address; //l'adresse ip du server 
	private int port; // le port du serveur
	private Socket socket; // le socket utilisé par le serveur pour communiquer avec le client
	private ObjectInputStream in; //va permettre d'envoyer des messages au client
	private ObjectOutputStream out; //va permettre de réceptionner les messages du client
        private ClientPanel cp;
       // private Label message;
       // private boolean envoyer;

	public Client(String ip, int portt,ClientPanel cp1) throws IOException{

            this.address=ip; //initialise les attributs correspondants
            this.port=portt;      
            this.socket = new Socket (address, port);//crée le socket à partir de ces informations
            this.out = new ObjectOutputStream(socket.getOutputStream());

            this.cp=cp1;
            //this.message=new Label();
           // this.envoyer=false;
            //Thread threadClientSend = new Thread(new ClientSend(out,/*message,*/cp/*,envoyer*/)); //crée deux threads : un pour émettre des messages, de type ClientSend 
            //threadClientSend.start();
            
                    Thread threadClientReceive = new Thread(new ClientReceive(this,socket,cp)); //pour recevoir des messages, de type ClientReceive
            threadClientReceive.start();        
	}
	public void disconnectedServer() throws IOException{
        //appelle la méthode close() sur les attributs in, out, socket,
            this.in.close();
            this.out.close();
            this.socket.close();
            System.exit(0); //quitte l’application
	}    
        public void sendMessage(Code code, String m) throws IOException{
            Message mess = new Message(code, m);
            //this.message=m;
            System.out.println("envoi du message "+m);
            out.writeObject(mess);
            out.flush();
        }
       /* public void setEnvoyer(boolean a){
            this.envoyer=a;
        }*/
        public void messageRecu(Message m){
            
            cp.setReceivedText(m.getContent());
        }
        
        public void addConnected(String idClient){
            this.cp.addConnected(idClient);
        }
        
        public void removeConnected(String idClient){
            this.cp.removeConnected(idClient);
        }
}