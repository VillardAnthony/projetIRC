/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author p1614217
 */
public class MainIHM extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        
        Group root = new Group();
        Scene scene = new Scene(root, 600, 500);
        //scene.setFill(Color.RED);
        
        //Text text = new Text(10, 30, "Hello World!");
        //root.getChildren().add(text);
        
        ClientPanel cp = new ClientPanel();
        root.getChildren().add(cp);
        
        //stage.setHeight(500);
        //stage.setWidth(600);
        stage.setTitle("Messagerie");
        
        stage.setScene(scene);
        stage.show();
        
    }
    
    public static void main(String[] args) {
        Application.launch(MainIHM.class, args);
    }
}
