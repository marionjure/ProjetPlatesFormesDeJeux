package src.application;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Date;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Date;

public class Partie {

    private int idPartie;
    private int idJeu;
    private int numPartie;
    private Date datePartie;
    private int numEtapePartie;
    private String ActionJoueur;
    private String JoueurActionPartie;
    private int etatPartie;
    private String vainqueurPartie;

    /* TODO: mettre les attributs avec les requêtes qui recupère les infos dont on a besoin */

    public Partie(int idJ, int numP, Date dateP, int numEtape,String actionJ,String joueurAction,int etatP, String vainqueurP){
        this.idPartie=0; // r
        this.idJeu=idJ;
        this.numPartie=numP;
        this.datePartie=dateP;
        this.numEtapePartie=numEtape; //r
        this.ActionJoueur=actionJ;
        this.JoueurActionPartie=joueurAction;
        this.etatPartie=etatP;
        this.vainqueurPartie=vainqueurP;
    }

    public Date getDatePartie() {
        return datePartie;
    }

    public int getEtatPartie() {
        return etatPartie;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public int getNumEtapePartie() {
        return numEtapePartie;
    }

    public int getNumPartie() {
        return numPartie;
    }

    public String getVainqueurPartie() {
        return vainqueurPartie;
    }

    public String getActionJoueur() {
        return ActionJoueur;
    }

    public String getJoueurActionPartie() {
        return JoueurActionPartie;
    }

    public void setActionJoueur(String actionJoueur) {
        ActionJoueur = actionJoueur;
    }

    public void setDatePartie(Date datePartie) {
        this.datePartie = datePartie;
    }

    public void setEtatPartie(int etatPartie) {
        this.etatPartie = etatPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public void setJoueurActionPartie(String joueurActionPartie) {
        this.JoueurActionPartie = joueurActionPartie;
    }

    public void setNumEtapePartie(int numEtapePartie) {
        this.numEtapePartie = numEtapePartie;
    }

    public void setNumPartie(int numPartie) {
        this.numPartie = numPartie;
    }

    public void setVainqueurPartie(String vainqueurPartie) {
        this.vainqueurPartie = vainqueurPartie;
    }
    public String toString(){
        return  "idPartie "+this.idPartie+ "\n"+ "idJeu" + +this.idJeu+ "\n numPartie "+this.numPartie+ "\n numEtape "+this.numEtapePartie +"\n actionJ "+ this.ActionJoueur +"\n datePartie "+ this.datePartie +"\n numEtapePartie "+ this.numEtapePartie + "\n actionJoueur "+ this.ActionJoueur +"\n joueurActionPartie "+ this.JoueurActionPartie +"\n etatPartie " +this.etatPartie +"\n vainqueurPartie " +this.vainqueurPartie;

    }
}

