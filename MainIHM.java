package IHM;

import java.awt.Rectangle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainIHM extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        Group root = new Group();
        
        Scene scene = new Scene(root, 600, 500);       
        stage.setResizable(false);//empecher d'agrandir ou de réduire la fenêtre ainsi que de la resize comme on le veut
        
        //scene.setFill(Color.RED);
        
        //Text text = new Text(10, 30, "Hello World!");
        //root.getChildren().add(text);
        
       // ClientAuth ca = new ClientAuth();
       //root.getChildren().add(ca);
        
        //stage.setTitle("Sign in");
       // scene.setFill(Color.LIGHTBLUE);
       // stage.setScene(scene);
       // stage.show();    
        
        //si le bouton créer un compte est appuyé
        ClientAccount cac = new ClientAccount();
        root.getChildren().add(cac);
        
        stage.setTitle("Sign up");
        scene.setFill(Color.LIGHTBLUE);
        stage.setScene(scene);
        stage.show();  

        //if connecté
        //ClientPanel cp = new ClientPanel();
        //root.getChildren().add(cp);
        
        
        
        //stage.setHeight(500);
        //stage.setWidth(600);
       // stage.setTitle("Messagerie");
        
        //stage.setScene(scene);
        //stage.show();    
    } 
    public static void main(String[] args) {
        Application.launch(MainIHM.class, args);
    }
}
