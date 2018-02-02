package IHM;

import client.*;
import java.io.IOException;
import java.util.Timer;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *  Classe principale MainIHM
 * 
 * @author ludo
 * @version 1.0
 */
public class MainIHM extends Application {

    private Scene sceneAuth;
    private Scene sceneAccount;
    private Scene scenePanel;

    private Stage stage;

    @Override
    public void start(Stage stage) throws ClassNotFoundException, IOException {
        this.stage = stage;
        

        Group authGroup = new Group(); //initialisation des groupes
        initSceneAuth(authGroup);

        Group accountGroup = new Group();
        initSceneAccount(accountGroup);

        Group panelGroup = new Group();
        initScenePanel(panelGroup);

        stage.setTitle("Messagerie");
        stage.getIcons().add(new Image("file:tchat-bulle.png")); //icone
        
        stage.setOnCloseRequest(event->
                System.exit(0)
        );
        
        stage.setScene(sceneAuth); //fenetre d'authentification 
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(MainIHM.class, args);
     //   new Thread( new ThreadIncTimer()).start();

    }

    private void initSceneAuth(Group authGroup) {
        sceneAuth = new Scene(authGroup, 600, 500); //initialisation de la scene
        stage.setResizable(false);//empecher d'agrandir ou de réduire la fenêtre ainsi que de la resize comme on le veut
        LinearGradient linearGrad; //mise en place du dégrader
        linearGrad = new LinearGradient(
                0, // start X 
                0, // start Y
                0, // end X
                1, // end Y                              
                true, // proportional        
                CycleMethod.REFLECT, // cycle colors    
                // stops
                new Stop(0.1f, Color.rgb(176, 220, 230, .6)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .0)));
        sceneAuth.setFill(linearGrad);

        ClientAuth ca = new ClientAuth(authGroup, this);//création d'un objet
        authGroup.getChildren().add(ca);//appel
    }

    private void initSceneAccount(Group accountGroup) throws ClassNotFoundException {
        sceneAccount = new Scene(accountGroup, 600, 500);
        LinearGradient linearGrad = new LinearGradient(
                0, // start X 
                0, // start Y
                0, // end X
                1, // end Y                              
                true, // proportional        
                CycleMethod.REFLECT, // cycle colors    
                // stops
                new Stop(0.1f, Color.rgb(176, 220, 230, .6)),
                new Stop(1.0f, Color.rgb(0, 0, 0, .0)));
        sceneAccount.setFill(linearGrad);

        ClientAccount cac = new ClientAccount(accountGroup, this);
        accountGroup.getChildren().add(cac);
    }

    private void initScenePanel(Group panelGroup) throws IOException {
        scenePanel = new Scene(panelGroup, 600, 500);

        ClientPanel cp = new ClientPanel(panelGroup, this);
        String address = "127.0.0.1"; //args[0]
        Integer port = new Integer(1080); //args[1]
        Client c = new Client(address, port, cp);
        cp.setClient(c);
        panelGroup.getChildren().add(cp);

        scenePanel.setFill(Color.WHITE); //force le fond blanc pour empecher le degradé
    }

    void makeSceneAccountVisible() {
        System.out.println("Changement de fenêtre, ClientAuth --> ClientAccount");
        sceneAccount.setCursor(Cursor.DEFAULT);
        stage.setScene(sceneAccount);
        stage.show();
    }

    void makeScenePanelVisible() {
        System.out.println("Changement de fenêtre, ClientAuth --> ClientPanel");
        stage.setScene(scenePanel);
        stage.show();
    }
    
     void makeSceneAuthVisible() {
        System.out.println("Changement de fenêtre, ClientAccount --> ClientAuth");
        stage.setScene(sceneAuth);        
        stage.show();
    }  
     private static void printUsage() {
        System.out.println("java client.Client <address> <port>");
        System.out.println("\t<address>: server's ip address");
        System.out.println("\t<port>: server's port");
    }
}
