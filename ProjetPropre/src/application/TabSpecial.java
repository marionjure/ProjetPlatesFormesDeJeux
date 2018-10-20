package src.application;

import javafx.scene.control.Tab;
import src.Interfaces.IonSelectedEvent;


public class TabSpecial extends Tab{

    private int numPartie;
    private actionTab action;


    public TabSpecial(int numPartie, actionTab action) {
        super();
        this.numPartie = numPartie;
//        this.interfaceActu = interfaceActu;
        this.action=action;
    }

    public int getNumPartie(){
        return this.numPartie;
    }

    public void setNumPartie(int newNumPartie){
        this.numPartie=newNumPartie;
    }
}

