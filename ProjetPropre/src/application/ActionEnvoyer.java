package src.application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.util.Duration;
import src.Exceptions.ExceptionInvitation;
import src.Interfaces.Jeux;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 * Controleur du bouton envoyer
 */
public class ActionEnvoyer implements EventHandler<ActionEvent> {
		/**
		 * vue des invitations
		 */
        private Invitation invitation;
        /**
		 * modele des invitations
		 */
        private InvitationBD invitationBD;
        /**
		 * id du jeu
		 */
        private int idjeu;
        private Timeline timeLine;
		 /**
		  * constructeur ActionEnvoyer
		 * @param invitation vue des invitations
		 * @param invitationBD modele des invitations
		 * @param idjeu id du jeu
		 */
        ActionEnvoyer(Invitation invitation,InvitationBD invitationBD,int idjeu){
            this.invitation=invitation;
            this.invitationBD=invitationBD;
            this.idjeu=idjeu;

        }
        /**
         * Actions à effectuer lors du clic sur le bouton envoyer
         * * Il faut donc: Inserer une invitation dans la Base de Donnée, mettre à jour l'affichage
         * @param actionEvent l'événement
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            Button b= (Button) actionEvent.getSource ();
            Alert al =new Alert(Alert.AlertType.ERROR);
            try{
                //le joueur selectionné par le user
                User user=(User) this.invitation.getListe().getSelection();
                String nomUser = user.getPseudo();
                //Changer la date et le USE_PSEUDOUSER en user
                this.invitation.getInvitationBD().setUSE_PSEUDOUSER(nomUser);
                this.invitation.getInvitationBD().setDate(new Date());
                System.out.println(this.invitationBD);
                if(!GLOBALS.REQUETE_BD.isInsererInvitation(this.invitationBD)){
                    //Inserer dans la Base de Donnée
                    try {
                        GLOBALS.REQUETE_BD.insererInvitation(this.invitation.getInvitationBD());

                        timeLine = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                if(GLOBALS.REQUETE_BD.invitationExistante(invitation.getInvitationBD())){

                                    Alert a = new Alert(Alert.AlertType.INFORMATION,"Partie acceptée");
                                    a.setHeaderText(null);
                                    a.setContentText("Le joueur "+invitation.getInvitationBD().getUSE_PSEUDOUSER()+ " à accepté votre invitation pour jouer à "+ GLOBALS.REQUETE_BD.getJeu(invitation.getInvitationBD().getIdjeu()).getNom());
                                    a.show();
                                    timeLine.stop();
                                }
                            }
                        }));
                        timeLine.setCycleCount(Animation.INDEFINITE);
                        timeLine.play();
                    } catch (ExceptionInvitation exceptionInvitation) {
                        exceptionInvitation.printStackTrace();
                    }

                }
                else{
                    al.setTitle("Attention !!!");
                    al.setHeaderText("Vous avez déjà envoyer une invitation à "+nomUser);
                    al.showAndWait();
                }
                //Mettre à jour l'affichage
                this.invitation.getListe().setSelection(null);
                this.invitation.getAffiche().setText("Choisi un(e) ami(e) pour l'inviter à jouer au "+GLOBALS.REQUETE_BD.nomJeu(this.idjeu));


            }


            catch(SQLException e) {
//       //.printStackTrace();
            }



        }
    }


