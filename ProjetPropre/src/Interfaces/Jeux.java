package src.Interfaces;

public interface Jeux {
    /**
     * Permet à un joueur de lancer le jeu en retrouvant l'état actuel de la partie
     * @param idPartie est l'indentifiant de la partie dans la base de donnée
     * @param numJoueur est le numéro du joueur de la partie (1 ou 2)
     * @param partage permet de donner au jeu l'accès à des variables du module joueur
     * @return donne le composant graphique dans lequel s'affiche le jeu
     */
    public void jouerCoup(int idPartie, String numJoueur, Object partage);

    /**
     * Permet de demander à créer une nouvelle partie et de la lancer
     * @param idJeu est l'identifiant du jeu dans la base de données
     * @param idJoueur1 identifiant du premier joueur
     * @param idJoueur2 identifiant du second joueur
     * @param partage permet de donner au jeu l'accès à des variables du module joueur
     * @return donne le composant graphique dans lequel s'affiche le jeu
     */
    public void creerPartie(int idJeu, String idJoueur1, String idJoueur2, Object partage);

    /**
     * Nous permet de reprendre une Partie qui est encore en cours
     * @param idJeu, l'identifiant unique d'un jeu
     * @param idJoueur1, le Pseudo d'un Joueur
     * @param idJoueur2, le Pseudo D'un Joueur
     * @param partage, permet de donner au jeu l'accès à des variables du module joueur
     * @param numPartie, le numéro identifiant unique d'une partie
     */
    public void reprendrePartie(int idJeu, String idJoueur1, String idJoueur2, Object partage, int numPartie);
}

