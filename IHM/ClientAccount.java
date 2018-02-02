package IHM;

import javafx.scene.input.KeyEvent;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.function.UnaryOperator;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.awt.FlowLayout;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import static javafx.scene.text.Font.font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import static javafx.scene.paint.Color.*;
import model.User;
import server.db.UsersRepository;

/**
 * Classe gestion de l'IHM de la création d'un compte
 * 
 * @author ludo
 * @version 1.0
 */
public class ClientAccount extends MyGroups { //les 3 IHM héritent de MyGroups

    private TextField idTextField;
    private TextField textToSendNdc;// Zone de texte permettant de saisir le nom de compte
    private Text champsNoRespect;
    private Text champsNoRespectPass;
    private PasswordField textToSendMdp;
    private PasswordField textToSendMdpRepeat;
    private TextArea textToSendMdpRepeatAnnot;
    private TextArea textToSendMdpAnnot;
    private TextArea textToSendNdcAnnot;
    private Button sendBtn;
    private Button sendCompte;
    private Text textWelcome;
    private Text textPseudo;
    private Text textPassword;
    private Text textRepeatPassword;
    private Text textCompte;
    private ProgressIndicator valid;
    
    //chargement du bean pour se connecter à la base
   // private UserAccountBean userBean;

    final Font times30BoldItalicFont = Font.font("arial", FontWeight.BOLD, FontPosture.ITALIC, 30);
    final Font times20ItalicFont = Font.font("arial", FontPosture.ITALIC, 20);
    final Font times15ItalicFont = Font.font("arial", FontPosture.ITALIC, 15);
    

    public ClientAccount(Group acc, MainIHM mainIHM) throws ClassNotFoundException { //mainIHM est un attribut commun au 3 groupes 
        super(mainIHM);
        // this.userBean = new UserAccountBean();
        textWelcome = new Text();
        textWelcome.setText("Création de votre compte :");
        textWelcome.setLayoutX(120);
        textWelcome.setLayoutY(70);
        textWelcome.setFont(times30BoldItalicFont);

        Reflection refl = new Reflection();
        refl.setFraction(0.5f);
        textWelcome.setEffect(refl);

        textPseudo = new Text();
        textPseudo.setText("Saisir un pseudo :");
        textPseudo.setLayoutX(150);
        textPseudo.setLayoutY(145);
        textPseudo.setFont(times15ItalicFont);

        textPassword = new Text();
        textPassword.setText("Saisir un mot de passe :");
        textPassword.setLayoutX(150);
        textPassword.setLayoutY(230);
        textPassword.setFont(times15ItalicFont);

        textRepeatPassword = new Text();
        textRepeatPassword.setText("Retapez le mot de passe :");
        textRepeatPassword.setLayoutX(150);
        textRepeatPassword.setLayoutY(280);
        textRepeatPassword.setFont(times15ItalicFont);

        champsNoRespect = new Text();
        champsNoRespect.setText("Un des champs ne respecte pas les conditions");
        champsNoRespect.setLayoutX(145);
        champsNoRespect.setLayoutY(345);
        champsNoRespect.setFill(RED);
        champsNoRespect.setFont(times15ItalicFont);
        champsNoRespect.setVisible(false);

        champsNoRespectPass = new Text();
        champsNoRespectPass.setText("Les deux mots de passe ne correspondent pas");
        champsNoRespectPass.setLayoutX(145);
        champsNoRespectPass.setLayoutY(345);
        champsNoRespectPass.setFill(RED);
        champsNoRespectPass.setFont(times15ItalicFont);
        champsNoRespectPass.setVisible(false);

        textToSendNdc = new TextField();
        textToSendNdc.setLayoutX(150);
        textToSendNdc.setLayoutY(150);
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
        //textToSendNdc.getText().length(max);
        textToSendNdcAnnot = new TextArea("Votre pseudo doit\n"
                + "au minimum contenir\n" + "4 caractères et ne\n" + "pas inclure de carac-\n"
                + "tères spéciaux :\n"
                + "( , ; : ! ?  . / % { * & \" ..."); //interdit , ; : ! ? . / § ù % µ * £ $ ¤ + = } ) ° ] @ ^ \ ` | ( [ ' { " # ~ & < > et plein d'autres
        //pour interdire tous les caractères spéciaux, on va juste autoriser a-z A-Z 0-9
        textToSendNdcAnnot.setLayoutX(5);
        textToSendNdcAnnot.setLayoutY(150);
        textToSendNdcAnnot.setPrefHeight(110);
        textToSendNdcAnnot.setPrefWidth(140);
        textToSendNdcAnnot.setVisible(false);
        textToSendNdcAnnot.setFont(font("default", FontPosture.ITALIC, 12));
        //textToSendNdc.selectionProperty().addListener(observable -> System.out.println("Sélection modifiée dans le champ d'édition !"));
        //textToSendNdc.copy();
        textToSendNdc.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed for pseudo : " + ke.getText());
                champsNoRespect.setVisible(false);
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
                textToSendNdcAnnot.setVisible(true);
            }
        });

        textToSendNdc.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                textToSendNdcAnnot.setVisible(false);
            }
        });

        //textToSendNdc.setDocument(new TStringField(10));
        TextFormatter<?> integerOnlyFormatter = null;
        textToSendNdc.setTextFormatter(integerOnlyFormatter);
        /*le filtrage des modifications apportées au contenu du contrôle de saisie (
           ex. : éviter la saisie de certains caractères); convertir le texte saisi dans le contrôle par l'utilisateur en une valeur de type V 
           qui sera contenue dans la propriété value du formateur. Dans le cas où le programmeur modifie la propriété value du formateur, 
           celui-ci convertira alors la valeur en texte à afficher dans le contrôle de saisie.*/
 /* final UnaryOperator<TextFormatter.Change> integerOnlyFilter = change -> { 
    final String text = change.getText(); 
    return (text.isEmpty() || text .matches("[0-9]")) ? change : null; 
}; 
final TextFormatter<Integer> integerOnlyFormatter = new TextFormatter(integerOnlyFilter);*/
        textToSendMdp = new PasswordField();
        textToSendMdp.setLayoutX(150);
        textToSendMdp.setLayoutY(235);
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
        textToSendMdpAnnot = new TextArea("Votre mot de passe\n"
                + "doit au minimum\n" + "contenir 6 caractères\n" + "et ne pas inclure de\n"
                + "caractères spéciaux :\n"
                + "( , ; : ! ? . / % { * & \" ..."); //interdit , ; : ! ? . / § ù % µ * £ $ ¤ + = } ) ° ] @ ^ \ ` | ( [ ' { " # ~ & < >
        textToSendMdpAnnot.setLayoutX(5);
        textToSendMdpAnnot.setLayoutY(220);
        textToSendMdpAnnot.setPrefHeight(110);
        textToSendMdpAnnot.setPrefWidth(140);
        textToSendMdpAnnot.setVisible(false);
        textToSendMdpAnnot.setFont(font("default", FontPosture.ITALIC, 12));
        textToSendMdp.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed for first password: " + ke.getText());
                champsNoRespect.setVisible(false);
                champsNoRespectPass.setVisible(false);
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
                textToSendMdpAnnot.setVisible(true);
            }
        });

        textToSendMdp.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                textToSendMdpAnnot.setVisible(false);
            }
        });

        textToSendMdpRepeat = new PasswordField();
        textToSendMdpRepeat.setLayoutX(150);
        textToSendMdpRepeat.setLayoutY(285);
        textToSendMdpRepeat.setPrefHeight(10);
        textToSendMdpRepeat.setPrefWidth(300);
        textToSendMdpRepeat.setEditable(true);
        textToSendMdpRepeat.clear();
        textToSendMdpRepeat.textProperty().addListener(new ChangeListener<String>() { //permet de limiter le nombre de caractere du champs Mdp (à 16)
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textToSendMdpRepeat.getText().length() > 16) {
                    String s = textToSendMdpRepeat.getText().substring(0, 16);
                    textToSendMdpRepeat.setText(s);
                }
            }
        });
        textToSendMdpRepeatAnnot = new TextArea(" Retapez le même \n"
                + " mot de passe");
        textToSendMdpRepeatAnnot.setLayoutX(25);
        textToSendMdpRepeatAnnot.setLayoutY(285);
        textToSendMdpRepeatAnnot.setPrefHeight(50);
        textToSendMdpRepeatAnnot.setPrefWidth(120);
        textToSendMdpRepeatAnnot.setVisible(false);
        textToSendMdpRepeat.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed for second password: " + ke.getText());
                champsNoRespect.setVisible(false);
                champsNoRespectPass.setVisible(false);
            }
        });
        textToSendMdpRepeat.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Released for second password: " + ke.getText());
            }
        });

        textToSendMdpRepeat.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse entered");
                textToSendMdpRepeatAnnot.setVisible(true);
            }
        });

        textToSendMdpRepeat.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("Mouse exited");
                textToSendMdpRepeatAnnot.setVisible(false);
            }
        });

        sendBtn = new Button();
        sendBtn.setText("Création du compte");
        sendBtn.setLayoutX(150);
        sendBtn.setLayoutY(380);
        sendBtn.setPrefWidth(140);
        sendBtn.setPrefHeight(50);
        sendBtn.setVisible(true);
        sendBtn.setTextFill(Color.BLACK);
        sendBtn.setDefaultButton(true);

        valid = new ProgressIndicator();
        valid.setLayoutX(95);
        valid.setLayoutY(380);
        valid.setVisible(false);
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
                sendBtn.setCursor(Cursor.WAIT);
                textToSendNdc.getText().trim().length();
                textToSendMdp.getText().trim().length();
                textToSendMdpRepeat.getText().trim().length();
                System.out.println("Mouse pressed");
                valid.setVisible(true);
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 400000000L) {
                            sendBtn.setCursor(Cursor.DEFAULT);
                            valid.setVisible(false);
                            this.stop();

                        }
                    }
                }.start();           
                
                Pattern p = Pattern.compile("^(\\p{Alnum})+$"); //pas de caractère speciaux
                Matcher m = p.matcher(textToSendNdc.getText());
                Matcher m2 = p.matcher(textToSendMdp.getText());
                Matcher m3 = p.matcher(textToSendMdpRepeat.getText());
                
                if (textToSendNdc.getText().equals("") || textToSendMdp.getText().equals("") || textToSendMdpRepeat.getText().equals("") || textToSendNdc.getText().length() < 4
                        || textToSendMdp.getText().length() < 6 || textToSendMdpRepeat.getText().length() < 6 || !(textToSendMdp.getText().equals(textToSendMdpRepeat.getText()))
                        || !(m.find())|| !(m2.find()) || !(m3.find())) {

                    System.out.println("inscription failed");
                    champsNoRespect.setVisible(true);

                    if (!(textToSendMdp.getText().equals(textToSendMdpRepeat.getText()))) {
                        champsNoRespect.setVisible(false);
                        champsNoRespectPass.setVisible(true);
                        System.out.println("inscription failed ");
                    }
                } else {
                    new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                        private long timeStart = System.nanoTime();

                        @Override
                        public void handle(long now) {
                            System.out.println(now - this.timeStart);
                            if (now - this.timeStart > 370000000L) { //ajout pour que le curseur sablier s'enleve avant de switch d'interface
                                sendBtn.setCursor(Cursor.DEFAULT);
                            }
                            if (now - this.timeStart > 400000000L) {
                                valid.setVisible(false);
                                this.stop();
                                mainIHM.makeSceneAuthVisible();
                                textToSendNdc.clear();
                                textToSendMdp.clear();
                                textToSendMdpRepeat.clear();
                            }
                        }
                    }.start();
                }

            }
        });
        valid.setVisible(false);

        sendCompte = new Button();
        sendCompte.setText("Menu connexion");
        sendCompte.setLayoutX(310);
        sendCompte.setLayoutY(380);
        sendCompte.setPrefWidth(140);
        sendCompte.setPrefHeight(50);
        sendCompte.setVisible(true);
        sendCompte.setTextFill(Color.MIDNIGHTBLUE);
        sendCompte.setDefaultButton(false);

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
                String custom_cursor = "file:cursor_WAIT.png";
                sendCompte.setCursor(Cursor.cursor(custom_cursor));
                new AnimationTimer() { //animation timer pour changer la valeur du curseur au bout d'un certain temps

                    private long timeStart = System.nanoTime();

                    @Override
                    public void handle(long now) {
                        System.out.println(now - this.timeStart);
                        if (now - this.timeStart > 270000000L) { //ajout pour que le curseur sablier s'enleve avant de switch d'interface
                            sendCompte.setCursor(Cursor.DEFAULT);
                        }
                        if (now - this.timeStart > 300000000L) {
                            //UsersRepository db;
                            //db = UsersRepository.getInstance();
                            //db.newLogin(textToSendNdc.getText(), textToSendMdp.getText());
                                this.stop();
                                champsNoRespect.setVisible(false);
                                champsNoRespectPass.setVisible(false);
                                textToSendNdc.clear();
                                textToSendMdp.clear();
                                textToSendMdpRepeat.clear();
                                mainIHM.makeSceneAuthVisible();
                        }
                    }
                }.start();
            }
        });

        Line lineup = new Line(150, 100, 450, 100);
        lineup.setStroke(Color.GREY);
        lineup.setStrokeWidth(1);

        Line linelow = new Line(150, 360, 450, 360);
        linelow.setStroke(Color.GREY);
        linelow.setStrokeWidth(1);

        this.getChildren().add(textToSendNdc);
        this.getChildren().add(textToSendMdp);
        this.getChildren().add(textToSendMdpRepeat);
        this.getChildren().add(textToSendNdcAnnot);
        this.getChildren().add(textToSendMdpAnnot);
        this.getChildren().add(textToSendMdpRepeatAnnot);
        this.getChildren().add(textWelcome);
        this.getChildren().add(textPseudo);
        this.getChildren().add(textPassword);
        this.getChildren().add(textRepeatPassword);
        this.getChildren().add(sendBtn);
        this.getChildren().add(sendCompte);
        this.getChildren().add(valid);
        this.getChildren().add(champsNoRespect);
        this.getChildren().add(champsNoRespectPass);
        this.getChildren().add(lineup);
        this.getChildren().add(linelow);
        //Constructeur

        //Chargement des information du premier enregistrement         

    }
//les TextField sont placé sur une Grille 2*2

    private Pane initFields() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_LEFT);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(20);
        grid.setVgap(2);
        grid.add(new Label("ID"), 1, 0);
        idTextField.setEditable(false);
        grid.add(idTextField, 2, 0);
        grid.add(new Label("Nom"), 1, 1);
        grid.add(textToSendNdc, 2, 1);
        grid.add(new Label("Prenom"), 1, 2);

        return grid;
    }

//Chargement des informations des TextFields dans l'objet UserAccount
  /*  private UserAccount getFieldData() {
        UserAccount user = new UserAccount();
        user.setId(Integer.parseInt(idTextField.getText()));
        user.setNdc(textToSendNdc.getText());
        user.setMdp(textToSendMdp.getText());
        return user;
    }
*/
//chargement de l'objet UserAccount dans les TextFiels
  /*  private void setFieldData(UserAccount user) {
        idTextField.setText(String.valueOf(user.getId()));
        textToSendNdc.setText(user.getNdc());
        textToSendMdp.setText(user.getMdp());
    }*/

//Test si un des fields est vide
    private boolean isEmptyFieldsData() {
        if (textToSendNdc.getText().equals("") || textToSendMdp.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

//Gestion des evenement etends la class EventHandler et redefinie la methode handler
  /*  private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            UserAccount user = getFieldData();
            if (event.getSource().equals(sendBtn) && sendBtn.getText().equals("Création du compte")) {
                if (isEmptyFieldsData()) {
                    champsNoRespect.setText("Veuillez renseigner tous les champs");
                    return;
                }
                if (userBean.create(user) != null) {
                    champsNoRespect.setText("Le compte à bien été créer !");
                }
                sendBtn.setText("Création du compte");
            } else if (event.getSource().equals(sendBtn) && sendBtn.getText().equals("Création du compte")) {
                user.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
                user.setNdc("");
                user.setMdp("");
                setFieldData(user);
            }

        }

    }*/
}
