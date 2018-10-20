package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class ActionAmie implements EventHandler<ActionEvent> {
    private String user;
    private Ami ami;
    ActionAmie(String user,Ami ami){
        this.user=user;
        this.ami=ami;

    }
    /**
     * Actions à effectuer lors du clic sur le bouton envoyer
     * * Il faut donc: Inserer une invitation dans la Base de Donnée, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        RadioButton b=(RadioButton) actionEvent.getSource();
//            p.afficheReponse();}
//        if(option.get() == non){this.p.getChrono().start();}}
        Alert al =new Alert(Alert.AlertType.ERROR);
        AmisBD amisBD= new AmisBD(this.user,b.getText(),0, null);
        try{

            if(!GLOBALS.REQUETE_BD.isDemandeAmis(b.getText(), this.user) && !GLOBALS.REQUETE_BD.isDemandeAmis(this.user, b.getText()) ){
                GLOBALS.REQUETE_BD.insererAmis(amisBD);
            }
            else{
                al.setTitle("Attention !!!");
                al.setHeaderText("Vous avez déjà envoyé ou recu une demmande de"+b.getText());
                al.showAndWait();
            }

        this.ami.getPrimaryStage().close();
        }
        catch (SQLException e){}


    }
}
