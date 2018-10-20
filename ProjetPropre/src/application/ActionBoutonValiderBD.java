package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class ActionBoutonValiderBD  implements EventHandler<ActionEvent> {


        private MainActivity mainActivity;
        private User u;
        private File f;
        private TextField exMdp,newMdp,verifMdp;
        private Profil p;

    /**
     * Handler du boutton de modification d'utilisateur dans la bd
     * @param u
     * @param mainActivity
     * @param exMdp
     * @param newMdp
     * @param verifMdp
     * @param p
     */
        public ActionBoutonValiderBD(User u, MainActivity mainActivity, TextField exMdp, TextField newMdp, TextField verifMdp, Profil p){
            this.mainActivity=mainActivity;
            this.u=u;
            this.exMdp=exMdp;
            this.newMdp=newMdp;
            System.out.println(this.newMdp);
            this.verifMdp=verifMdp;
            this.p = p;
        }

    /**
     * démare la validation
     * @param e
     */
    public void handle(ActionEvent e){
                valider();
        }


    /**
     * Met a jour l'utilisateur dans la base de donnée et affiche des
     * erreurs si les nouvelle information ne sont pas valides
     */
    public void valider(){
            if(p.getIconeSelect()!=null){
                try {
                    u.setIcone(Files.readAllBytes(p.getIconeSelect().toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                u.majDansBdPourIcone();
                alert("Maj effectuée", "Votre icone a été mise a jour");
            }

            if(exMdp.getText().equals(u.getMdp())){
                if(newMdp.getText().equals("")){
                    alert("Mot de passe obligatoire", "Vous ne pouvez pas supprimer votre mot de passe vous devez obligatoirement en avoir un");
                }else{
                    if(newMdp.getText().equals(verifMdp.getText())){
                        u.setMdp(newMdp.getText());
                        u.majDansBd();
                        alert("Succès", "Modification de votre mot de passe réussie");
                    }else{
                        alert("Confirmation invalide", "Le nouveau mot de passe et sa confirmation ne correspondent pas");
                    }
                }
            }else if(!exMdp.getText().equals("")){
                alert("Erreur", "Votre mot de passe actuel et celui fourni ne correspondent pas");
            }


        }

    /**
     * Affiche un message l'écran
     * @param titre
     * @param contenu
     */
    public void alert(String titre, String contenu){
            Alert a = new Alert(Alert.AlertType.INFORMATION,titre);
            a.setHeaderText(null);
            a.setContentText(contenu);
            a.showAndWait();
        }
}
