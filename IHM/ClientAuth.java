package IHM;

import java.awt.Graphics;
import java.util.Timer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.*;
import server.*;
import server.db.UsersRepository;

/**
 *  Classe gestion de l'IHM de connexion
 * 
 * @author ludo
 * @version 1.0
 */
public class ClientAuth extends MyGroups {

    private TextField textToSendNdc;// Zone de texte permettant de saisir le nom de compte
    private PasswordField textToSendMdp;
    private Button sendBtn;
    private Button sendCompte;
    private ProgressIndicator valid;
    private Text textWelcome;
    private Text textConnection;
    private Text textPseudo;
    private Text textPassword;
    private Text textCompte;

    final Font times30BoldItalicFont = Font.font("arial", FontWeight.BOLD, FontPosture.ITALIC, 30);
    final Font times20ItalicFont = Font.font("arial", FontPosture.ITALIC, 20);
    final Font times15ItalicFont = Font.font("arial", FontPosture.ITALIC, 15);

    public ClientAuth(Group auth, MainIHM mainIHM) {
        super(mainIHM);
        textWelcome = new Text();
        textWelcome.setText("Bienvenue sur l'IRC");
        textWelcome.setLayoutX(162);
        textWelcome.setLayoutY(80);
        textWelcome.setFont(times30BoldItalicFont);

        Reflection refl = new Reflection();
        refl.setFraction(0.5f);
        textWelcome.setEffect(refl);

        textConnection = new Text();
        textConnection.setText("Connectez-vous :");
        textConnection.setLayoutX(225);
        textConnection.setLayoutY(130);
        textConnection.setFont(times20ItalicFont);

        textPseudo = new Text();
        textPseudo.setText("Nom de compte :");
        textPseudo.setLayoutX(150);
        textPseudo.setLayoutY(185);
        textPseudo.setFont(times15ItalicFont);

        textPassword = new Text();
        textPassword.setText("Mot de passe :");
        textPassword.setLayoutX(150);
        textPassword.setLayoutY(250);
        textPassword.setFont(times15ItalicFont);

        textToSendNdc = new TextField();
        textToSendNdc.setLayoutX(150);
        textToSendNdc.setLayoutY(190);
        textToSendNdc.setPrefHeight(10);
        textToSendNdc.setPrefWidth(300);
        textToSendNdc.setEditable(true);
        textToSendNdc.clear();
        textToSendNdc.textProperty().addListener(new ChangeListener<String>() { //permet de limiter le nombre de caractere du champs Ndc (à 16)
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textToSendNdc.getText().length() > 16) {
                    String s = textToSendNdc.getText().substring(0, 16);
                    textToSendNdc.setText(s);
                }
            }
        });
        textToSendNdc.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed for pseudo : " + ke.getText()); //si l'utilisateur rentre autre chose que des caracteres voulus, ecrire "vous avez rentrer un mauvais caractere"
            }
        });
        textToSendNdc.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released for pseudo: " + ke.getText());
            }
        });
        textToSendNdc.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
            }
        });

        textToSendNdc.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
            }
        });

        textToSendMdp = new PasswordField();
        textToSendMdp.setLayoutX(150);
        textToSendMdp.setLayoutY(255);
        textToSendMdp.setPrefHeight(10);
        textToSendMdp.setPrefWidth(300);
        textToSendMdp.setEditable(true);
        textToSendMdp.clear();
        textToSendMdp.textProperty().addListener(new ChangeListener<String>() { //permet de limiter le nombre de caractere du champs Mdp (à 16)
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textToSendMdp.getText().length() > 16) {
                    String s = textToSendMdp.getText().substring(0, 16);
                    textToSendMdp.setText(s);
                }
            }
        });
        textToSendMdp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed for first password: " + ke.getText());
            }
        });
        textToSendMdp.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released for first password: " + ke.getText());
            }
        });

        textToSendMdp.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
            }
        });

        textToSendMdp.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
            }
        });

        sendBtn = new Button();
        sendBtn.setText("Connexion");
        sendBtn.setLayoutX(250);
        sendBtn.setLayoutY(330);
        sendBtn.setPrefWidth(100);
        sendBtn.setPrefHeight(50);
        sendBtn.setVisible(true);
        sendBtn.setTextFill(Color.BLACK);
        sendBtn.setDefaultButton(true);

        valid = new ProgressIndicator();
        valid.setLayoutX(360);
        valid.setLayoutY(330);
        valid.setVisible(false);
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                valid.setVisible(true);
            }
        });

        sendBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                sendBtn.setCursor(Cursor.HAND);
            }
        });

        sendBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                sendBtn.setCursor(Cursor.DEFAULT);

            }
        });

        sendBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                valid.setVisible(true);
                sendBtn.setCursor(Cursor.WAIT);
                User user = new User(textToSendNdc.getText(),textToSendMdp.getText());
                UsersRepository db;
                db = UsersRepository.getInstance();
                //if(UsersRepository.getInstance().login(user.getPseudo(),user.getMdp())==true){
                    System.out.println("réussi");
                    textToSendMdp.clear();
                    valid.setVisible(false);
                    mainIHM.makeScenePanelVisible();
                //}else{
                    System.out.println("échec");
               // }
                
            }
        });

        sendCompte = new Button();
        sendCompte.setText("Créer un compte");
        sendCompte.setLayoutX(230);
        sendCompte.setLayoutY(430);
        sendCompte.setPrefWidth(140);
        sendCompte.setPrefHeight(40);
        sendCompte.setVisible(true);
        sendCompte.setTextFill(Color.MIDNIGHTBLUE);
        sendCompte.setDefaultButton(false);

        sendCompte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //si le bouton créer un compte est appuyé
            }
        });
        sendCompte.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                sendCompte.setCursor(Cursor.HAND);
            }
        });

        sendCompte.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                sendCompte.setCursor(Cursor.DEFAULT);
            }
        });

        sendCompte.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                sendCompte.setCursor(Cursor.WAIT);
                textToSendNdc.clear();
                textToSendMdp.clear();
                mainIHM.makeSceneAccountVisible(); //appel de la méthode dans la classe MainIHM

            }
        });

        textCompte = new Text();
        textCompte.setText("ou");
        textCompte.setLayoutX(290);
        textCompte.setLayoutY(420);
        textCompte.setFont(times15ItalicFont);

        Line lineup = new Line(150, 150, 450, 150);
        lineup.setStroke(Color.GREY);
        lineup.setStrokeWidth(1);

        Line linemid = new Line(150, 310, 450, 310);
        linemid.setStroke(Color.GREY);
        linemid.setStrokeWidth(1);

        Line linelow = new Line(230, 400, 370, 400);
        linelow.setStroke(Color.GREY);
        linelow.setStrokeWidth(1);

        this.getChildren().add(textToSendNdc);
        this.getChildren().add(textToSendMdp);
        this.getChildren().add(textWelcome);
        this.getChildren().add(textConnection);
        this.getChildren().add(textPseudo);
        this.getChildren().add(textPassword);
        this.getChildren().add(sendBtn);
        this.getChildren().add(textCompte);
        this.getChildren().add(sendCompte);
        this.getChildren().add(valid);
        this.getChildren().add(lineup);
        this.getChildren().add(linemid);
        this.getChildren().add(linelow);
    }

}
