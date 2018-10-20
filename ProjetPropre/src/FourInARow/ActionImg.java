package src.FourInARow;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ActionImg implements EventHandler<MouseEvent> {

    private ImageView img;
    private Grid grid;
    private boolean over;

    public ActionImg(ImageView im, Grid grid) {
        this.img = im;
        this.grid = grid;
        this.over = false;
    }

    /**
     * permet de savoir si la souris est sur une imageView
     *
     * @param actionEvent
     */
    @Override
    public void handle(MouseEvent actionEvent) {
        if (!over) {
            Integer numCol = this.grid.getMatrice().getColonne(this.img);
            this.grid.greyColumn(numCol, false);
            over = !over;// permet de detecter la souris sur les imgViews même
            // si on est déjà passé dessus
        }
    }
}


