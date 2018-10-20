package src.application;

import java.util.Date;
/**
 * le modele 
 */
public class ParticiperBD {
	/**
	 * le joueur
	 */
    private String PSEUDOUSER ;
    /**
	 * l'adversaire
	 */
    private String USE_PSEUDOUSER;
    /**
	 * le numéro de la partie
	 */
    private int NUMPARTIE;
    /**
	 * la date
	 */
    private Date DATEPARTICIPATION;
    /**
	 * constructeur InvitationBD
	 * @param PSEUDOUSER le joueur
	 * @param USE_PSEUDOUSER l'adversaire
	 * @param NUMPARTIE le numéro de la partie
	 * @param DATEPARTICIPATION la date
	 */
    ParticiperBD(String PSEUDOUSER, String USE_PSEUDOUSER, int NUMPARTIE, Date DATEPARTICIPATION){
        this.NUMPARTIE=NUMPARTIE;
        this.DATEPARTICIPATION=DATEPARTICIPATION;
        this.PSEUDOUSER=PSEUDOUSER;
        this.USE_PSEUDOUSER=USE_PSEUDOUSER;
    }
	/**
	 * @return la date
	 */
    public Date getDATEPARTICIPATION() {
        return DATEPARTICIPATION;
    }
	/**
	 * @return le numéro de la partie
	 */
    public int getNUMPARTIE() {
        return NUMPARTIE;
    }
	/**
	 * @return le joueur
	 */
    public String getPSEUDOUSER() {
        return PSEUDOUSER;
    }
	/**
	 * @return l'adversaire
	 */
    public String getUSE_PSEUDOUSER() {
        return USE_PSEUDOUSER;
    }
}
