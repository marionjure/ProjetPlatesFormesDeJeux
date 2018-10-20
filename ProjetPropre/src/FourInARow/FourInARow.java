package src.FourInARow;
import src.Exceptions.JoueurPasTrouverException;
import src.Interfaces.Jeux;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.application.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class FourInARow extends Application implements Jeux {
    private Grid grid;
    private Button button;
    private boolean playerTurn;
    private int idJeu;
    private Couple<Integer, User> idJoueur1, idJoueur2, idLocal;
    private User joueur1, joueur2, joueurLocal;
    private Stage primaryStage;
    private Date date;
    private Partie partie;
    private Couple coordonnees;
    private boolean fini = false;
    private int numPartie;
    private Timeline wait;
    private boolean attendre;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * permet de lancer une fenêtre
     *
     * @param primaryStage, c'est la fenêtre qui s'ouvre
     */

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(GLOBALS.MAINACTIVITY_TITLE);
        this.primaryStage.setScene(fourInARow());
        this.primaryStage.setResizable(false);
        if (this.joueurLocal.equals(this.joueur2) && attendre) {
            this.disableEcran(true);
            this.attendre();
        }
        this.primaryStage.show();
    }

    /**
     * Cette fonction nous permet de créer la scene principale du puissance 4 en y mettant
     * des border pour ne pas ce perdre dans les Panes.
     *
     * @return la Scene du Puissance 4
     */
    public Scene fourInARow() {
        this.grid = new Grid(this);
        this.playerTurn = this.joueurLocal.equals(this.joueur1);
        BorderPane bp = new BorderPane();
        //bp.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        bp.setCenter(this.grid.getGrid());
        bp.setRight(right());
        Tchat chat = new Tchat(this.joueurLocal, this.joueur2.equals(joueurLocal) ? joueur1 : joueur2);
        chat.setAlignment(Pos.CENTER);
        chat.setPadding(new Insets(5));
        bp.setLeft(chat);
        bp.getStyleClass().add("jeux");
        bp.getStylesheets().add(new File("style/style.css").toURI().toString());

        return new Scene(bp, 1294, 800);
    }

    public Timeline getWait() {
        return wait;
    }

    /**
     * Créer le côté droite de l'IHM du puissance 4
     *
     * @return vb : une VBox
     */
    public VBox right() {
        VBox vb = new VBox();
        vb.setSpacing(GLOBALS.SPACING_VALUE);
        vb.setAlignment(Pos.CENTER);
        //vb.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        HBox hb3 = new HBox();

        Label token = new Label("Your token is :");

        File imgEmpty = new File("./img/Red.png");
        Image img = new Image(imgEmpty.toURI().toString());
        ImageView imgR = new ImageView(img);

        vb.setPadding(new Insets(5));

        hb1.setMaxWidth(200);
        hb2.setMaxWidth(200);
        hb3.setMaxWidth(200);

        String adversaire = this.joueur1.getPseudo();
        if (joueurLocal.equals(joueur1)) {
            adversaire = this.joueur2.getPseudo();
        }
        Label against = new Label("You play against :\n" + adversaire);
        hb1.getChildren().add(against);
        hb1.setStyle("-fx-border-style: solid inside;" + "-fx-padding : 10;"); // sert à mettre du CSS dan du JAVA

        Label choose = new Label("Choose a column\nwhere to insert a token");
        hb3.getChildren().add(choose);

        if (!this.playerTurn) {
            Label turn = new Label("His turn to play");
            choose.setTextFill(Color.web("#CECECE"));
            hb2.getChildren().add(turn);
            hb3.setStyle("-fx-border-color: grey;" + "-fx-padding : 10;");
        } else {
            Label turn = new Label("Your turn to play");
            hb2.getChildren().add(turn);
            hb3.setStyle("-fx-border-style: solid inside;" + "-fx-padding : 10;");
        }

        hb2.setStyle("-fx-border-style: solid inside;" + "-fx-padding : 10;");

        this.button = new Button("Give up");
        this.button.getStyleClass().add("abandon");
        this.button.setOnAction(new ButtonAction(this));

        vb.getChildren().addAll(token, imgR, hb1, hb2, hb3, this.button);
        return vb;
    }

    @Override
    /**
     * Permet à un joueur de lancer le jeu en retrouvant l'état actuel de la partie
     * Permet de voir ce que l'adversaire à fait
     * @param idPartie est l'indentifiant de la partie dans la base de donnée
     * @param numJoueur est le numéro du joueur de la partie (1 ou 2)
     * @param partage permet de donner au jeu l'accès à des variables du module joueur
     * @return donne le composant graphique dans lequel s'affiche le jeu
     */
    public void jouerCoup(int idPartie, String numJoueur, Object partage) {
        int val = (partage == null ? 0 : ((int) partage));
        if (!fini) {
            try {
                this.partie = new Partie(this.idJeu, this.numPartie, this.date, GLOBALS.REQUETE_BD.numetapePartie3(this.joueur1.getPseudo(), this.joueur2.getPseudo()) + 1, this.transfCouple(this.coordonnees), this.joueurLocal.getPseudo(), partage == null ? 0 : ((int) partage), partage == null ? "NULL" : (((int) partage == -2) ? (this.joueurLocal.equals(this.joueur1) ? joueur2.getPseudo() : joueur1.getPseudo()) : (this.joueurLocal.equals(this.joueur1) ? joueur1.getPseudo() : joueur2.getPseudo())));
                GLOBALS.REQUETE_BD.insererPartie(this.partie);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (val == -1 || val == -2)
            this.fini = true;
    }


//    public void jouerCoup(int numPartie, int numJoueur, Object partage) throws SQLException {
//        System.out.println("je rentre dans jouer coup");
//        int val = (partage == null ? 0 : ((int) partage));
//        if (!fini) {
//            this.partie = new Partie(this.idJeu, this.numPartie, this.date, GLOBALS.REQUETE_BD.numetapePartie3(this.joueur1.getPseudo(), this.joueur2.getPseudo()) + 1, this.transfCouple(this.coordonnees), this.joueurLocal.getPseudo(), partage == null ? 0 : ((int) partage), partage == null ? "NULL" : (((int) partage == -2) ? (this.joueurLocal.equals(this.joueur1) ? joueur2.getPseudo() : joueur1.getPseudo()) : (this.joueurLocal.equals(this.joueur1) ? joueur1.getPseudo() : joueur2.getPseudo())));
//            System.out.println("ID PARTIE DANS JOUERCOUP : " + partie.getIdPartie());
//            GLOBALS.REQUETE_BD.insererPartie(this.partie);
//        }
//
//        if (val == -1 || val == -2)
//            this.fini = true;
//    }

    @Override
    /**
     * Permet de demander à créer une nouvelle partie et de la lancer
     * @param idJeu est l'identifiant du jeu dans la base de données
     * @param pseudoJoueur1 identifiant du premier joueur
     * @param pseudoJoueur2 identifiant du second joueur
     * @param partage permet de donner au jeu l'accès à des variables du module joueur
     * @return donne le composant graphique dans lequel s'affiche le jeu
     */


    public void creerPartie(int idJeu, String pseudoJoueur1, String pseudoJoueur2, Object partage) {
        this.idJeu = idJeu;
        this.date = new Date();
        try {
            this.joueur1 = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(pseudoJoueur1);
            this.idJoueur1 = new Couple(1, this.joueur1);
            this.joueur2 = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(pseudoJoueur2);
            this.idJoueur2 = new Couple(2, this.joueur2);
            try {
                this.numPartie = GLOBALS.REQUETE_BD.numPartie(joueur1.getPseudo(), joueur2.getPseudo());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("numPartie p4 : " + numPartie);
        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
        }
        try {
            this.joueurLocal = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail((String) partage);
        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
        }
        if (joueurLocal.equals(this.joueur2)) {
            this.idLocal = this.idJoueur2;
            playerTurn = false;
        } else {
            this.idLocal = this.idJoueur1;
            playerTurn = true;
        }
        this.attendre = true;
        this.start(new Stage());
    }

    /**
     * retourne un booleen qui nous indique le tour
     *
     * @return playerTurn, un booléen
     */
    public boolean getPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * modifie le booléen qui nous dit le joueur qui doit jouer
     */
    public void setPlayerTurn() {
        this.playerTurn = !this.playerTurn;
    }

    /**
     * nous permet de fermer la fenêtre
     */
    public void close() {
        this.primaryStage.close();
    }

    /**
     * nous permet de savoir qui est le JoueurLocal
     *
     * @return un User, qui est le joueur local
     */
    public User getJoueurLocal() {
        return joueurLocal;
    }

    /**
     * nous permet de savoir l'id du joueur 1
     *
     * @return un couple qui contient un id et un joueur
     */
    public Couple getIdJoueur1() {
        return idJoueur1;
    }

    /**
     * nous renvoie la partie
     *
     * @return une partie
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * nous permet de récupérer les cooredonnées où le joueur a placé
     * son pion.
     *
     * @param coordonnees, un couple qui contient le numéro de ligne et de colonne
     */
    public void setCoordonnees(Couple coordonnees) {
        this.coordonnees = coordonnees;
    }

    /**
     * nous permet de transformé des coordonnées en les
     * mettant dans un format plus comprehensible
     *
     * @param couple, un couple de coordonnées
     * @return un String
     */
    public String transfCouple(Couple couple) {
        String res = "";
        res += "L" + couple.getPosLig() + "/C" + couple.getPosCol();
        return res;
    }

    public boolean coupAJouer() {
        Couple<String, String> couple = GLOBALS.REQUETE_BD.getCoupPrecP4(this.numPartie);
        return !this.joueurLocal.getPseudo().equals(couple.getElem1());
    }

    /**
     * nous permet d'enlevé la possibilité, à un joueur de jouer
     *
     * @param bool, un booléen
     */
    public void disableEcran(boolean bool) {
        for (Node img : this.grid.getGrid().getChildren()) {
            img.setDisable(bool);
        }
    }

    /**
     * nous permet de récupérer la grid
     *
     * @return un grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * méthode qui permet de faire attendre le joueur qui ne doit pas jouer
     * et qui permet de faire apparaître le coup jouer par l'adversaire sur l'écran
     */
    public void attendre() {
        this.wait = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Couple<String, String> couple = GLOBALS.REQUETE_BD.getCoupPrecP4(numPartie);
                String pseudoDernierJoueur = couple.getElem1();

                if (!pseudoDernierJoueur.equals(joueurLocal.getPseudo()) && !pseudoDernierJoueur.equals("")) {
                    int colonne = extraitColonneFromRes(couple.getElem2());
                    grid.getMatrice().jouer(colonne);
                    grid.getMatrice().gagne();
                    setPlayerTurn();
                    disableEcran(false);
                    wait.stop();
                }
            }
        }));
        this.wait.setCycleCount(Animation.INDEFINITE);
        this.wait.play();
    }

    /**
     * Nous permet de récupérer une colonne d'un pion qui a été placé sur le
     * plateau, par la Base de Données
     *
     * @param str, le String présent dans la Base de Données
     * @return un int
     */
    public int extraitColonneFromRes(String str) {
        String[] decomposition = str.split("/");
        int numCol = Integer.parseInt(decomposition[1].substring(1));
        return numCol;

    }

    public int extraitLigneFromRes(String str) {
        String[] decomposition = str.split("/");
        int numCol = Integer.parseInt(decomposition[0].substring(1));
        return numCol;
    }

    /**
     * Nous permet de recupérer le numéro de la Partie
     *
     * @return un int
     */
    public int getNumPartie() {
        return numPartie;
    }

    /**
     * Nous permet de récupérer l'ID du joueur Local
     *
     * @return Un Couple
     */
    public Couple getIdLocal() {
        return idLocal;
    }


    /**
     * Nous permet de reprendre une partie qui à déjà été commencé
     *
     * @param idJeu     , un int
     * @param idJoueur1 , un String
     * @param idJoueur2 , un String
     * @param partage   , Object
     * @param numPartie , un int
     */
    public void reprendrePartie(int idJeu, String idJoueur1, String idJoueur2, Object partage, int numPartie) {
        this.numPartie = numPartie;

        this.idJeu = idJeu;
        ArrayList<String> listeDeCoup = GLOBALS.REQUETE_BD.getPartieEnCours(numPartie);


        try {
            Couple<String, String> lesUsers = GLOBALS.REQUETE_BD.getPseudoPartieEnCours(numPartie);
            this.joueur1 = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(lesUsers.getElem1());
            this.idJoueur1 = new Couple(1, this.joueur1);
            this.joueur2 = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(lesUsers.getElem2());
            this.idJoueur2 = new Couple(2, this.joueur2);
            this.joueurLocal = GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail((String) partage);
            this.date = GLOBALS.REQUETE_BD.getDateEnCours(numPartie);
            if (joueurLocal.equals(this.joueur2)) {
                this.idLocal = this.idJoueur2;
                playerTurn = false;
            } else {
                this.idLocal = this.idJoueur1;
                playerTurn = true;
            }
            try {
                GLOBALS.REQUETE_BD.partie(numPartie, GLOBALS.REQUETE_BD.getIDJeu(numPartie), joueurLocal.getPseudo());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
        }
        this.attendre = false;
        this.start(new Stage());
        this.primaryStage.setTitle(joueurLocal.getPseudo());
        for (int i = 0; i < listeDeCoup.size(); i++) {
            grid.getMatrice().jouer(extraitColonneFromRes(listeDeCoup.get(i)));
            this.setPlayerTurn();
        }

        Couple<String, String> cp = GLOBALS.REQUETE_BD.getCoupPrecP4(this.numPartie);

        disableEcran(!this.getPlayerTurn());

        if (cp.getElem1().equals(joueurLocal.getPseudo())) {
            attendre();
        }
    }
}
