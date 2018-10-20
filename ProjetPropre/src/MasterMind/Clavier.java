package src.MasterMind;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
/**
 * Génère la vue d'un clavier et associe le contrôleur des boutons
 * le choix ici est d'un faire un héritié d'un HBox
 */
public class Clavier extends HBox
{
	/**
     * la liste des différentes couleur du clavier
     */
    private ArrayList<ButtonCouleur> liste;
    /**
     * constructeur du clavier
     * @param action le contrôleur des boutons
     */
    public Clavier(ActionClavier action)
    {
		super();
		this.liste= new ArrayList<>();
		//la liste des couleurs du clavier
		ArrayList<String> couleur=new ArrayList<String>();
		couleur.add("bleu");
        couleur.add("noir");
        couleur.add("vert");
        couleur.add("blanc");
        couleur.add("jaune");
        couleur.add("rouge");

        for(String elem : couleur)
        {
			 ButtonCouleur b = new ButtonCouleur(elem);
             this.getChildren().add(b);
             b.setOnAction(action);
             this.liste.add(b);
        }
		// Style //
        this.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: black;"+"-fx-padding: 20;");
        this.setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
		// Fin Style //
    }
    /**
    * permet activer ou désactiver les bouton du clavier
	* @param ok l'état de bouton : true:activation et false:désactivation
    */
    public void activeBouton(boolean ok){
        for(ButtonCouleur elem: this.liste){
            if(ok==true)
            {elem.setDisable(false);}
            else elem.setDisable(true);
        }

    }
}
