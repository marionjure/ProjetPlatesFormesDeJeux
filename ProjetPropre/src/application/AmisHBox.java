package src.application;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AmisHBox extends VBox {
    /**
     * constructeur  InvitationHBox
     * @param user le joueur
     */

    //InvitationHBox(String user,ActionInvitation actionV,String texte){
    AmisHBox(String user,ActionAccepter actionAccepter){
        super();
        HBox res= new HBox();
        //Création des bouton accpter et refuser
        Button accepter=new Button("accepter");
        Button annuler=new Button("refuser");
        annuler.setOnAction(actionAccepter);
        accepter.setOnAction(actionAccepter);
        //Ajout dans le res
        res.getChildren().add(accepter);
        res.getChildren().add(annuler);
        res.setSpacing(20);
        //Ajout dans la panel
        this.getChildren().add(new Label(user));
        this.getChildren().add(res);
        this.setSpacing(5);
        this.setStyle("-fx-padding: 5;-fx-border-style: solid inside;");
        this.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE,null,null)));
    }
}


