package src.application;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Génère la vue de la liste de ses invitations
 * le choix ici est d'un faire un héritié d'un HBox
 */
public class listeInvitation extends VBox {
	/**
	  * constructeur listeInvitation
	  * @param user le joueur
	  //* @param idjeu id  du jeu
	  */
    listeInvitation(Jeu j,String user,int type){
        super();
        ScrollPane scroll=new ScrollPane();
        VBox panel =new VBox();
        ArrayList<InvitationBD> liste=null;
        String texte="";
        try{
			//Affiche le jeu
            this.getChildren().add(new Label("Les invitations pour "+GLOBALS.REQUETE_BD.nomJeu(j.getId())));
			//Création de la liste de ses invitations
            if(type==0){
                liste= GLOBALS.REQUETE_BD.InvitationTout(user);
                texte=" vous invites à jouer pour ajouter le jeu";
            }
            else{
                texte=" vous invites à jouer";
			liste= GLOBALS.REQUETE_BD.Invitation(j.getId(),user);}}

         catch(SQLException e) {
//			//.printStackTrace();
    }
		//Chaque invitation est mis dans InvitationHBox
        for(int i=0;i<liste.size();i++){
            ActionInvitation actionValider=new ActionInvitation(null,liste.get(i),user,j);
            panel.getChildren().add(new InvitationHBox(liste.get(i).getUser(),actionValider ,texte));
        }
        panel.setSpacing(10);
        //this.milieu = new ScrollPane();
        scroll.setContent(panel);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-padding: 15;");
        scroll.setVvalue(1);
       this.getChildren().add(scroll);


    }
}
