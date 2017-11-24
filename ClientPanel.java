/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author p1614217
 */
public class ClientPanel extends Parent {
    private TextArea textToSend;// Zone de texte permettant de saisir du texte
    private ScrollPane scrollReceivedText;//Zone de texte affichant les messages reçus
    private TextFlow receivedText;
    private Button sendBtn;//Bouton permettant d’envoyer du texte
    private Button clearBtn;// Bouton permettant d’effacer la zone de saisie
    private TextArea connected;//Zone de texte recevant les noms des utilisateurs connectés
    private Text textMembers;//Label «connectés»
    
    public ClientPanel(){
        textToSend = new TextArea();
        textToSend.setLayoutX(50);
        textToSend.setLayoutY(350);
        textToSend.setPrefHeight(100);
        textToSend.setPrefWidth(400);
        textToSend.setEditable(true);
        
        receivedText = new TextFlow();
        receivedText.setLayoutX(50);
        receivedText.setLayoutY(50);
        receivedText.setPrefHeight(280);
        receivedText.setPrefWidth(400);
        receivedText.setVisible(true);
        
        scrollReceivedText = new ScrollPane();
        scrollReceivedText.setLayoutX(50);
        scrollReceivedText.setLayoutY(50);
        scrollReceivedText.setContent(receivedText);
        scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
  
        sendBtn= new Button();
        sendBtn.setText("Envoyer");
        sendBtn.setLayoutX(470);
        sendBtn.setLayoutY(350);
        sendBtn.setPrefWidth(100);
        sendBtn.setPrefHeight(25);
        sendBtn.setVisible(true);
        sendBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Text t = new Text();
                t.setText(textToSend.getText());
                receivedText.getChildren().add(t);
                textToSend.setText("");
            }
        });
        
        clearBtn= new Button();
        clearBtn.setText("Effacer");
        clearBtn.setLayoutX(470);
        clearBtn.setLayoutY(380);
        clearBtn.setPrefHeight(25);
        clearBtn.setPrefWidth(100);
        clearBtn.setVisible(true);
        clearBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textToSend.setText("");
            }
        });
        
        connected = new TextArea();
        connected.setLayoutX(470);
        connected.setLayoutY(50);
        connected.setPrefHeight(280);
        connected.setPrefWidth(100);
        connected.setEditable(false);
        
        textMembers = new Text();
        textMembers.setText("Connectés :");
        textMembers.setLayoutX(470);
        textMembers.setLayoutY(45);
        
        this.getChildren().add(connected);
        this.getChildren().add(textMembers);
        this.getChildren().add(scrollReceivedText);
        this.getChildren().add(textToSend);
        this.getChildren().add(clearBtn);        
        this.getChildren().add(sendBtn);
    }
}
