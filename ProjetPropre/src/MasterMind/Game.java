package src.MasterMind;
import java.util.ArrayList;
/**
 * Modèle du jeu 
 */
public class Game
{
	/**
	 * le nombre total de manche
	 * le nombre actuelle de manche
	 * le nombre de victoire
	 * le nombre de defaite
	 */
    private int nbManche, manche,nbVictoire,nbDefaite;
    private String adversaire; //supprimer
    /**
	 * le nombre essai
	 */
    private int essai;
	/**
	 * constructeur Game
	 * @param nbManche le nombre de manche total
	 */
    public Game(int nbManche)
    {
        this.nbManche = nbManche;
        this.manche = 1;
        this.nbVictoire = 0;
        this.nbDefaite = 0;
        this.essai=0;
    }
    /**
	 * @return le nombre de manche actuelle
	 */
    public int getNbManche()
    { 
		return nbManche;
	}
	/**
	 * @return le nombre d'essai dans une manche
	 */
    public int getEssai()
    {
        return essai;
    }
	/**
	 * Ajouter un essai
	 */
    public void IncrementEssai() 
    {
        this.essai += 1;
    }
    /**
	 * change le nombre de manche
	 * @param manche le nombre de manche
	 */

    public void setManche(int manche) 
    {
        this.manche = manche;
    }
    /**
	 * change le nombre de essai
	 * @param essai le nombre de essai
	 */

    public void setEssai(int essai) 
    {
        this.essai = essai;
    }
	/**
	 * @return le nombre de  manche
	 */
    public int getManche()
    { 
		return manche;
	}
	/**
	 * Ajouter d'une manche
	 */
    public void finManche()
    {
        this.manche += 1;
    }
    /**
	 * Ajouter d'une victoire
	 */
    public void gagnerManche()
    {
        this.nbVictoire += 1;
    }
    /**
	 * Ajouter d'une defaite
	 */
    public void perduManche()
    {
        this.nbDefaite += 1;
    }
    /**
	 * @return un booléen indiquant si le joueur fini la manche
	 */
    public boolean finPartie()
    {
        return nbManche < manche;
    }
    /**
	 * @return un booléen indiquant si le joueur a gangé le jeu
	 */
    public boolean resultatJeu()
    {
        if (nbDefaite<nbVictoire)
        {
            return true;
        }
        return false;
    }

}
