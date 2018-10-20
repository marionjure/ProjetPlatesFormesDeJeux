package src.FourInARow;
import src.Exceptions.JoueurPasTrouverException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import src.application.GLOBALS;
import src.application.Jeu;
import src.application.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestJoueur1 extends Application {

    private ArrayList<Image> liste;
    private ArrayList<ImageView> listeImageV;
    private int val;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(GLOBALS.MAINACTIVITY_TITLE);
        primaryStage.setScene(Test());
        primaryStage.show();
    }

    public Scene Test() {
        GridPane gp = new GridPane();
       /* Label lb = new Label("Bonjour");
        TextField tf = new TextField();
        Button bt = new Button("salut");
        Hyperlink hp = new Hyperlink("Je ne sais pas pk ça ne fonctionne pas");
        gp.add(lb, 0, 0);
        gp.add(tf, 0, 1);
        gp.add(bt, 1, 0);
        gp.add(hp, 1, 1);
        HBox hb = new HBox();
        gp.getChildren().set(1, hb);
        System.out.println(gp.getChildren());*/

        Button pd = new Button("jouer P4");
        gp.add(pd, 100, 100);

        //System.out.println(GLOBALS.REQUETE_BD.getPartieEnCours(8));
        try {
            this.val = GLOBALS.REQUETE_BD.maxIdPartieJeu() + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("test val : " + val);
        pd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    User user1 = GLOBALS.REQUETE_BD.getJoueur("Donald", "mdp3");
                    User user2 = GLOBALS.REQUETE_BD.getJoueur("iute", "mdp2");

                    try {
                        GLOBALS.REQUETE_BD.insertPseudoPartie(user1, user2, val);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Jeu j = new Jeu(1, "p4", "description", "point", null, 1);
                    FourInARow p4 = new FourInARow();

                    p4.creerPartie(j.getId(), user1.getPseudo(), user2.getPseudo(), user1);

                } catch (JoueurPasTrouverException pj) {
                    System.out.println("pas de joueur");
                }
            }
        });


        return new Scene(gp, GLOBALS.MAINACTIVITY_WIDTH, GLOBALS.MAINACTIVITY_HEIGHT);
    }
}
