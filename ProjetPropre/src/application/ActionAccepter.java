package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;

public class ActionAccepter implements EventHandler<ActionEvent> {
    private AmisBD amisBD;
    private AmisBD amisBD1;
    ActionAccepter(AmisBD amisBD,AmisBD amisBD1){
        this.amisBD=amisBD;
        this.amisBD1=amisBD1;

    }
    /**
     * Actions à effectuer lors du clic sur le bouton envoyer
     * * Il faut donc: Inserer une invitation dans la Base de Donnée, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b=(Button) actionEvent.getSource ();
//            p.afficheReponse();}
//        if(option.get() == non){this.p.getChrono().start();}}
    try {
        if (b.getText().compareTo("accepter") == 0) {
            GLOBALS.REQUETE_BD.insererAmis2(this.amisBD);
            GLOBALS.REQUETE_BD.majAmis(this.amisBD1);


        }
        else{
            GLOBALS.REQUETE_BD.deleteAmis(this.amisBD1);
        }
    }
    catch (SQLException y){}
    }
}


