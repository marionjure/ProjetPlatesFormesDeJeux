package src.MasterMind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Integer;

/**
 * Controleur du clavier
 */
public class ActionClavier implements EventHandler<ActionEvent> {

	/**
	 * vue du jeu
     */
	private MasterMind masterMind;

    /**
     * @param m vue du jeu
     */
	ActionClavier(MasterMind m){
	    this.masterMind=m;

	}
    /**
     * Actions à effectuer lors du clic sur une couleur du clavier
     * Il faut donc: Ajouter la couleur dans la combinaison actuelle, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
	@Override
	public void handle(ActionEvent actionEvent) {
	    ButtonCouleur b= (ButtonCouleur) actionEvent.getSource ();
	    // Trouver l'indice du premier emplacement vide dans la combinaison actuelle
		int i=this.masterMind.getElabore().IndicePremiereBoutonVide();
		// Ajouter la couleur dans la combinaison actuelle
		this.masterMind.getCombinaisonActuelle().setCouleur(b.getNomCouleur(),i);
		//Met à jour l'affichage
		this.masterMind.majAffichage();
	    
	}
}
