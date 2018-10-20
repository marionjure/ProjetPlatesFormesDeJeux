package src.application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Demande extends VBox {
    private final String user;

    Demande(String user){
        this.user = user;
        this.getChildren().add(new Label("Demande Amis"));
        //this.getChildren().add(recevoir2(user));
        this.getChildren().add(new listeAmis(user));
        //this.getChildren().add(new listeAmis("iuto"));
        //root.getChildren().add(new listeAmis("iuto"));

    }

//    private VBox recevoir2(String user){
//        VBox res = new VBox();
//
//        //Création de la liste de ses invitation tout les secondes
//        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
//                res.getChildren().clear();
//                //res.getChildren().add(new listeInvitation(idjeu,user));
//                res.getChildren().add(new listeAmis(user));
//
//            }
//        }));
//        tl.setCycleCount(Animation.INDEFINITE);
//        tl.play();
//
//
//        return res;
//
//    }
}
