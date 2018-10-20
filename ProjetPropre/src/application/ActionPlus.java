package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class ActionPlus implements EventHandler<ActionEvent> {
    private String user;
    ActionPlus(String user){
        this.user=user;

    }
    /**
     * Actions à effectuer lors du clic sur le bouton envoyer
     * * Il faut donc: Inserer une invitation dans la Base de Donnée, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {

//            p.afficheReponse();}
//        if(option.get() == non){this.p.getChrono().start();}}
            Ami a= new Ami(this.user);
        try {
            a.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
