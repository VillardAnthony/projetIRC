package IHM;

import client.*;
import server.*;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.util.*;
import java.text.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import static javafx.scene.Cursor.cursor;
import javafx.stage.WindowEvent;
import util.Code;

public class ClientPanel extends MyGroups {

    private TextArea textToSend;// Zone de texte permettant de saisir du texte
    private ScrollPane scrollReceivedText;//Zone de texte affichant les messages reçus
    private TextFlow receivedText;
    private Button clearBtnChat;//Bouton permettant de clear la zone de chat
    private Button sendBtn;//Bouton permettant d’envoyer du texte
    private Button clearBtn;// Bouton permettant d’effacer la zone de saisie
    private Text textMembers;//Label «connectés»
    private TextFlow connected;
    private ScrollPane scrollConnected;
    private Text dateBtn; //bouton qui affiche l'heure   
    private Label labelTime = new Label();
    private Button DeconnexionBtn; //bouton déconnexion
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private DateFormat ft2 = new SimpleDateFormat("HH:mm");
    private Client client;
    
   /**
    *  Classe gestion de l'IHM du chat
    * 
    * @author ludo
    * @version 1.0
    */
    public ClientPanel(Group pan, MainIHM mainIHM) {
        super(mainIHM);
        //message à envoyer
        textToSend = new TextArea();
        textToSend.setLayoutX(50);
        textToSend.setLayoutY(350);
        textToSend.setPrefHeight(100);
        textToSend.setPrefWidth(400);
        textToSend.setEditable(true);

        //message recu
        receivedText = new TextFlow();
        receivedText.setLayoutX(50);
        receivedText.setLayoutY(50);
        receivedText.setPrefHeight(280);
        receivedText.setPrefWidth(400);
        receivedText.setVisible(true);

        //Pour faire défiler le textFlow, textArea ne gère pas les ScrollPane
        scrollReceivedText = new ScrollPane();
        scrollReceivedText.setLayoutX(50);
        scrollReceivedText.setLayoutY(50);
        scrollReceivedText.setContent(receivedText);
        scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());

        //message recu
        connected = new TextFlow();
        connected.setLayoutX(470);
        connected.setLayoutY(50);
        connected.setPrefHeight(280);
        connected.setPrefWidth(100);
        connected.setVisible(true);

        //Pour faire défiler le textArea
        scrollConnected = new ScrollPane();
        scrollConnected.setLayoutX(470);
        scrollConnected.setLayoutY(50);
        scrollConnected.setContent(connected);
        scrollConnected.vvalueProperty().bind(connected.heightProperty());    

        /*
        DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM);
        SimpleDateFormat ft
                = new SimpleDateFormat("E dd/MM/yyyy ',' kk:mm:ss");
         */
        Date currentDate = new Date();
        dateBtn = new Text();
        dateBtn.setLayoutX(10);
        dateBtn.setLayoutY(490);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        Timer timer;
        timer = new Timer();
        TimerTask chrono = new TimerTask() {
            Date currentDatePlusOne = new Date();

            //Le timer
            @Override
            public void run() {
                currentDatePlusOne = c.getTime();
                c.add(Calendar.SECOND, 1);
                //System.out.println(dateFormat.format(currentDatePlusOne));
                dateBtn.setText(dateFormat.format(currentDatePlusOne));
                labelTime.setText(ft2.format(currentDatePlusOne));
            }
        };
        timer.schedule(chrono, 1000, 1000);

        sendBtn = new Button();
        sendBtn.setText("Envoyer");
        sendBtn.setLayoutX(470);
        sendBtn.setLayoutY(350);
        sendBtn.setPrefWidth(100);
        sendBtn.setPrefHeight(25);
        sendBtn.setVisible(true);
        sendBtn.setDefaultButton(true);
        sendBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                sendBtn.setCursor(Cursor.HAND);
            }
        });

        sendBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                //setCursor(Cursor.HAND);     

            }
        });

        sendBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

                System.out.println("Mouse pressed");
                sendBtn.setCursor(Cursor.WAIT);
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 300000000L) {
                            sendBtn.setCursor(Cursor.HAND);
                            this.stop();
                        }

                    }
                }.start();

                Label msg = new Label();
                Label labelTime = new Label();
                if (textToSend.getText().isEmpty()) {
                } else {

                    labelTime.setText("[" + ft2.format(currentDate) + "] : " + textToSend.getText());
                    msg.setText("\nVotre Message >> " + textToSend.getText());
                    msg.setPrefWidth(400);

                    System.out.println("on écrit dans " + this);

                    try {
                        client.sendMessage(Code.MESSAGE, textToSend.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //receivedText.getChildren().add(labelTime);
                    receivedText.getChildren().add(msg);

                    textToSend.setText("");
                    System.out.flush();
                }
            }
        });

        clearBtn = new Button();
        clearBtn.setText("Effacer");
        clearBtn.setLayoutX(470);
        clearBtn.setLayoutY(380);
        clearBtn.setPrefHeight(25);
        clearBtn.setPrefWidth(100);
        clearBtn.setVisible(true);
        clearBtn.setCancelButton(true);
        clearBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                clearBtn.setCursor(Cursor.HAND);
            }
        });

        clearBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                clearBtn.setCursor(Cursor.DEFAULT);
            }
        });
        clearBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                textToSend.setText("");
                clearBtn.setCursor(Cursor.WAIT);
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 200000000L) {
                            clearBtn.setCursor(Cursor.HAND);
                            this.stop();
                        }
                    }
                }.start();
            }
        });

        clearBtnChat = new Button();
        clearBtnChat.setText("X");
        clearBtnChat.setLayoutX(20);
        clearBtnChat.setLayoutY(50);
        clearBtnChat.setPrefHeight(25);
        clearBtnChat.setPrefWidth(25);
        clearBtnChat.setVisible(true);
        clearBtnChat.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                clearBtnChat.setCursor(Cursor.HAND);
            }
        });
        clearBtnChat.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                clearBtnChat.setCursor(Cursor.DEFAULT);
            }
        });
        clearBtnChat.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                receivedText.getChildren().clear();
                clearBtnChat.setCursor(Cursor.WAIT);
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 200000000L) {
                            clearBtnChat.setCursor(Cursor.HAND);
                            this.stop();
                        }
                    }
                }.start();
            }
        });

        DeconnexionBtn = new Button();
        DeconnexionBtn.setText("Se déconnecter");
        DeconnexionBtn.setLayoutX(470);
        DeconnexionBtn.setLayoutY(460);
        DeconnexionBtn.setPrefHeight(30);
        DeconnexionBtn.setPrefWidth(100);
        DeconnexionBtn.setVisible(true);
        DeconnexionBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                DeconnexionBtn.setCursor(Cursor.HAND);
            }
        });
        DeconnexionBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                DeconnexionBtn.setCursor(Cursor.DEFAULT);
            }
        });
        DeconnexionBtn.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                System.out.println("Mouse pressed");
                String custom_cursor = "file:cursor_WAIT.png";
                DeconnexionBtn.setCursor(Cursor.cursor(custom_cursor));
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 270000000L) { //ajout pour que le curseur sablier s'enleve avant de switch d'interface
                            textToSend.setText("");
                            receivedText.getChildren().clear();
                            DeconnexionBtn.setCursor(Cursor.DEFAULT);
                        }
                        if (now - this.timeStart > 300000000L) {
                            this.stop();
                            mainIHM.makeSceneAuthVisible();
                        }
                    }
                }.start();
            }
        });

        textMembers = new Text();
        textMembers.setText("Connectés :");
        textMembers.setLayoutX(470);
        textMembers.setLayoutY(45);

        this.getChildren().add(textMembers);
        this.getChildren().add(scrollReceivedText);
        this.getChildren().add(scrollConnected);
        this.getChildren().add(textToSend);
        this.getChildren().add(clearBtn);
        this.getChildren().add(sendBtn);
        this.getChildren().add(clearBtnChat);
        this.getChildren().add(DeconnexionBtn);
        this.getChildren().add(dateBtn);
    }

    public void setClient(Client c1) {

        this.client = c1;
    }

    public void setReceivedText(String m) {
        System.out.println("dans receivedTest : on écrit dans " + this);

        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Label l = new Label();
                l.setText("\nMessage reçu : " + m);
                l.setPrefWidth(400);
                receivedText.getChildren().add(l);
            }
        });

    }

    public void setConnected(Server s) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

            }
        });
    }

    public void addConnected(String idClient) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                System.out.println("hello world");
                Label l = new Label();
                l.setText("Client " + idClient);
                l.setPrefWidth(400);

                connected.getChildren().add(l);

            }
        });
    }

    public void removeConnected(String idClient) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {  //je cherche le label qui se termine par l'id recherché
                for (int i = 0 ; i < connected.getChildren().size() ; i++) {
                    Label l = (Label)connected.getChildren().get(i);
                    if (l.getText().endsWith(idClient)) {
                        connected.getChildren().remove(i);
                        return;
                    }
                }
            }
        });
    }
}
