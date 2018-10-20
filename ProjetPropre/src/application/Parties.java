package src.application;

import src.Interfaces.IonChangeEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import src.Interfaces.IonSelectedEvent;

import java.util.ArrayList;


public class Parties extends HBox implements IonSelectedEvent {

    private MainActivity mainActivity;
    private User u;
    private ArrayList<Integer> listeIdJeu;
    private int numPartie;
    private TabSpecial partie;


    public Parties(User u,MainActivity mainActivity){


        this.mainActivity=mainActivity;
        this.u=u;
        BorderPane cont=new BorderPane();
        cont.setLeft(this.Gauche());
        cont.setRight(this.Droite());
        cont.setTop(this.Haut());
        cont.setBottom((this.Bas()));
        getChildren().addAll(cont);
        this.setAlignment(Pos.CENTER);

    }
    private VBox Haut(){
        VBox haut = new VBox();
        Button Deconnexion = new Button("Deconnexion");
        Deconnexion.getStyleClass().add("abandon");
        Deconnexion.setText("Deconnexion");
        Deconnexion.setId("Deconnexion");
        Deconnexion.setOnAction(new ActionBouton(this.u, mainActivity));
        haut.setAlignment(Pos.CENTER_RIGHT);
        haut.setPadding(new Insets(10,10,10,0));
        haut.getChildren().add(Deconnexion);
        return haut;
    }


    private VBox Gauche(){
        listeIdJeu=this.u.getListePartiesEnCours(this.u.getPseudo());
        System.out.println(listeIdJeu);
        VBox gauche = new VBox();
        TabPane parties =new TabPane();
        parties.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        actionTab action=new actionTab(this.u);
        Tab t = new Tab();
        t.setGraphic(new Label("Nom des jeux"));
        parties.getTabs().add(t);
        for(Integer i:listeIdJeu) {
            partie = new TabSpecial(i,action);
            Label textepartie = new Label(GLOBALS.REQUETE_BD.getNomJeu(i)+ "\nAdversaire : "+GLOBALS.REQUETE_BD.getNomAdversaire(i, u.getPseudo())+"\nDate : "+GLOBALS.REQUETE_BD.getDatePartie(i));
            textepartie.setMinWidth(200);
            textepartie.setWrapText(true);
            partie.setGraphic(textepartie);
            partie.setOnSelectionChanged(action);
            parties.getTabs().add(partie);
        }

        parties.getSelectionModel().clearSelection();
        Label l1 =new Label("Parties en cours");
        l1.setFont(Font.font(20));

        parties.setTabMinHeight(200);
        parties.setTabMaxHeight(200);
        parties.setTabMinWidth(100);
        parties.setTabMaxWidth(100);
        parties.setSide(Side.LEFT);
        gauche.getChildren().addAll(l1,parties);
        gauche.setPadding(new Insets(10,20,10,20));
        return gauche;
    }

    private VBox Droite(){
        VBox droite = new listeInvitation(new Jeu(-1,null,null,null,null,true),this.u.getPseudo(),0);
        droite.setPadding(new Insets(10,20,10,20));
        return droite;
    }

    private VBox Bas(){
        VBox bas = new VBox();
        Button retour = new Button("Retour");
        retour.setText("Retour");
        retour.setId("Retour");
        retour.getStyleClass().add("abandon");
        retour.setOnAction(new ActionBouton(this.u, this.mainActivity));
        bas.setAlignment(Pos.CENTER_RIGHT);
        bas.setPadding(new Insets(10,10,10,0));
        bas.getChildren().add(retour);
        return bas;
    }


    @Override
    public void actualiser() {
        System.out.println("actualiser");
    }
}
