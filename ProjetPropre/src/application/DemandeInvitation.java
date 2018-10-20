package src.application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;

public class DemandeInvitation extends VBox {
    private String user;
    DemandeInvitation(String user){
        this.user=user;
        this.setMaxWidth(220);
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {

                getChildren().clear();
                getChildren().add(plus(user));
                getChildren().add(new Demande(user));
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();

        //this.getChildren().add(plus());

    }
    private VBox plus(String user){
        HBox haut=new HBox();
        haut.getChildren().add(new Label("Liste des amis"));
        VBox panal =new VBox();
        panal.setPrefWidth(200);
        panal.setPrefHeight(200);
        Button res =new Button("+");
        res.setOnAction(new ActionPlus(this.user));
        haut.getChildren().add(res);
        haut.setSpacing(10);
        panal.getChildren().add(haut);
        panal.getChildren().add(liste(user));
        return panal;

    }
    private VBox liste(String user){
        ScrollPane scroll=new ScrollPane();
        VBox res =new VBox();
        VBox panel=new VBox();
        //ArrayList<String> m=GLOBALS.REQUETE_BD.listePasAmis(user);
        ArrayList<String> m=GLOBALS.REQUETE_BD.listeMesAmis(user);
        for (int i=0;i<m.size();i++){
            res.getChildren().add(new Label(m.get(i)));
        }
        scroll.setContent(res);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-padding: 15;");
        scroll.setVvalue(1);
        panel.getChildren().add(scroll);
        return panel;
    }
}
