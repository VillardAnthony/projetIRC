/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author p1614217
 */
public class Connection implements Runnable{  
    private Server server;
    private ServerSocket serverSocket;
    
    public Connection(Server a) throws IOException{
        this.server=a;
        this.serverSocket = new ServerSocket(server.getPort());
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Socket sockNewClient = serverSocket.accept();
                ConnectedClients newClient = new ConnectedClients(server,sockNewClient);
                server.addClient(newClient);
                Thread threadNewClient = new Thread((Runnable) newClient);
                threadNewClient.start();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
