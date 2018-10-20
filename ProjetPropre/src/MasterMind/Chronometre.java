package src.MasterMind;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Permet de gérer un label associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Label{
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ActionTemps actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    Chronometre(){
        super("0:0:0");
        this.actionTemps = new ActionTemps(this);
        this.keyFrame = new KeyFrame(Duration.millis(100),this.actionTemps);
        this.timeline = new Timeline(keyFrame);
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Permet au controleur de mettre à jour le label
     * la durée est affichée sous la forme h:m:s
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec){
        long tpsSec = tempsMillisec/1000;
        long heure = tpsSec /3600;
        long min = (tpsSec%3600)/60;
        long sec = tpsSec%60;
        this.setText(heure+":"+min+":"+sec);
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start(){
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop(){
        timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime(){
        this.actionTemps.reset();

    }
}
