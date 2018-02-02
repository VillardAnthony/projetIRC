/*package client;

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
 /*public class MainClient {
/**
* construct a new client
* @param args
 */
 /*  public static void main(String[] args) {
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
}*/
package client;

import IHM.ClientPanel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class MainClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Group root = new Group();
        Scene scene = new Scene(root, 600, 500);
        //scene.setFill(Color.RED);

        //Text text = new Text(10, 30, "Hello World!");
        //root.getChildren().add(text);
        String address = "127.0.0.1"; //args[0]
        Integer port = new Integer(1080); //args[1]
        ClientPanel cp = new ClientPanel(null, null);
        Client c = new Client(address, port, cp);
        cp.setClient(c);

        root.getChildren().add(cp);

        //stage.setHeight(500);
        //stage.setWidth(600);
        stage.setTitle("Messagerie");

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent t) -> {
           /* try {
                c.disconnectedServer();
            } catch (IOException ex) {
                Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            System.exit(0);
        });
        
        
    }

    public static void main(String[] args) {

        /*if (args.length != 2) {
        printUsage();
        } else*/
        if (args.length == 0) {
            
            Application.launch(MainClient.class, args);
        }
    }

    private static void printUsage() {
        System.out.println("java client.Client <address> <port>");
        System.out.println("\t<address>: server's ip address");
        System.out.println("\t<port>: server's port");
    }
}
