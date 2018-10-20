package src.MasterMind;

import javafx.scene.layout.*;
import java.util.ArrayList;

import javafx.scene.paint.Color;

/**
 * Génère la vue de la combinaison en cours et associe le contrôleur aux bouton
 * le choix ici est d'un faire un héritié d'un HBox
 */
public class ElaboreCombinaison extends HBox {
	/**
	 * stocker les boutonCouleur dans un ArrayList
	 */
	private ArrayList<ButtonCouleur> liste;
	/**
	 * constructeur du clavier
	 * @param actionChoix le contrôleur des boutons
	 */
	public ElaboreCombinaison(ActionChoix actionChoix )
	{
		this.liste=new ArrayList<ButtonCouleur>();
		for(int i=0 ;i<4 ;i++)
		{
			// Création d'un boutonCouleur//
			ButtonCouleur b = new ButtonCouleur("vide",i);
			b.setOnAction(actionChoix);
			//Ajout sur panel
			this.getChildren().add(b);
			//Ajout à la liste des boutonCouleur
			this.liste.add(b);
		}
		// Style //
		this.setBackground(new Background(new BackgroundFill(Color.GREY,null,null)));
	}
	/**
	 * permet de récupérer l'indice du premier bouton vide dans la combinaison
	 * @return l'indice du premier bouton vide ou -1 si le combinaison est compléte
	 */
	public int IndicePremiereBoutonVide()
	{
		for(ButtonCouleur elem: this.liste)
		{
			if(elem.getNomCouleur().compareTo("vide")==0)
			{
				return elem.getIndice();
			}
		}
		return -1;
	}
	/**
	 * permet change une coulour dans la combinaison
	 * @param nouvelleCouleur la nouvelle couleur
	 * @param indice l'indice ou il faut changer dans le combinaison
	 */
	public void ChangerCouleur(String nouvelleCouleur,int indice)
	{
		this.liste.get(indice).changerImage(nouvelleCouleur);
	}
	/**
	 * permet initialisation une Combinaison , mettre tout à vide

	 */
	public void initialisationCombinaison()
	{
		//remettre tout à 'vide'
		for(ButtonCouleur elem : this.liste)
		{
			elem.changerImage("vide");
		}
	}
	/**
	 * Permet de désactiver tout les boutons de l'élaboration du choix
	 * */
	public void activeBouton(boolean ok){
		for(ButtonCouleur elem: this.liste){
			if(ok==true)
			{elem.setDisable(false);}
			else elem.setDisable(true);
		}

	}
}
