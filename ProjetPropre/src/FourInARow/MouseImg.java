package src.FourInARow;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import src.application.Couple;

public class MouseImg implements EventHandler<MouseEvent> {

    private ImageView img;
    private Grid grid;
    private FourInARow p4;
    private Couple coordonnees;
    private int i;


    public MouseImg(ImageView im, Grid grid, FourInARow fur) {
        this.img = im;
        this.grid = grid;
        this.p4 = fur;
        this.coordonnees = new Couple(0, 0);
        this.i = 0;


    }

    /**
     * permet de savoir si la souris est sur une imageView
     *
     * @param mouseEvent l'action du clique de souris
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            // Dans le cas où le joueur local est en train de jouer
            if (p4.getPlayerTurn()) {
                int colonneClique = grid.getMatrice().getColonne(img);
                // il joue
                if (grid.getMatrice().jouer(colonneClique)) {
                    if (!grid.getMatrice().gagne()) {
                        p4.jouerCoup(p4.getNumPartie(), p4.getIdLocal().getId() + "", 0);
                        if (p4.getWait() != null) {
                            p4.getWait().stop();
                        }
                        p4.disableEcran(true);
                        p4.setPlayerTurn();
                        p4.attendre();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "WHAT ARE YOU DOING ????");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            }
        }
    }
}
