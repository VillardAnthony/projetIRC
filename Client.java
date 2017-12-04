package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private String address; //l'adresse ip du server 
	private int port; // le port du serveur
	private Socket socket; // le socket utilisé par le serveur pour communiquer avec le client
	private BufferedReader in; //va permettre d'envoyer des messages au client
	private PrintWriter out; //va permettre de réceptionner les messages du client

	public Client(String ip, int portt) throws IOException{

            this.address=ip; //initialise les attributs correspondants
            this.port=portt;      
            this.socket = new Socket (address, port);//crée le socket à partir de ces informations
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //crée les attributs in et out à partir du socket
            this.out = new PrintWriter(socket.getOutputStream());
            Thread threadClientSend = new Thread(new ClientSend(out)); //crée deux threads : un pour émettre des messages, de type ClientSend 
            threadClientSend.start();
            Thread threadClientReceive = new Thread(new ClientReceive(this,in)); //pour recevoir des messages, de type ClientReceive
            threadClientReceive.start();        
	}
	public void disconnectedServer() throws IOException{
        //appelle la méthode close() sur les attributs in, out, socket,
            this.in.close();
            this.out.close();
            this.socket.close();
            System.exit(0); //quitte l’application
	}        
}