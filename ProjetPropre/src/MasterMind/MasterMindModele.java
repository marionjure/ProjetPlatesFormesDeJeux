package src.MasterMind;

import src.application.Partie;

/**
 * modèle du jeu
 */
public class MasterMindModele {
	/**
	* le jeu
	*/
    private Game game;
    /**
	* la partie
	*/
    private Partie partie;
    /**
	* la combinaison mystere
	*/
    private CombinaisonMystere combinaisonMystere;
    /**
	* le user
	*/
    private String user;
    /**
	* l'adversaire
	*/
    private String adversaire;
    /**
	 * constructeur Modele
	 * @param game le jeu
	 * @param combinaisonMystere la combinaison mystere
	 * @param user le user
	 * @param adversaire l'adversaire
	 */
    MasterMindModele(Game game, CombinaisonMystere combinaisonMystere, String user, String adversaire){
        this.game=game;
        this.partie=null;
        this.combinaisonMystere=combinaisonMystere;
        this.user=user;
        this.adversaire=adversaire;
    }
	/**
	 * change le user
	 * @param user le nouveau user
	 */
    public void setUser(String user) {
        this.user = user;
    }
	/**
	 * @return l'adversaire
	 */
    public String getAdversaire() {
        return adversaire;
    }
	/**
	 * @return le user
	 */
    public String getUser() {
        return user;
    }
    /**
	 * @return le jeu
	 */
    public Game getGame() {
        return game;
    }
	/**
	 * @return la combinaison mystere
	 */
    public CombinaisonMystere getCombinaisonMystere() {
        return combinaisonMystere;
    }
	/**
	 * @return la partie
	 */
    public Partie getPartie() {
        return partie;
    }
    /**
	 * change la partie
	 * @param partie le nouvelle partie
	 */
    public void setPartie(Partie partie) {
        this.partie = partie;
    }
}
