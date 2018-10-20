package src.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import src.Interfaces.IonChangeEvent;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Administrateur extends VBox implements IonChangeEvent {

    private final static int PARTIE_USER = 1, PARTIE_JEU = 2;
    private boolean gaucheSelected;
    private Label titreJoueur,titreJeu, chemin;
    private Liste liJeux,liJoueur;
    private VBox partieJoueur, partieJeux;
    private HBox partieConfirmation;
    private GridPane main;
    private CheckBox bannisementJoueur,activationJeu;
    private ComboBox<String> grade;
    private ComboBox<String> typeJeu;
    private TextField nomJeu;
    private TextArea descJeu;
    private int partieCourrante;
    private Button btnModifierJeu,btnModifierJoueur,btnAjouter,btnSupprimer, btnChoisirFichier;
    private FileChooser selectionFichier;
    private File fichier;
    private MainActivity mainActivity;
    private User u;


    public Administrateur(User u,MainActivity mainActivity){
        super();

        this.u=u;
        this.mainActivity=mainActivity;
        gaucheSelected=true;

        genererAdmin();
        activationPartie(true, false);


    }

    public GridPane genererAdmin(){
        main = new GridPane();
        main.setPadding(new Insets(5));
        main.setGridLinesVisible(false);
        main.setMinWidth(GLOBALS.MAINACTIVITY_WIDTH);
        main.setAlignment(Pos.CENTER);
        main.setHgap(10);
        main.setVgap(10);


        liJoueur = new Liste(Liste.TYPE_LISTEJOUEUR,true);
        liJoueur.setPrefSize(GLOBALS.ADMIN_CENTER_WIDHT,GLOBALS.ADMIN_TAB_HEIGHT);
        liJoueur.setOnChangeEvent(this);
        liJeux = new Liste(Liste.TYPE_LISTEJEUX,true);
        liJeux.setPrefSize(GLOBALS.ADMIN_CENTER_WIDHT,GLOBALS.ADMIN_TAB_HEIGHT);
        liJeux.setOnChangeEvent(this);

        partieJoueur = partieGauche();
        partieJeux = partieDroite();

        main.add( partieJoueur, 0, 0);
        main.add(liJoueur,1,0);
        main.add(partieJeux,2,0);

        partieConfirmation = partieBas();

        this.getChildren().addAll(main,partieConfirmation);

        return main;

    }



    private void addInfoBulle(Control c, String info){
        Tooltip infoBulle = new Tooltip(info);
        c.setTooltip(infoBulle);
    }


    public VBox partieGauche(){
        VBox res = new VBox();
        res.setPrefSize(GLOBALS.ADMIN_BORDER_WHIDHT,GLOBALS.ADMIN_TAB_HEIGHT);
        res.setAlignment(Pos.TOP_CENTER);
        res.setSpacing(GLOBALS.SPACING_VALUE);

        titreJoueur = new Label("Aucune sélection");
        titreJoueur.setFont(Font.font(GLOBALS.TITLE_SIZE));
        titreJoueur.setPadding(new Insets(30));


        Label bannisementLabel = new Label("Banni ");
        bannisementJoueur = new CheckBox();
        bannisementJoueur.setSelected(false);
        HBox ban = new HBox();
        ban.getChildren().addAll(bannisementLabel,bannisementJoueur);
        ban.setSpacing(GLOBALS.SPACING_VALUE);

        grade = new ComboBox<>();
        grade.getItems().addAll("Utilisateur normal","Administrateur");
        Label gradeLab = new Label("Grade ");
        HBox gradeEtLab = new HBox();
        gradeEtLab.getChildren().addAll(gradeLab,grade);


        res.getChildren().addAll(titreJoueur, ban,gradeEtLab);

        res.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");

        res.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                activationPartie(true, false);
            }
        });
        return res;
    }

    public VBox partieDroite(){
        VBox res = new VBox();
        res.setPrefSize(GLOBALS.ADMIN_BORDER_WHIDHT,GLOBALS.ADMIN_TAB_HEIGHT);
        res.setAlignment(Pos.TOP_CENTER);
        res.setSpacing(GLOBALS.SPACING_VALUE);

        titreJeu = new Label("Aucune sélection");
        titreJeu.setFont(new Font(GLOBALS.TITLE_SIZE));
        titreJeu.setPadding(new Insets(30));

        nomJeu = new TextField();
        nomJeu.setPromptText("Nom du jeu");
        Label labNomJeu = new Label("Titre du jeu ");
        HBox champNom= new HBox(labNomJeu,nomJeu);
        champNom.setSpacing(GLOBALS.SPACING_VALUE);

        Label labFile = new Label("Fichier executable ");
        btnChoisirFichier = new Button("Choisir un fichier");
        chemin = new Label();
        HBox div = new HBox(btnChoisirFichier,chemin);
        selectionFichier = new FileChooser();
        selectionFichier.setTitle("Selectionner le fichier executable");
        selectionFichier.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichier executable","jar","JAR"));
        btnChoisirFichier.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fichier = selectionFichier.showOpenDialog(null);
                chemin.setText(fichier.getPath());
            }
        });
        HBox champFile = new HBox(labFile,div);
        champFile.setSpacing(GLOBALS.SPACING_VALUE);

        Label labType = new Label("Type de jeu ");
        typeJeu = new ComboBox<>();
        typeJeu.getItems().addAll("Temps","Tour à tour","Points");
        HBox champType = new HBox(labType,typeJeu);
        champType.setSpacing(GLOBALS.SPACING_VALUE);

        Label labDesc = new Label("Description ");
        descJeu = new TextArea();
        descJeu.setPromptText("Comment jouer au jeu : ");
        descJeu.setPrefWidth(GLOBALS.ADMIN_BORDER_WHIDHT-175);
        descJeu.setMinWidth(GLOBALS.ADMIN_BORDER_WHIDHT-175);
        descJeu.setMaxWidth(GLOBALS.ADMIN_BORDER_WHIDHT-175);
        HBox champDesc = new HBox(labDesc,descJeu);
        champDesc.setSpacing(GLOBALS.SPACING_VALUE);

        activationJeu = new CheckBox();
        Label labelActiv = new Label("Visible ");
        HBox champActiv = new HBox(labelActiv,activationJeu);
        champActiv.setSpacing(GLOBALS.SPACING_VALUE);

        res.getChildren().addAll(titreJeu,champNom,champFile,champType, champDesc, champActiv);
        res.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;");
        res.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                activationPartie(false, true);
            }
        });
        return res;
    }

    public HBox partieBas(){

        //TODO ajouter les handler pour les buttons
        btnAjouter = new Button("Ajouter Jeu");
        btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newJeu();
            }
        });
        btnModifierJeu = new Button("Modifier le jeu");
        btnModifierJeu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateJeu();
            }
        });
        btnSupprimer = new Button("Supprimer Jeu");
        btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteJeu();
            }
        });
        btnModifierJoueur = new Button("Modifier le joueur");
        btnModifierJoueur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                updateJoueur();
            }
        });



        HBox res = new HBox();
        res.setSpacing(GLOBALS.SPACING_VALUE);
        res.setAlignment(Pos.CENTER);

        return res;
    }

    public void activationPartie(boolean gauche, boolean droite){
        String styleRed = "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: red;";
        String styleBlue = "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: blue;";
        if(!gauche){
            for(Node n : partieJoueur.getChildren()){n.setDisable(true);}
            partieJoueur.setStyle(styleRed);

        }else{
            for(Node n : partieJoueur.getChildren()){n.setDisable(false);}
            partieCourrante=PARTIE_USER;
            partieJoueur.setStyle(styleBlue);
            if(main.getChildren().contains(liJeux)) {
                main.getChildren().remove(liJeux);
                main.add(liJoueur, 1, 0);
            }
        }
        if(!droite){
            for(Node n : partieJeux.getChildren()){n.setDisable(true);}
            partieJeux.setStyle(styleRed);
        }else{
            for(Node n : partieJeux.getChildren()){n.setDisable(false);}
            partieCourrante=PARTIE_JEU;
            partieJeux.setStyle(styleBlue);
            if(main.getChildren().contains(liJoueur)) {
                main.getChildren().remove(liJoueur);
                main.add(liJeux, 1, 0);
            }
        }
        majConfirmation();
    }

    public void majConfirmation(){
        partieConfirmation.getChildren().clear();
        if(partieCourrante==PARTIE_USER){
            partieConfirmation.getChildren().add(btnModifierJoueur);
        }else if(partieCourrante==PARTIE_JEU){
            if(liJeux.getSelection()!=null) {
                partieConfirmation.getChildren().addAll(btnModifierJeu, btnSupprimer);
            }else{
                partieConfirmation.getChildren().addAll(btnAjouter);
            }
        }
    }

    public void majInfobulle(){
    }


    @Override
    public void onChangeAction() {
        switch (partieCourrante){
            case PARTIE_USER:
                if(this.liJoueur.getSelection()!=null) {
                    User u = (User) this.liJoueur.getSelection();
                    majPartUser(u);
                }
                break;
            case PARTIE_JEU:
                if(this.liJeux.getSelection()!=null) {
                    Jeu j = (Jeu) this.liJeux.getSelection();
                    majPartJeu(j);
                }
                break;

        }
        majConfirmation();
    }

    /**
     * afficher les information relative a un jeu
     * @param j
     */
    private void majPartJeu(Jeu j) {
        this.titreJeu.setText("Modifier le jeu "+ j.getNom());
        this.nomJeu.setText(j.getNom());
        this.descJeu.setText(j.getDescription());
        this.fichier = j.getFichierFile();
        this.chemin.setText(fichier.getPath());
        this.activationJeu.setSelected(j.isJeuActif());
        this.typeJeu.getSelectionModel().select(j.getType().equals(Jeu.TYPE_POINT)? "Points":(j.getType().equals(Jeu.TYPE_TOURATOUR)?"Tour à tour":"Temps"));
    }

    /**
     * afficher les info relative a un utilisateur
     * @param u
     */
    private void majPartUser(User u) {
        this.titreJoueur.setText("Modifier l'utilisateur "+u.getPseudo());
        this.bannisementJoueur.setSelected(!u.isActif());
        this.grade.getSelectionModel().select(u.isAdmin() ? "Administrateur":"Utilisateur normal");
    }

    /**
     * met a jour le jouer tel qu'il a été modifier par l'admin
     */
    private void updateJoueur(){
        if(liJoueur.getSelection()!=null) {
            User u = (User) liJoueur.getSelection();
            u.setRole(this.grade.getSelectionModel().getSelectedItem().equals("Administrateur") ? User.ROLE_ADMIN : User.ROLE_USER);
            u.setActif(!this.bannisementJoueur.isSelected());
            u.majDansBd();
            Alert ab = new Alert(Alert.AlertType.INFORMATION, "Les modifications ont bien été prise en compte");
            ab.setHeaderText(null);
            ab.showAndWait();
        }
    }

    private void deleteJeu(){
        if(liJeux.getSelection()!=null) {
            GLOBALS.REQUETE_BD.deleteJeu((Jeu) liJeux.getSelection());
            liJeux.setSelection(null);
            Alert ab = new Alert(Alert.AlertType.INFORMATION, "La suppression du jeu a été opérée");
            ab.setHeaderText(null);
            ab.showAndWait();
        }
        liJeux.remplir();
    }

    private void  updateJeu(){
        if(liJeux.getSelection()!=null){
            boolean ok = true;
            Jeu j = (Jeu)liJeux.getSelection();
            j.setDescription(this.descJeu.getText());
            try {
                j.setFichier(Files.readAllBytes(fichier.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            j.setJeuActif(this.activationJeu.isSelected());
            if(this.nomJeu.getText().length()>0) {
                j.setNom(this.nomJeu.getText());
            }else {
                ok=false;
                Alert ab = new Alert(Alert.AlertType.WARNING, "La modification ne peut pas être effectuée tant que le nom est vide");
                ab.setHeaderText(null);
                ab.showAndWait();
            }

            j.setType(this.typeJeu.getSelectionModel().getSelectedItem().equals("Temps") ? Jeu.TYPE_TEMPS:(this.typeJeu.getSelectionModel().getSelectedItem().equals("Points") ?Jeu.TYPE_POINT:Jeu.TYPE_TOURATOUR));

           if(ok){
               j.majDansBd();
               Alert ab = new Alert(Alert.AlertType.WARNING, "La mise a jour a été effectuée");
               ab.setHeaderText(null);
               ab.showAndWait();
           }
        }
    }

    private void newJeu(){
        if(liJeux.getSelection()==null){
            try {
                if(nomJeu.getText().equals("") || descJeu.getText().equals("") || fichier.length()==0 || this.typeJeu.getSelectionModel().getSelectedItem().equals("")){
                    Alert ab = new Alert(Alert.AlertType.WARNING, "Vous ne pouvez pas créer de jeu sans remplir tout les champs");
                    ab.setHeaderText(null);
                    ab.showAndWait();
                }else{
                    Jeu j =new Jeu(GLOBALS.REQUETE_BD.getMaxNumJeu()+1,this.nomJeu.getText(),this.descJeu.getText(),Jeu.TYPE_INCONNU,Files.readAllBytes(fichier.toPath()),this.activationJeu.isSelected());
                    j.setType(this.typeJeu.getSelectionModel().getSelectedItem().equals("Temps") ? Jeu.TYPE_TEMPS:(this.typeJeu.getSelectionModel().getSelectedItem().equals("Points") ?Jeu.TYPE_POINT:Jeu.TYPE_TOURATOUR));
                    GLOBALS.REQUETE_BD.addJeu(j);
                    Alert ab = new Alert(Alert.AlertType.WARNING, "Le jeu a été ajouté");
                    ab.setHeaderText(null);
                    ab.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            liJeux.remplir();
        }
    }

}
