package src.application;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import src.Exceptions.JoueurPasTrouverException;


import java.sql.SQLException;

/**
 * Génère la vue l'envoie d'une invitation et la liste de ses invitations
 * le choix ici est d'un faire un héritié d'un HBox
 */
public class Invitation extends HBox {
	/**
     * le destinataire de l'invitation
     */
    private Label affiche;
    /**
	 * modele des invitations
	 */
    private InvitationBD invitationBD;
    /**
	 * la liste de ses amis
	*/
    private Liste liste;

    /**
	  * constructeur Invitation
	  * @param user le joueur
	  * //@param idjeu id  du jeu
	  */
    Invitation(String user,Jeu j,int type){
        if(j!=null){
        this.getChildren().add(envoyer(user,j.getId()));
        this.getChildren().add(recevoir(user,j,type));
        this.setSpacing(30);}
    }

    void start(String user,Jeu j,int type){
        this.getChildren().clear();
        if(j!=null){
            this.getChildren().add(envoyer(user,j.getId()));
            this.getChildren().add(recevoir(user,j,type));
            this.setSpacing(30);}
    }
	/**
	 * @return le destinataire de l'invitation
	 */
    public Label getAffiche() {
        return affiche;
    }
	/**
	 * @return le modéle
	 */
    public InvitationBD getInvitationBD() {
        return invitationBD;
    }
	/**
	 * @return la liste d'amis
	 */
    public Liste getListe() {
        return liste;
    }
	/**
	 * la vue l'envoie d'une invitation
	 * @param user le joueur
	 * @param idjeu id  du jeu
	 * @return la vue l'envoie d'une invitation
	 */
    private VBox envoyer(String user, int idjeu){
        VBox res = new VBox();
        this.invitationBD=new InvitationBD(user,null ,idjeu,null);
        //Création du bouton envoyer
        ActionEnvoyer actionE= new ActionEnvoyer(this,this.invitationBD,idjeu);
        Button envoyer =new Button("Envoyer");
        envoyer.getStyleClass().add("abandon");
		envoyer.setOnAction(actionE);
        try{
			//Création du label affiche
            this.affiche= new Label("Choisi un(e) ami(e) pour l'inviter à jouer au "+GLOBALS.REQUETE_BD.nomJeu(idjeu));
			res.getChildren().add(this.affiche);
            //Création de la liste d'amis
            this.liste=new Liste(Liste.TYPE_LISTEAMIS,false,GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(user));
            
        }
        catch(JoueurPasTrouverException e) {
//			//.printStackTrace();
        }
        catch(SQLException e) {
//			//.printStackTrace();
        }
        //Ajout au panel
		res.getChildren().add(this.liste);
        res.getChildren().add(envoyer);
        res.setSpacing(10);
        //Mettre à jour la label en fonction de la selection
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                User user =(User)getListe().getSelection();
                if (user != null) {
                    String nomUser = user.getPseudo();
                    affiche.setText("voulez-vous vraiment envoyer une invitation à " + nomUser);
                }
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
        return res;

    }
	/**
	 * la vue de ses invitations
	 * @param user le joueur
	 * //@param idjeu id  du jeu
	 * @return la vue de  ses invitations
	 */
    private HBox recevoir(String user,Jeu j,int type){
        HBox res = new HBox();
        //Création de la liste de ses invitation tout les secondes
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                res.getChildren().clear();
                //res.getChildren().add(new listeInvitation(idjeu,user));
                res.getChildren().add(new listeInvitation(j,user,type));
            }
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();


        return res;

    }

}

