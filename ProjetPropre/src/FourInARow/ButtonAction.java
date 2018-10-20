package src.FourInARow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import src.application.GLOBALS;

import java.sql.SQLException;
import java.util.Optional;

public class ButtonAction implements EventHandler<ActionEvent> {

    private FourInARow four;

    public ButtonAction(FourInARow four) {
        this.four = four;
    }

    /**
     * Permet d'afficher une alerte quand l'utilisateur clique sur abandonner
     *
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();

        // Permet de créer le message d'avertissement avec des buttons 'yes' ou 'cancel' pour confirmer ou annuler
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to give up ?");
        alert.setContentText("You will lose your progress");

        // Creation des boutons 'yes' et 'cancel'
        ButtonType yes = new ButtonType("Yes");
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        // Si l'utilisateur confirme l'abandon, ferme l'application
        // TODO Revenir à l'IHM de selection de jeu
        if (result.get() == yes) {
            try {
                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -2);
                this.four.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

