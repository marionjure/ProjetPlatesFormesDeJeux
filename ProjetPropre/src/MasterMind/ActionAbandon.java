package src.MasterMind;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import src.application.GLOBALS;

import java.lang.Integer;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controleur du clavier
 */
public class ActionAbandon implements EventHandler<ActionEvent> {

    /**
     * vue du jeu
     */
    private MasterMind masterMind;
    private CombinaisonMystere combinaisonMystere;

    /**
     * @param m vue du jeu
     */
    ActionAbandon(MasterMind m,CombinaisonMystere c){
        this.masterMind=m;
        this.combinaisonMystere=c;
    }
    /**
     * Actions à effectuer lors du clic sur une couleur du clavier
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b= (Button) actionEvent.getSource ();
        try {
            // Permet de créer le message d'avertissement avec des buttons 'yes' ou 'cancel' pour confirmer ou annuler
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Voulez vous vraiment abandonner ?");
            alert.setContentText("Vous serez déclaré perdant de la partie");

            // Creation des boutons 'oui' et 'Non'
            ButtonType oui = new ButtonType("Oui");
            ButtonType annuler = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(oui, annuler);

            Optional<ButtonType> result = alert.showAndWait();
            // Si l'utilisateur confirme l'abandon, ferme l'application
            if (result.get() == oui)
            {
                GLOBALS.REQUETE_BD.abandon(this.masterMind.getMasterMindModele().getPartie());
                this.masterMind.close();
            }
        }
        catch (SQLException e)
        {
            System.out.println("ERROR 404");
        }


    }
}
