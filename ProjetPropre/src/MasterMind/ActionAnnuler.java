package src.MasterMind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Integer;

/**
 * Controleur du bouton resert
 */
public class ActionAnnuler implements EventHandler<ActionEvent> {

	/**
	 * vue du jeu
     */
	private MasterMind masterMind;
    /**
     * @param m vue du jeu
     */
	ActionAnnuler(MasterMind m){
	    this.masterMind=m;

	}
    /**
     * Actions à effectuer lors du clic sur le bouton annuler (X)
     * * Il faut donc: Réinitialiser la combinaison actuelle, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
	@Override
	public void handle(ActionEvent actionEvent) {
	    Button b= (Button) actionEvent.getSource ();
	    // réinitialiser la combinaison actuelle
		this.masterMind.getCombinaisonActuelle().setCombinaison("vide","vide","vide","vide");
	    // Met à jour l'affichage
	    this.masterMind.majAffichage();
	}
}
