package src.application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Profil extends HBox {

    private MainActivity mainActivity;
    private User u;
    private TextField exMdp,newMdp,verifMdp;
    private File iconeFile;
    private ImageView imageViewIcon;

    /**
     * Crée la page de profil d'un utilisateur
     * @param u
     * @param mainActivity
     */
    public Profil(User u,MainActivity mainActivity){

        this.mainActivity=mainActivity;
        this.u=u;
        BorderPane cont=new BorderPane();
        cont.setLeft(this.Gauche());
        cont.setRight(this.Centre());
        cont.setBottom((this.Bas()));
        getChildren().addAll(cont);
       // this.setAlignment(Pos.CENTER);
        this.setMinWidth(GLOBALS.MAINACTIVITY_WIDTH);

    }


    private VBox Gauche(){
        Label titre = new Label("Vos amis");
        titre.setFont(new Font(GLOBALS.TITLE_SIZE));
        VBox gauche = new VBox(titre,new DemandeInvitation(u.getPseudo()));
        gauche.setPadding(new Insets(10,200,0,10));
        return gauche;
    }

    /**
     * Crée la partie relative a l'affichage de lutilisateur
     * @return HBox
     */
    private HBox Centre(){

        VBox centre = new VBox();

        Label modifProfil = new Label("Modification du Profil");
        modifProfil.setPadding(new Insets(10,0,10,0));
        modifProfil.setFont(Font.font(20));
        modifProfil.setAlignment(Pos.CENTER);

        HBox partieModif = new HBox();

        VBox partieLabel = new VBox();

        Label labelPseudo=new Label("Pseudo");
        Label labelExMDP=new Label("Ancien mot de passe");
        Label labelNewMDP=new Label("Nouveau mot de passe");
        Label labelVerifMDP=new Label("Confirmation du mot de passe");

        partieLabel.getChildren().addAll(labelPseudo,labelExMDP,labelNewMDP,labelVerifMDP);
        partieLabel.setSpacing(30);
        partieLabel.setPadding(new Insets(10,0,0,20));

        VBox partieText = new VBox();

        Label textPseudo = new Label(u.getPseudo());
        this.exMdp = new PasswordField();
        this.newMdp = new PasswordField();
        this.verifMdp = new PasswordField();

        partieText.getChildren().addAll(textPseudo,this.exMdp,this.newMdp,this.verifMdp);
        partieText.setSpacing(20);
        partieText.setPadding(new Insets(10,0,0,20));

        partieModif.getChildren().addAll(partieLabel,partieText);

        centre.getChildren().addAll(modifProfil,partieModif);
        centre.setPadding(new Insets(5,5,5,5));

        Image icone;
        this.u.loadIcon();
        if(u.getIcone()!=null) {
            icone = new Image(new ByteArrayInputStream(u.getIcone()));
        }else{
            icone = new Image(GLOBALS.LOGO_ICON);
        }
        imageViewIcon = new ImageView(icone);
        imageViewIcon.setFitWidth(100);
        imageViewIcon.setFitHeight(100);

        FileChooser selectionFichier = new FileChooser();
        selectionFichier.setTitle("Selectionner votre icone");
        selectionFichier.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichier image","png","jpg","PNG","JPG"));
        imageViewIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                iconeFile = selectionFichier.showOpenDialog(null);
                System.out.println(iconeFile);
                imageViewIcon.setImage(new Image(iconeFile.toURI().toString()));
                try {
                    u.setIcone(Files.readAllBytes(iconeFile.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        return new HBox(centre,imageViewIcon);
    }

    /**
     * Génère l'icone de validation
     * @return VBox
     */
    private VBox Bas(){
        VBox bas = new VBox();
        Image imageValider= new Image(GLOBALS.VALIDER2_ICON);
        ImageView imageViewValider=new ImageView();
        imageViewValider.setFitHeight(30);
        imageViewValider.setFitWidth(30);
        imageViewValider.setImage(imageValider);
        Button valider = new Button();
        valider.setGraphic(imageViewValider);




        valider.setPrefSize(20,20);
        valider.setId("Valider");
        System.out.println(this.newMdp);
        valider.setOnAction(new ActionBoutonValiderBD(this.u, this.mainActivity,this.exMdp,this.newMdp,this.verifMdp,this));
        bas.setAlignment(Pos.CENTER_RIGHT);
        bas.setPadding(new Insets(10,10,10,0));
        bas.getChildren().addAll(valider);
        return bas;
    }

    /**
     * Renvoit le fichier icone que l'utilisateru a séléctionner
     * @return File
     */
    public File getIconeSelect(){
        return iconeFile;
    }
}
