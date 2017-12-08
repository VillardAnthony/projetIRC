package IHM;

import java.awt.Graphics;
import java.awt.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ClientAccount extends Parent{
 
    private TextArea textToSendNdc;// Zone de texte permettant de saisir le nom de compte
    private TextArea textToSendMdp;
    private Button sendBtn;
    private Button sendCompte;
    private Text textWelcome;
    private Text textConnection;
    private Text textPseudo;
    private Text textPassword;
    private Text textCompte;
    
    final Font times30BoldItalicFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 30);
    final Font times20ItalicFont = Font.font("Times New Roman", FontPosture.ITALIC, 20);
    final Font times15ItalicFont = Font.font("Times New Roman", FontPosture.ITALIC, 15);

    public ClientAccount(){
       
        textWelcome = new Text();
        textWelcome.setText("Création de votre compte :");
        textWelcome.setLayoutX(140);
        textWelcome.setLayoutY(60);
        textWelcome.setFont(times30BoldItalicFont);
      
        textConnection = new Text();
        textConnection.setText("Connectez-vous :");
        textConnection.setLayoutX(225);
        textConnection.setLayoutY(150);
        textConnection.setFont(times20ItalicFont);
        
        textPseudo = new Text();
        textPseudo.setText("Nom de compte :");
        textPseudo.setLayoutX(150);
        textPseudo.setLayoutY(195);
        textPseudo.setFont(times15ItalicFont);

        textPassword= new Text();
        textPassword.setText("Mot de passe :");
        textPassword.setLayoutX(150);
        textPassword.setLayoutY(295);
        textPassword.setFont(times15ItalicFont);
       
        textToSendNdc = new TextArea();
        textToSendNdc.setLayoutX(150);
        textToSendNdc.setLayoutY(200);
        textToSendNdc.setPrefHeight(10);
        textToSendNdc.setPrefWidth(300);
        textToSendNdc.setEditable(true);
        
        textToSendMdp = new TextArea();
        textToSendMdp.setLayoutX(150);
        textToSendMdp.setLayoutY(300);
        textToSendMdp.setPrefHeight(10);
        textToSendMdp.setPrefWidth(300);
        textToSendMdp.setEditable(true);
    
        sendBtn= new Button();
        sendBtn.setText("Connexion");
        sendBtn.setLayoutX(250);
        sendBtn.setLayoutY(360);
        sendBtn.setPrefWidth(100);
        sendBtn.setPrefHeight(35);
        sendBtn.setVisible(true);
       /* sendBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Label label = new Label();
                if (textToSend.getText().isEmpty() ){
                } else { 
                    label.setText(textToSend.getText()+"\n");
                    label.setPrefWidth(400);

                    receivedText.getChildren().add(label);
                    textToSend.setText("");
                  }                         
            
        });*/
       
        sendCompte = new Button();
        sendCompte.setText("Créer un compte");
        sendCompte.setLayoutX(230);
        sendCompte.setLayoutY(450);
        sendCompte.setPrefWidth(140);
        sendCompte.setPrefHeight(35);
        sendCompte.setVisible(true);
       /* sendBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Label label = new Label();
                if (textToSend.getText().isEmpty() ){
                } else { 
                    label.setText(textToSend.getText()+"\n");
                    label.setPrefWidth(400);

                    receivedText.getChildren().add(label);
                    textToSend.setText("");
                  }                         
            
        });*/
        
        textCompte = new Text();
        textCompte.setText("ou");
        textCompte.setLayoutX(290);
        textCompte.setLayoutY(440);
        textCompte.setFont(times15ItalicFont);
       
        this.getChildren().add(textToSendNdc);
        this.getChildren().add(textToSendMdp);
        this.getChildren().add(textWelcome);
        this.getChildren().add(textConnection);
        this.getChildren().add(textPseudo);
        this.getChildren().add(textPassword);
        this.getChildren().add(sendBtn);
        this.getChildren().add(textCompte);
        this.getChildren().add(sendCompte);
    }  
}
