package src.application.widget;


import javafx.scene.control.Label;
import src.Interfaces.ElementDeListe;

public class LabelObject extends Label {
    private ElementDeListe objet;

    public LabelObject(ElementDeListe elem){
        super(elem.getTexteAffichage());
        objet = elem;
    }

    public ElementDeListe getObjet(){
        return objet;
    }
}
