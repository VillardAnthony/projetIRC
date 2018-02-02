package server;

import java.io.IOException;
import server.db.UsersRepository;
/**
* start a server. Reads the server's port from the command line argument
* @author Remi Watrigant
*
*/
public class MainServer {
/**
* creates a new server
* @param args
*  * Objectif du serveur : 
 *  - Accepter les nouvelles connexions de clients
 *  - Recevoir les messages de chaque client, et les diffuser à tout le monde
 *	- Notifier les clients des nouvelles conexions et des déconnexions d'autres clients
*/
    public static void main(String[] args) {
        try {
           /* if (args.length != 1 ) {
                printUsage();
            } */
            /*else*/ if(args.length == 0) {
                UsersRepository db = UsersRepository.getInstance();
		db.init();
                Integer port = new Integer(1080);
                Server server = new Server(port);
             }else {
                Integer port = new Integer(args[0]);
                Server server = new Server(port);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
          }
    }
    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
