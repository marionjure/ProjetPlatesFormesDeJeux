package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import src.Interfaces.Jeux;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 * Controleur des boutons refuser et accepter
 */
public class ActionInvitation implements EventHandler<ActionEvent> {
	/**
	 * vue des invitations
	 */
    private Invitation invitation;
    /**
	 * modele des invitations
	 */
    private InvitationBD invitationBD;
    private String user;
    private int idjeu;
    private Jeu j;
	 /**
	  * constructeur ActionValider
	  * @param invitation vue des invitations
	  * @param invitationBD modele des invitations
	   */
     ActionInvitation(Invitation invitation, InvitationBD invitationBD, String user, Jeu j){
        this.invitation=invitation;
        this.invitationBD=invitationBD;
        this.idjeu=j.getId();
        this.user=user;
        this.j=j;

    }
    /**
     * Actions à effectuer lors du clic sur des boutons refuser et accepter
     * * Il faut donc: supprimer l'invitation dans la Base de donnée,mettre à jour l'affichage
     * 				   pour accepter : inserer une participation la Base de donnée et lancer le jeu
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b= (Button) actionEvent.getSource ();

        try{
            if(b.getText().compareTo("refuser")!=0){
                System.out.println("créer une partie");
                //Inserer une participation
                int numP= GLOBALS.REQUETE_BD. maxParticiper()+1;

                ParticiperBD participerBD=new ParticiperBD(user,user.equals(invitationBD.getUSE_PSEUDOUSER())?invitationBD.getUser():invitationBD.getUSE_PSEUDOUSER(),numP,new Date());
                //ParticiperBD participerBD=new ParticiperBD(invitationBD.getUser(),invitationBD.getUSE_PSEUDOUSER(),numP,new Date());
                GLOBALS.REQUETE_BD.insererParticiper(participerBD);

                ChargeurJeu chargeur = new ChargeurJeu("src/jar/");
                Jeux ma= null;
                try {
                    ma = chargeur.chargerJeu(j.getNom()+".jar", j.getNom());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                ArrayList<String> res=GLOBALS.REQUETE_BD.getParticipantParNumPart(numP);
                ma.creerPartie(this.idjeu,res.get(0),res.get(1),this.user);
            }
            //Supprimer l'invitation
            GLOBALS.REQUETE_BD.supprimer(this.invitationBD);}

        catch(SQLException e) {
//			//.printStackTrace();
        }

    }
}
