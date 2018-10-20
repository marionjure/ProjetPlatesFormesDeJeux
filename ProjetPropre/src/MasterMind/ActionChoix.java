package src.MasterMind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Integer;

/**
 * Controleur de la combinaison actuelle 
 */
public class ActionChoix implements EventHandler<ActionEvent> {

	/**
	 * vue du jeu
     */
	private MasterMind masterMind;

    /**
     * @param m vue du jeu
     */
	ActionChoix(MasterMind m){
	    this.masterMind=m;

	}
    /**
     * Actions à effectuer lors du clic sur un bouton de la combinaison actuelle
     * Il faut donc: Enlever la couleur de la combinaison actuelle, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
	@Override
	public void handle(ActionEvent actionEvent) {
	    ButtonCouleur b= (ButtonCouleur) actionEvent.getSource ();
		//Enlever la couleur de la combinaison actuelle
		this.masterMind.getCombinaisonActuelle().setCouleur("vide",b.getIndice());
		//Met à jour l'affichage
		this.masterMind.majAffichage();;
	    
	}
}
