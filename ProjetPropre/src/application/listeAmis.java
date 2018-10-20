package src.application;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class listeAmis extends VBox {
    /**
     * constructeur listeInvitation
     * @param user le joueur

     */
    listeAmis(String user){
        super();
        ScrollPane scroll=new ScrollPane();
        VBox panel =new VBox();
        ArrayList<AmisBD> liste=null;

        try {
            //Affiche le jeu
            //this.getChildren().add(new Label("Les invitations pour " + GLOBALS.REQUETE_BD.nomJeu(idJeu)));
            //Création de la liste de ses invitations
            liste = GLOBALS.REQUETE_BD.listeAmie(user);
        }


        catch(SQLException e) {
//			//.printStackTrace();
        }
        //Chaque invitation est mis dans InvitationHBox
        for(int i=0;i<liste.size();i++){
            AmisBD amisBD= new AmisBD(user,liste.get(i).getPSEUDOUSER(),1,new Date());
            AmisBD amisBD1= new AmisBD(liste.get(i).getPSEUDOUSER(),user,1,new Date());
            ActionAccepter actionValider=new ActionAccepter(amisBD,amisBD1);
            panel.getChildren().add(new AmisHBox(liste.get(i).getPSEUDOUSER(),actionValider));
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
