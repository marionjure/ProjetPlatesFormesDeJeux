package src.FourInARow;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import src.application.Couple;
import src.application.GLOBALS;

import java.sql.SQLException;
import java.util.ArrayList;

public class Matrice {

    private Integer nbLigne;
    private Integer nbColonne;
    private ArrayList<ArrayList<Node>> matrice;
    private FourInARow four;
//    private int cpt;
//    private boolean trouve;

    public Matrice(Integer nbColonne, Integer nbLigne, FourInARow four) {
        this.four = four;
//        this.cpt = 0;
//        this.trouve = false;

        this.nbColonne = nbColonne;
        this.nbLigne = nbLigne;
        this.matrice = new ArrayList<ArrayList<Node>>();
        for (int i = 0; i < this.nbLigne; i++) {
            ArrayList<Node> ligne = new ArrayList<Node>();
            for (int j = 0; j < this.nbColonne; j++) {
                ligne.add(null);
            }
            this.matrice.add(ligne);
        }
    }

    /**
     * Permet d'obtenir la cellule en fonction d'une ligne et d'une colonne
     *
     * @param posLigne,   Integer la position de la ligne
     * @param posColonne, Integer la position de la colonne
     * @return une cellule
     */
    public Node getCell(Integer posLigne, Integer posColonne) {
        return this.matrice.get(posLigne).get(posColonne);
    }

    /**
     * Permet de modifier une cellule en fonction d'une ligne, d'une colonne et d'une cellule
     *
     * @param posLigne,   Integer la position de la ligne
     * @param posColonne, Integer la position de la colonne
     * @param n,          Node la cellule
     */
    public void setCell(Integer posLigne, Integer posColonne, Node n) {
        this.matrice.get(posLigne).set(posColonne, n);
    }

    /**
     * Permet d'obtenir le nombre de colonne présent dans le Puissance 4
     *
     * @return Integer, le nombre de colonne
     */
    public Integer getNbColonne() {
        return nbColonne;
    }

    /**
     * Permet d'obtenir le nombre de ligne présent dans le Puissance 4
     *
     * @return Integer, le nombre de ligne
     */
    public Integer getNbLigne() {
        return nbLigne;
    }

    /**
     * Permet d'obtenir le nombre de colonne d'une cellule
     *
     * @param n Node, une cellule
     * @return Integer, le numéro de la colonne
     */
    public Integer getColonne(Node n) {
        for (int i = 1; i < this.nbLigne; i++) {
            for (int j = 0; j < this.nbColonne; j++) {
                if (n.equals(this.getCell(i, j))) {
                    return j;
                }
            }

        }
        return 0; // a voir si on ne lève pas une exception
    }

    /**
     * Fonction qui permet d'avoir la liste de toutes les cases d'une colonne
     *
     * @param numCol Integer, le numéro d'une colonne
     * @return ArrayList<Node>, une liste de toutes les cellules d'une colonne
     */
    public ArrayList<Node> getColonne(Integer numCol) {
        ArrayList<Node> col = new ArrayList<Node>();
        for (int i = 1; i < this.nbLigne; i++) {
            col.add(this.matrice.get(i).get(numCol));
        }
        return col;
    }

    /**
     * Fonction permettant d'afficher une alerte quand l'utilisateur gagne
     */
    public boolean gagne() {
        boolean res = false;
        boolean abandon = false;
        try {
            if (GLOBALS.REQUETE_BD.partieEncour(four.getNumPartie())) {
                for (int i = 1; i < this.nbLigne; i++) {
                    for (int j = 0; j < this.nbColonne; j++) {
                        if (!(this.getCell(i, j).getId().equals("Empty") || this.getCell(i, j).getId().equals("EmptyDark"))) {
                            // horizontal
                            if (i + 3 < this.getNbColonne() && sameColor(this.getCell(i, j).getId(), this.getCell(i + 1, j).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i + 2, j).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i + 3, j).getId())) {
                                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -1);

                                this.four.getPartie().setVainqueurPartie(this.four.getIdJoueur1().getUser().getPseudo());

                                res = true;
                                four.close();
                                break;
                            }
                            // vertical
                            if (j + 3 < this.getNbLigne() && sameColor(this.getCell(i, j).getId(), this.getCell(i, j + 1).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i, j + 2).getId()) && sameColor(this.getCell(i, j).getId(), (this.getCell(i, j + 3).getId()))) {
                                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -1);

                                this.four.getPartie().setVainqueurPartie(this.four.getIdJoueur1().getUser().getPseudo());

                                res = true;
                                four.close();
                                break;
                            }
                            //diagonal gauche
                            if (i + 3 < this.getNbColonne() && j + 3 < this.getNbLigne() && sameColor(this.getCell(i, j).getId(), this.getCell(i + 1, j + 1).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i + 2, j + 2).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i + 3, j + 3).getId())) {
                                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -1);

                                this.four.getPartie().setVainqueurPartie(this.four.getIdJoueur1().getUser().getPseudo());
                                res = true;

                                break;
                            }
                            //diagonal droite
                            if (i - 3 > 0 && j + 3 < this.getNbLigne() && sameColor(this.getCell(i, j).getId(), this.getCell(i - 1, j + 1).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i - 2, j + 2).getId()) && sameColor(this.getCell(i, j).getId(), this.getCell(i - 3, j + 3).getId())) {
                                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -1);
                                this.four.getPartie().setVainqueurPartie(this.four.getIdJoueur1().getUser().getPseudo());

                                res = true;
                                break;
                            }
                        }
                    }
                }
            } else {
                res = true;
                abandon = GLOBALS.REQUETE_BD.abandonPartie(four.getNumPartie());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        if (this.isEgalite()) {
//            Alert a = new Alert(Alert.AlertType.INFORMATION, "Draw");
//            a.setHeaderText("The Platfrom is full of token.\n ");
//            a.setContentText("Click OK to go back to menu");
//            a.show();
//            four.close();
//        }
        if (res) {
            if (this.four.getPlayerTurn() || abandon) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Win");

                a.setHeaderText("You've won! Congratulations");
                a.setContentText("Click OK to go back to menu");
                a.show();
                //trouve = true;
            } else {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Loose");
                a.setHeaderText("You've lost!");
                a.setContentText("Click OK to go back to menu");
                a.show();
                //trouve = true;
            }
            four.close();
        }
        return res;
    }

    /**
     * Permet de savoir si id1 et id2 ont la même couleur
     *
     * @param id1 String, l'ID d'une cellule
     * @param id2 String, l'ID d'une cellule
     * @return boolean
     */
    public boolean sameColor(String id1, String id2) {
        return id1.replaceAll("Dark", "").equals(id2.replaceAll("Dark", ""));
    }

    /**
     * méthode qui permet de mettre dans la matrice le pon pion, de la bonne couleur,
     * à la bonne position.
     *
     * @param numCol, le numéro de la colonne où l'on veut
     *                placer le pion
     * @return un booléen, qui nous dit si on a jouer ou pas
     */
    public boolean jouer(int numCol) {
        ImageView img;
        boolean succes = false;
        if (this.four.getJoueurLocal().equals(this.four.getIdJoueur1().getElem2())) {
            if (this.four.getPlayerTurn()) {
                img = this.four.getGrid().genererImage("./img/tokenYDark.png", "YellowDark");
            } else {
                img = this.four.getGrid().genererImage("./img/tokenR.png", "Red");
            }
        } else {
            if (this.four.getPlayerTurn()) {
                img = this.four.getGrid().genererImage("./img/tokenRDark.png", "RedDark");
            } else {
                img = this.four.getGrid().genererImage("./img/tokenY.png", "Yellow");
            }
        }
        for (int i = getNbLigne() - 1; i > 0; i--) {
            if (getCell(i, numCol).getId().equals("Empty") || getCell(i, numCol).getId().equals("EmptyDark")) {
                //this.cpt++;
                setCell(i, numCol, img);
                four.setCoordonnees(new Couple(i, numCol));
                succes = true;
                four.getGrid().majAffichage();
                break;
            }
        }
        return succes;
    }

//    public boolean isEgalite() {
//        boolean trouve = false;
//        if (this.cpt == getNbLigne() * getNbColonne() - getNbColonne()) {
//            trouve = true;
//            try {
//                this.four.jouerCoup(GLOBALS.REQUETE_BD.maxIdPartie() + 1, this.four.getIdJoueur1().getId() + "", -3);
//                System.out.println("INSERT DANS LA BD EGALITE");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return trouve;
//    }
}