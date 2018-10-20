package src.application;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Tchat extends VBox {
    public ArrayList<Message> lesMessages;
    public ArrayList<User> lesUtilisateurs;
    private VBox monTchat;
    private ScrollPane sp;
    private TextArea msg;
    private ContextMenu cm;
    private User u;
    private Timeline tl;
    private Button send;
    private User destinataireUnique;

    /**
     * Création du tctat
     */
    public Tchat(User u) {
        super();
        this.setWidth(GLOBALS.TCHAT_WIDTH);
        this.setHeight(GLOBALS.TCHAT_HEIGHT);

        lesMessages = new ArrayList<>();
        lesUtilisateurs = GLOBALS.REQUETE_BD.getListeUtilisateur();

        this.u = u;

        tl = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                remplir();
            }
        }));

        tl.setCycleCount(Animation.INDEFINITE);

        tl.play();

        this.getChildren().addAll(genereHaut(), genereBas());


    }


    public void passerEnAnglais(){
        this.msg.setPromptText("Message to send");
        this.send.setText("Send");
    }


    public Tchat(User u, User d) {
        this(u);
        destinataireUnique = d;
    }


    /**
     * partie affichage des message du tchat
     *
     * @return
     */
    private ScrollPane genereHaut() {
        monTchat = new VBox();
        monTchat.setPrefHeight(GLOBALS.TCHAT_CONTENT_HEIGHT);
        monTchat.setPrefWidth(GLOBALS.TCHAT_WIDTH);
        monTchat.setBorder(GLOBALS.BORDER_DEFAULT);
        monTchat.setSpacing(3);
        sp = new ScrollPane(monTchat);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return sp;
    }

    /**
     * partie envoy de messages
     *
     * @return
     */
    private HBox genereBas() {
        HBox partieBas = new HBox();
        send = new Button("Envoyer");
        send.getStyleClass().add("abandon");
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionEnvoyer();
            }
        });
        genereMsg();

        VBox conteneur = new VBox(msg, send);
        conteneur.setAlignment(Pos.CENTER);
        partieBas.getChildren().add(conteneur);
        partieBas.setAlignment(Pos.CENTER);
        partieBas.setPrefWidth(GLOBALS.TCHAT_WIDTH);

        return partieBas;

    }

    /**
     * création du textfield pour écrire les message et gestion de l'autocomplétion
     */
    public void genereMsg() {
        msg = new TextArea();
        msg.setMaxHeight(GLOBALS.TCHAT_HEIGHT * 0.1);
        msg.setPromptText("Message a envoyer");
        msg.setTooltip(new Tooltip("Vous pouvez préceder le message par @pseudoDuDestinataire \nCela permetra de l'envoyer a une personne précise"));

        msg.setOnKeyReleased(new EventHandler<KeyEvent>() {  // le handler qui sert a l'autocomplétion
            @Override
            public void handle(KeyEvent event) {
                if (msg.getText().length() > 1 && !msg.getText().contains(" ") && msg.getText().charAt(0) == '@') { //vérif que le texte est formatté correctement
                    if (cm != null)
                        cm.hide(); //on cache l'ancienne proposition d'autocomplétion si il en a une
                    cm = new ContextMenu();
                    for (User usr : lesUtilisateurs) {
                        if (usr.getPseudo().contains(msg.getText().substring(1))) { // on regarde si l'utilisateur correspond a la recherche
                            MenuItem mi = new MenuItem("@" + usr.getPseudo());
                            mi.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    String suite = "";
                                    boolean premier = true;
                                    for (String str : msg.getText().split(" ")) {
                                        if (premier) {
                                            premier = false;
                                        } else {
                                            suite += str + " ";
                                        }
                                    }
                                    msg.setText(mi.getText() + " " + suite);
                                    msg.positionCaret(msg.getText().length());
                                }
                            });
                            cm.getItems().addAll(mi);// on ajout la proposition
                        }
                    }
                    if (cm.getItems().size() <= 10 && cm.getItems().size() > 0) { //on affiche les propositions si il y en a moins de 10
                        msg.setContextMenu(cm);
                        cm.setWidth(msg.getWidth());
                        Bounds boundsInScene = msg.localToScene(msg.getBoundsInLocal());
                        cm.show(msg, msg.getScene().getWindow().getX() + boundsInScene.getMinX() + 10, msg.getScene().getWindow().getY() + boundsInScene.getMaxY()/*+msg.getHeight()*/);

                    }
                } else { //on cache la proposition si le texte de l'utilisateur n'est plus correctement formater
                    if (cm != null)
                        cm.hide();
                }
                if (msg.getText().length() > 150) { // si taille message trop grade pour la bd
                    msg.setText(msg.getText(0, 150));
                    msg.positionCaret(msg.getText().length());
                }
            }
        });

    }

    /**
     * L'action a executer quand on appuis sur le boutton envoyer
     */
    public void actionEnvoyer() {
        if(!msg.getText().equals("")) {
            int type = Message.TYPE_GLOBAL;
            User destinataire = new User("", null, null, null, -1);
            if (this.destinataireUnique == null) {
                for (User u : lesUtilisateurs) {
                    if (this.msg.getText().split(" ")[0].contains(u.getPseudo())) {
                        destinataire = u;
                        type = Message.TYPE_PRIVER;
                        break;
                    }
                }
            } else {
                destinataire = destinataireUnique;
            }


            Message m = new Message(GLOBALS.REQUETE_BD.tchatMaxIdMessage() + 1, this.msg.getText(), type, new Date(System.currentTimeMillis()), this.u, destinataire, 0);
            GLOBALS.REQUETE_BD.tchatAddMessage(m);
            this.msg.setText("");
        }
    }

    public void remplir() {
        lesMessages = GLOBALS.REQUETE_BD.tchatListeMessageGlobaux();
        lesMessages.addAll(GLOBALS.REQUETE_BD.tchatListeMessagePriver(this.u));
        Collections.sort(lesMessages);
        majAffichage();
    }

    private void majAffichage() {
        boolean scroll = false;
        if (sp.getVmax() == sp.getVvalue())
            scroll = true;
        monTchat.getChildren().clear();
        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        for (Message m : lesMessages) {
            if (destinataireUnique==null || m.getEmeteur().equals(this.u) || m.getEmeteur().equals(this.destinataireUnique)) {
                VBox content = new VBox();
                content.setMaxWidth(GLOBALS.TCHAT_WIDTH);

                HBox div = new HBox();

                Label lab = new Label(m.getContenu());
                lab.setMaxWidth(GLOBALS.TCHAT_WIDTH * 0.8);
                lab.setWrapText(true);


                if (m.getEmeteur().equals(this.u)) {
                    content.setAlignment(Pos.CENTER_RIGHT);
                    div.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    content.setAlignment(Pos.CENTER);
                    div.setAlignment(Pos.CENTER_LEFT);
                    lab.setText(m.getEmeteur().getPseudo() + " : " + lab.getText());
                    GLOBALS.REQUETE_BD.tchatLitMessage(m);
                }

                if (m.getType() == Message.TYPE_GLOBAL) {
                    content.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), new Insets(5))));
                    lab.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(5))));
                } else if (m.getType() == Message.TYPE_PRIVER) {
                    content.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), new Insets(5))));
                    lab.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), new Insets(5))));
                }


                lab.setFont(new Font(12));
                //div.setPadding(new Insets(5));


                content.getChildren().add(lab);
                div.getChildren().add(content);

                monTchat.getChildren().addAll(div);


                content.setMaxWidth(GLOBALS.TCHAT_WIDTH * 0.90);
                content.setPrefWidth(GLOBALS.TCHAT_WIDTH * 0.90);

                if (lab.getText().length() < 36 && lab.getText().split("\n").length - 1 == 0) {
                    content.setMinHeight(40);
                    content.setMaxHeight(40);
                } else {
                    content.setMinHeight(40 + ((((int) (lab.getText().length() / 36))) + (lab.getText().split("\n").length - 1)) * 15);
                    content.setMaxHeight(40 + ((((int) (lab.getText().length() / 36))) + (lab.getText().split("\n").length - 1)) * 15);
                }
                div.setMaxWidth(GLOBALS.TCHAT_WIDTH);

            }

        }
        sp.setVvalue(1);
    }


}


