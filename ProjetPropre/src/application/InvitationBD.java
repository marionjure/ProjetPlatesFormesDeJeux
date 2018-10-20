package src.application;

import java.util.Date;
/**
 * le modele 
 */
public class InvitationBD {
	/**
	 * le joueur
	 */
    private String user;
    /**
	 * l'adversaire
	 */
    private String USE_PSEUDOUSER;
    /**
	 * l'id du jeu
	 */
    private int idjeu;
    /**
	 * la date
	 */
    private Date date;
    /**
	 * constructeur InvitationBD
	 * @param user le joueur
	 * @param USE_PSEUDOUSER l'adversaire
	 * @param idjeu id du jeu
	 * @param date la date
	 */
    InvitationBD(String user,String USE_PSEUDOUSER,int idjeu,Date date){
        this.user=user;
        this.USE_PSEUDOUSER=USE_PSEUDOUSER;
        this.idjeu=idjeu;
        this.date=date;

    }
    /**
	 * constructeur InvitationBD
	 */
    InvitationBD(){
        this.user="";
        this.USE_PSEUDOUSER="";
        this.idjeu=-1;
        this.date=new Date();

    }

	/**
	 * @return le joueur
	 */
    public String getUser() {
        return user;
    }
	/**
	 * @return la date
	 */
    public Date getDate() {
        return date;
    }
	/**
	 * @return l'id du jeu
	 */
    public int getIdjeu() {
        return idjeu;
    }
	/**
	 * @return l'adversaire
	 */
    public String getUSE_PSEUDOUSER() {
        return USE_PSEUDOUSER;
    }
	/**
	 * change l'adversaire
	 * @param USE_PSEUDOUSER le nouveau adversaire
	 */
    public void setUSE_PSEUDOUSER(String USE_PSEUDOUSER) {
        this.USE_PSEUDOUSER = USE_PSEUDOUSER;
    }
	/**
	 * change la date
	 * @param date la nouvelle date
	 */
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  this.user +"user \n"+
        this.USE_PSEUDOUSER +"USE_PSEUDOUSER \n"+
        this.idjeu +"idjeu \n"+
        this.date +"date \n";
    }
}
