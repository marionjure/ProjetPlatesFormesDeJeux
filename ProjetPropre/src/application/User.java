package src.application;




import src.Interfaces.ElementDeListe;

import java.util.ArrayList;

public class User implements ElementDeListe {
    public static final String ROLE_USER = "J", ROLE_ADMIN = "A";
    private String pseudo,mdp,mail,role;
    private boolean actif;
    private byte[] icone;

    public User(String pseudo, String mdp, String mail, String role, int actif){
        this(pseudo,mdp,mail,role,actif,null);
    }

    public User(String pseudo, String mdp, String mail, String role, int actif, byte[] icone){
        this.pseudo = pseudo;
        this.mail=mail;
        this.mdp = mdp;
        this.role=role;
        this.actif=actif==1;
        this.icone = icone;
    }

    public String toString(){
        return pseudo+" "+mail;
    }

    /**
     * indique si le joueur est banni
     * @return
     */

    public String getPseudo(){
        return this.pseudo;
    }

    /**
     * renvoi le mot de passe de l'utilisateur
     * @return
     */
    public String getMdp(){
        return this.mdp;
    }

    /**
     * Modifie le mot de passe de l'utilisateur
     * @return
     */
    public void setMdp(String newMdp){this.mdp=newMdp;}

    /**
     * renvoit le mail de l'utilisateur
     * @return
     */
    public String getMail(){
        return this.mail;
    }

    /**
     * renvoit le role de l'utilisateur
     * @return
     */
    public String getRole(){
        return this.role;
    }

    /**
     * renvoit l'état de joueur
     * @return
     */
    public boolean isActif(){
        return this.actif;
    }

    /**
     * renvoit l'état de l'utilisateur sous forme d'entier
     * @return
     */
    public int getActifInt(){
        return this.actif ? 1:0;
    }

    /**
     * permet de bannir ou non joueur.
     * @param etat
     */
    public void setActif(boolean etat){
        this.actif = etat;
    }

    /**
     * indique je le joueur est administrateur
     * @return
     */
    public boolean isAdmin(){
        return role.equals("A");
    }

    /**
     * permet de définir le role de lutilisateur
     * des var statique sont def pour avoir les valeur possible
     * @param role
     */
    public void setRole(String role){
        this.role = role;
    }

    /**
     * renvoit licone du joueur (peut renvoyer null)
     * @return
     */

    public void loadIcon(){
        this.icone = GLOBALS.REQUETE_BD.chargerIcone(this);
    }

    public byte[] getIcone() {
        return icone;
    }
    public void setIcone(byte[] b) {
        icone=b;
    }



    @Override
    public String getTexteAffichage() {
        return this.pseudo;
    }



    /**
     * indique si j=deux joueur sont identique
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if(o!=null) {
            User u = (User) o;
            return this.pseudo.equals(u.pseudo) || this.mail.equals(u.mail);
        }
        return false;
    }

    @Override
    public void majDansBd() {
        GLOBALS.REQUETE_BD.updateJoueur(this);

        GLOBALS.REQUETE_BD.updateJoueurPseudoMdp(this);

    }

    public int getNbPartiesJouées(String nomJoueur,int idJeu){
        return GLOBALS.REQUETE_BD.getNombrePartiesJouéesSurJeu(nomJoueur,idJeu);
    }

    public int getNbPartiesGagnées(String nomJoueur,int idJeu){
        return GLOBALS.REQUETE_BD.getNombrePartiesGagnéesSurJeu(nomJoueur,idJeu);
    }

    public ArrayList<Integer> getListePartiesEnCours(String nomJoueur){
        return GLOBALS.REQUETE_BD.getPartiesEnCours(nomJoueur);
    }

    public void majDansBdPourIcone() {
        GLOBALS.REQUETE_BD.updateJoueurIcone(this);



}}
