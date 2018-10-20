package src.MasterMind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import src.application.GLOBALS;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Controleur du bouton valider
 */
public class ActionValider implements EventHandler<ActionEvent> {

	/**
	 * vue du jeu
     */
	private MasterMind masterMind;
	/**
	 * modéle du jeu
     */
	private MasterMindModele masterMindModele;

    /**
     * @param m vue du jeu
     * @param na modéle du jeu
     */
	ActionValider(MasterMind m,MasterMindModele na){
	    this.masterMind=m;
	    this.masterMindModele=na;
	}
    /**
     * Actions à effectuer lors du clic sur lr bouton valider
     * Il faut donc: Essayer si la combinaison actuelle est correcte,si oui crée une nouvelle manche ou annonce le vainqueur
     * @param actionEvent l'événement
     */
	@Override
	public void handle(ActionEvent actionEvent) {
	    Button b= (Button) actionEvent.getSource ();
	    //Création d'un Alert
		Alert  alFin =new Alert(Alert.AlertType.INFORMATION);
		alFin.setTitle("Bien joué !");
		//Regarde si la combinaison actuelle est incorrecte
		//Ajout de la combinaison actuelle sur le plateau
		this.masterMind.getListeCombinaisonTrouver().getChildren().add(new AfficheCombinaison(this.masterMind.getCombinaisonActuelle(),50));
		//Création de la combinaison avec les indications des pions bien ou mal placés
		//Ajout de  la combinaison  sur le plateau
        this.masterMind.getListeCombinaisonNoirBlanc().getChildren().add(new AfficheCombinaison(this.masterMindModele.getCombinaisonMystere().noirBlanc(this.masterMind.getCombinaisonActuelle()),50));
		if( this.masterMindModele.getCombinaisonMystere().gagner(this.masterMind.getCombinaisonActuelle()) )
		{
			// incrément la nombre de manche
			this.masterMindModele.getGame().finManche();
			//Changer numéro de l'étape
			this.masterMindModele.getPartie().setNumEtapePartie(this.masterMindModele.getGame().getManche());
			//Met à jour dans la base de donnée
			this.masterMind.jouerCoup(this.masterMindModele.getPartie().getNumPartie(),this.masterMindModele.getUser(),null);
			//Affiche d'une alert sur la fin de la manche
			alFin.setHeaderText("tu as finis cette manche en "+this.masterMindModele.getGame().getEssai()+" coups");
			alFin.showAndWait();

			//Regarde si la partie n'est pas fini
            if (!this.masterMindModele.getGame().finPartie() && GLOBALS.REQUETE_BD.getEtatAdverse(this.masterMindModele.getAdversaire(),this.masterMindModele.getPartie())!=-2)
            {
				//Création d'une nouvelle manche
                this.masterMind.nouvelleManche();
                this.masterMind.getChrono().start();
            }
            else
            {
				
                this.masterMind.getChrono().stop();
                this.masterMind.getCombinaisonActuelle().initialisation();
                this.masterMind.majAffichage();
                //Désactive le clavier ,la combinaison actuelle, le bouton  annuler
                this.masterMind.getElabore().activeBouton(false);
                this.masterMind.getClavier().activeBouton(false);
                this.masterMind.getAnnuler().setDisable(true);
                try{
				// Regarde si adversaire a fini	et si oui trouve le vainqueur
                this.masterMind.listeGagner();
                boolean ok=true;
                boolean finpartie = false;
                //Si l'adversaire n'a pas fini en attente de résultat
					while(GLOBALS.REQUETE_BD.numEtapeJoueur(this.masterMindModele.getPartie().getNumPartie(),this.masterMindModele.getAdversaire())<5 && GLOBALS.REQUETE_BD.numEtapeJoueur(this.masterMindModele.getPartie().getNumPartie(),this.masterMindModele.getUser())<5 && !finpartie && GLOBALS.REQUETE_BD.getEtatAdverse(this.masterMindModele.getAdversaire(),this.masterMindModele.getPartie())!=-2){
						{
							Alert al = new Alert(Alert.AlertType.CONFIRMATION);
							al.setHeaderText("Vous avez finis la partie ! En attente de l'adversaire...");
							al.setContentText("Souhaitez vous attendre votre adversaire ? ( Si vous cliquez sur oui,\n ça rafraichira cette page si l'adversaire n'a pas terminé)");
							ButtonType oui = new ButtonType("Oui");
							ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
							al.getButtonTypes().setAll(oui, non);

							Optional<ButtonType> result = al.showAndWait();
							// Si l'utilisateur confirme l'abandon, ferme l'application
							if (result.get() == non)
							{
								finpartie = true;
							}

						}
					}
					//Affiche le vainqueur si les deux ont fini
					if (!finpartie || GLOBALS.REQUETE_BD.getEtatAdverse(this.masterMindModele.getAdversaire(),this.masterMindModele.getPartie())==-2)
					{
						alFin.setHeaderText("Vous avez finis la partie ! " + GLOBALS.REQUETE_BD.VainqueurPartie(this.masterMindModele.getPartie().getNumPartie()) + " à gagné.");
						alFin.showAndWait();
					}
					this.masterMind.close();
                }
				catch (SQLException e)
				{
					e.printStackTrace();
				}

            }
		}
		//Regarder pour supprimer
		//Vérfie si pas fin de partie pour remettre à jour l
		if (!this.masterMindModele.getGame().finPartie())
		{
            this.masterMind.getCombinaisonActuelle().initialisation();
            this.masterMind.majAffichage();
        }
	    //this.masterMind.getListeCombinaisonTrouver().getChildren().add(new AfficheCombinaison(new Combinaison(),20));
	    //this.masterMind.getListeCombinaisonTrouver().getChildren().add(new AfficheCombinaison(new Combinaison("rouge","bleu","vert","noir"),20));
	}
}
