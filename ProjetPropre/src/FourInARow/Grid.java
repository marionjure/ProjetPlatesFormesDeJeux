package src.FourInARow;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import src.application.GLOBALS;

import java.io.File;

public class Grid {

    private Matrice matrice;
    private GridPane grid;
    private int coupJouer;
    private FourInARow p4;

    /**
     * Nous permet de lancer un grid en l'appelant
     */
    public Grid(FourInARow fur) {
        System.out.println("grid créer");
        this.grid = new GridPane();
        this.p4 = fur;
        this.matrice = new Matrice(7, 7, this.p4);

//        File imgEm = new File("./img/tokenE.png");
//        File imgEmptyD = new File("./img/tokenEDark.png");
//        File imgRed = new File("./img/tokenR.png");
//        File imgRedD = new File("./img/tokenRDark.png");
//        File imgYellow = new File("./img/tokenY.png");
//        File imgYellowD = new File("./img/tokenYDark.png");

        this.placerImage();
        this.majAffichage();
        //this.greyColumn(0,false);
    }

    /**
     * Permet de placer les images au début de la partie avec que des images vides
     * et de placer les labels qui sont sur la première ligne.
     */
    public void placerImage() {
        String colonne = "ABCDEFG";
        for (int i = 0; i < colonne.length(); i++) {
            Label lb = new Label(colonne.charAt(i) + ""); // une lettre par case du gridpane
            lb.setFont(new Font(50)); // on augmente la taille des label
            this.matrice.setCell(0, i, lb);
        }
        for (int i = 1; i < this.matrice.getNbLigne(); i++) {
            for (int j = 0; j < this.matrice.getNbColonne(); j++) {
                this.matrice.setCell(i, j, genererImage("./img/tokenE.png", "Empty"));
            }
        }
    }

    /**
     * Permet de vider le grid pane et de le reconstruire pas la suite.
     */
    public void majAffichage() {
        this.grid.getChildren().clear(); // vide tout le gridpane
        for (int i = 0; i < this.matrice.getNbLigne(); i++) {
            for (int j = 0; j < this.matrice.getNbColonne(); j++) {
                Node n = this.matrice.getCell(i, j); // recupère les imgViews de la matrice
                this.grid.setHalignment(n, HPos.CENTER); // place les labels au centre des cases
                this.grid.add(this.matrice.getCell(i, j), j, i);
            }
        }
    }

    /**
     * Permet de retourner le GridPane
     *
     * @return un gridpane
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Permet degriser ou de grisé une colonne en foction de la position de la souris
     *
     * @param numCol,  int
     * @param degrise, boolean pour savoir si la case est grisé ou non
     */
    public void greyColumn(int numCol, boolean degrise) {
        this.grisage(numCol, degrise);
        for (int i = 0; i < this.matrice.getNbColonne(); i++) {
            if (i != numCol) {
                this.grisage(i, true);
            }
        }
        majAffichage();
    }

    /**
     * Regarde si la souris est sur une ImgView qui a un Id definis à l'avance et remplace l'imgview
     * par un imageviewDark
     *
     * @param numCol,  int
     * @param degrise, boolean pour savoir si la case est grisé ou non
     */
    public void grisage(Integer numCol, boolean degrise) {
        for (int i = 1; i < this.matrice.getNbLigne(); i++) {
            // IF pour les images EMPTY
            if (this.matrice.getCell(i, numCol).getId().equals("Empty")) {
                if (!degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenEDark.png", "EmptyDark"));
                }
            }
            if (this.matrice.getCell(i, numCol).getId().equals("EmptyDark")) {
                if (degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenE.png", "Empty"));
                }
            }
            // IF pour les images RED
            if (this.matrice.getCell(i, numCol).getId().equals("Red")) {
                if (!degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenRDark.png", "RedDark"));
                }
            }
            if (this.matrice.getCell(i, numCol).getId().equals("RedDark")) {
                if (degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenR.png", "Red"));
                }
            }
            // IF pour les images YELLOW
            if (this.matrice.getCell(i, numCol).getId().equals("Yellow")) {
                if (!degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenYDark.png", "YellowDark"));
                }
            }
            if (this.matrice.getCell(i, numCol).getId().equals("YellowDark")) {
                if (degrise) {
                    this.matrice.setCell(i, numCol, genererImage("./img/tokenY.png", "Yellow"));
                }
            }
        }
    }

    /**
     * Permet de generer des imageViews
     *
     * @param urlImage, l'URL de l'image
     * @param nomImage, le nom de l'image
     * @return des ImageViews
     */
    public ImageView genererImage(String urlImage, String nomImage) {
        File imgEmpty = new File(urlImage);
        Image img = new Image(imgEmpty.toURI().toString());
        ImageView imgE = new ImageView(img);
        imgE.setFitHeight(0.155 * GLOBALS.MAINACTIVITY_HEIGHT);
        imgE.setFitWidth(0.095 * GLOBALS.MAINACTIVITY_WIDTH);
        imgE.setId(nomImage);
        imgE.setOnMouseEntered(new ActionImg(imgE, this));
        imgE.setOnMouseClicked(new MouseImg(imgE, this, this.p4));
        return imgE;
    }

    /**
     * @return une matrice
     */
    public Matrice getMatrice() {
        return matrice;
    }

    /**
     * Permet de modifier le coupJouer
     *
     * @param coupJouer, un int
     */
    public void setCoupJouer(int coupJouer) {
        this.coupJouer = coupJouer;
    }

    /**
     * Permet d'obtenir le coup joué
     *
     * @return, int
     */
    public int getCoupJouer() {
        return coupJouer;
    }
}
