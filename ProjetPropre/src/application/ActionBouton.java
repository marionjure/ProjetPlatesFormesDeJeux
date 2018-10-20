package src.application;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;



public class ActionBouton implements EventHandler<ActionEvent>{


    private MainActivity mainActivity;
    private User u;

    public ActionBouton(User u, MainActivity mainActivity){
        this.mainActivity=mainActivity;
        this.u=u;
    }

    public void handle(ActionEvent e){
        Button b = (Button)e.getTarget();
        if(b.getId().endsWith("Deconnexion")){
            deconnexion();
        }
        if(b.getId().endsWith("Jouer"))
            jouer();

        else if(b.getId().endsWith("Parties"))
            parties();

        else if(b.getId().endsWith("Retour"))
            jouer();
        else if(b.getId().endsWith("Profil"))
            profil();
        else if(b.getId().endsWith("Refuser"))
            jouer();
    }

    public void deconnexion(){
        Login login = new Login();
        try {
            login.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mainActivity.close();
    }

    public void jouer(){
        actionTab.init=true;
        mainActivity.changeJoueur(null);
//        mainActivity.setRoot(new Joueur(u,mainActivity));
//        System.out.println("J'ai appuyé sur jouer");
    }

    public void parties(){
        mainActivity.changeJoueur(new Parties(u,mainActivity));
    }
    public void profil(){
        mainActivity.changeJoueur(new Profil(u,mainActivity));
    }

}

