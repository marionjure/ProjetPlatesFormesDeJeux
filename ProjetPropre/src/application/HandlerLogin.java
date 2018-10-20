package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import src.Exceptions.JoueurDejaExistantException;
import src.Exceptions.JoueurPasTrouverException;

public class HandlerLogin implements EventHandler<ActionEvent> {
    private Login logVue;

    /**
     * gère le conneixion et création de compte
     * @param l
     */
    public HandlerLogin(Login l){
        logVue=l;
    }

    public void popUp(String titre, String content){
        Alert a = new Alert(Alert.AlertType.INFORMATION,content);
        a.setHeaderText(null);
        a.setTitle(titre);
        a.showAndWait();
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        switch (logVue.getPartieUtilise()){
            case Login.PARTIE_LOGIN:
                if (logVue.getId().equals("") || logVue.getPass().equals("")){ // on met une erreur si on essaye de se connecter sans identifiants
                    popUp("Champ(s) non remplis", "Veuiller renseigner les champ login et mot de passe pour pouvoir vous connecter");
                }else{
                    try {
                        User u = GLOBALS.REQUETE_BD.getJoueur(logVue.getId(),logVue.getPass());
                        if(u.isActif()) {
                            new MainActivity(u);
                            logVue.close();
                        }else{
                            popUp("Banni", "Vous êtes bannis de la plateforme vous ne pouveez pas vous connecter");
                        }
                    } catch (JoueurPasTrouverException e) {
                        popUp("Erreur d'authentification","Les informations que vous avez renseigner n'ont pas permie de vous identifier");
                    }
                }
                break;

            case Login.PARTIE_NOUVEAU_COMPTE:
                if(logVue.getId().equals("") || logVue.getPass().equals("") || logVue.getMail().equals("") || logVue.getPassConfirm().equals("")){
                    popUp("Champ(s) non remplis", "Veuiller renseigner tout les champs pour pouvoir continuer");
                }else{
                    if(logVue.getPassConfirm().equals(logVue.getPass())) {
                        if (!logVue.getId().contains("@")) {
                            User u = new User(logVue.getId(), logVue.getPass(), logVue.getMail(), User.ROLE_USER, 1);
                            try {
                                GLOBALS.REQUETE_BD.addJoueur(u);
                                new MainActivity(u);
                                logVue.close();
                            } catch (JoueurDejaExistantException e) {
                                popUp("Compte Existant", "Ce pseudo ou cette adresse email sont déjà utiliser par un autre utilisateur veuiller les changer");
                            }
                        }else{
                            popUp("Charactère invalide", "Le charactère '@' n'est pas permis dans un pseudo.");
                        }
                    }else{
                        popUp("Validation incorrecte", "Le mot de passe et sa confirmation sont différents");
                    }
                }
                break;

        }
    }
}