/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.io.IOException;
import java.net.UnknownHostException;
/**
* starts a client. Reads the address and port from the command line
argument
* @author Remi Watrigant
*
*  Objectif du client : 
 *   - Inviter l'utilisateur à saisir un message qu'il soit ensuite envoyer au serveur
 *   - Réceptionner les messages du serveur : messages d'autres clients, 
 *                                            connexions ou déconnexions d'autres clients
 */

public class MainClient {
/**
* construct a new client
* @param args
*/
public static void main(String[] args) {
try {
if (args.length != 2) {
printUsage();
} else {
String address = args[0];
Integer port = new Integer(args[1]);
Client c = new Client(address, port);
}
} catch (UnknownHostException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
}
}
private static void printUsage() {
System.out.println("java client.Client <address> <port>");
System.out.println("\t<address>: server's ip address");
System.out.println("\t<port>: server's port");
}
}
