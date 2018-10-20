package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import src.Exceptions.ExceptionInvitation;
import src.Interfaces.Jeux;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ActionJouer implements EventHandler<ActionEvent> {

    /**
     * vue des invitations
     */
    private String user;
    private int idjeu;
    private String idjoueur1;
    private String idjoueur2;
    private Jeu j;

    /**
     * constructeur ActionEnvoyer
     */
    ActionJouer(String user,Jeu jeu){
        this.user=user;
        this.idjeu=jeu.getId();
        this.idjoueur1=idjoueur1;
        this.idjoueur2=idjoueur2;
        this.j = jeu;
        jeu.creerJar();


    }
    /**
     * Actions à effectuer lors du clic sur le bouton envoyer
     * * Il faut donc: Inserer une invitation dans la Base de Donnée, mettre à jour l'affichage
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Button b= (Button) actionEvent.getSource ();
        try{
            System.out.println(GLOBALS.REQUETE_BD.isAdversaire(this.idjeu,this.user));
            if(GLOBALS.REQUETE_BD.isAdversaire(this.idjeu,this.user)){
                String adversaire=GLOBALS.REQUETE_BD.chercherAdversaire(idjeu,this.user);
                int numP= GLOBALS.REQUETE_BD.maxParticiper()+1;
                ParticiperBD participerBD=new ParticiperBD(this.user,adversaire,numP,new Date());
                GLOBALS.REQUETE_BD.insererParticiper(participerBD);
                InvitationBD i= new InvitationBD(adversaire,"",idjeu,GLOBALS.REQUETE_BD.dateInvitation(this.idjeu, adversaire,""));
                GLOBALS.REQUETE_BD.supprimer(i);


                ChargeurJeu chargeur = new ChargeurJeu("src/jar/");
                Jeux ma= chargeur.chargerJeu(j.getNom()+".jar", j.getNom());
                ArrayList<String> res=GLOBALS.REQUETE_BD.getParticipantParNumPart(numP);
                ma.creerPartie(this.idjeu,res.get(0),res.get(1),this.user);
            }
            else{
                try {
                    GLOBALS.REQUETE_BD.insererInvitation(new InvitationBD(user,"",idjeu,new Date()));
                } catch (ExceptionInvitation exceptionInvitation) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Attention !");
                    al.setContentText("Vous êtes déjà en recherche d'un utilisateur");
                    al.showAndWait();

                }
            }


        }


        catch(SQLException e) {
//			//.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
