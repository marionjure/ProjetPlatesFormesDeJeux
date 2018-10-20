package src.application;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import src.Interfaces.Jeux;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class actionTab implements EventHandler<Event> {
    public static boolean init=true;
    private User u;


    public actionTab(User u){
        this.u=u;


    }
    public void handle(Event actionEvent) {
                TabSpecial t = (TabSpecial) actionEvent.getSource();
                Jeu j = GLOBALS.REQUETE_BD.getJeu(GLOBALS.REQUETE_BD.getIdJeu(t.getNumPartie()));
                j.creerJar();
                ChargeurJeu chargeur = new ChargeurJeu("src/jar/");
                Jeux ma = null;
                try {
                    ma = chargeur.chargerJeu(j.getNom() + ".jar", j.getNom());
                    ArrayList<String> liste = GLOBALS.REQUETE_BD.getParticipantParNumPart(t.getNumPartie());
                    ma.reprendrePartie(GLOBALS.REQUETE_BD.getIdJeu(t.getNumPartie()), liste.get(0), liste.get(1), this.u.getPseudo(), t.getNumPartie());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

    }}

