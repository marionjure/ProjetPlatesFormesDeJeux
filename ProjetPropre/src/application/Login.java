package src.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {
    public static final int PARTIE_LOGIN = 0, PARTIE_NOUVEAU_COMPTE=1;
    private VBox root;
    private VBox zone_login,zone_nouveauCompte;
    private HBox zone_validation;
    private TextField txt_id,txt_pass,txt_newpseudo,txt_newemail,txt_newpass,txt_newpassconfirm;
    private Button btn_valider;
    private int partieUtilise;
    private Stage stage;

    public static void main(String[] args){
        launch(args);
    }

    /**
     * démarage de l'application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene laScene = new Scene(genPageConnexion());
        primaryStage.setScene(laScene);
        primaryStage.setWidth(GLOBALS.LOGIN_WIDTH);
        primaryStage.setHeight(GLOBALS.LOGIN_HEIGHT);
        primaryStage.setTitle("Duel sur la Toile");
        this.stage = primaryStage;
        this.stage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Génère les champs relatif a la partie de login et la partie création de compte
     * @return le root de du stage
     */
    public VBox genPageConnexion(){
        root = new VBox();
        HBox content = new HBox();

        Rectangle trait = new Rectangle(2,(int)(GLOBALS.LOGIN_CONTENT_HEIGHT*0.8));
        VBox separateur = new VBox(trait);
        separateur.setPrefHeight(GLOBALS.LOGIN_CONTENT_HEIGHT);
        separateur.setAlignment(Pos.CENTER);

        content.getChildren().addAll(partieLogin(),separateur,portieNouveauCompte());

        zone_login.setOnMouseEntered(new EventHandler<MouseEvent>() { //on grise la partie création compte quand on passe sur la connexion
            @Override
            public void handle(MouseEvent event) {
                partieActive(PARTIE_LOGIN);
                btn_valider.setText("Connexion");
            }
        });
        zone_nouveauCompte.setOnMouseEntered(new EventHandler<MouseEvent>() { // on grise la partie connexion quand on passe sur la partie création de compte
            @Override
            public void handle(MouseEvent event) {
                partieActive(PARTIE_NOUVEAU_COMPTE);
                btn_valider.setText("Créer un compte");
            }
        });

        content.setSpacing(GLOBALS.SPACING_VALUE);
        content.setAlignment(Pos.CENTER);
        content.setPrefWidth(GLOBALS.LOGIN_WIDTH);
        content.setPrefHeight(GLOBALS.LOGIN_CONTENT_HEIGHT);
        root.getChildren().addAll(content,partieValidation());
        root.setSpacing(GLOBALS.SPACING_VALUE);
        content.setAlignment(Pos.CENTER);
        content.setPrefWidth(GLOBALS.LOGIN_WIDTH);
        root.getStylesheets().add("style/style.css");
        root.getStyleClass().add("jeux");
        return root;
    }


    /**
     * Génère les champs relatif a la partie de login
     * @return la box de login
     */
    public VBox partieLogin(){
        zone_login = new VBox();
        GridPane content = new GridPane();

        Label tire_Login = new Label("Se connecter");
        tire_Login.setFont(Font.font(GLOBALS.TITLE_SIZE));

        Label lab_id = new Label("Pseudo/Email ");
        txt_id = new TextField();
        txt_id.setPromptText("NOM D'UTILISATEUR");
        content.add(lab_id, 0,0 );
        content.add(txt_id, 1,0 );

        Label lab_pass = new Label("Mot de passe ");
        txt_pass = new PasswordField();
        txt_pass.setPromptText("MOT DE PASSE");
        content.add(lab_pass, 0,1 );
        content.add(txt_pass, 1,1 );

        Hyperlink lien = new Hyperlink("Mot de passe oublié");
        lien.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                motPasseOublier();
            }
        });
        content.add(lien,0,2 );

        content.setVgap(GLOBALS.SPACING_VALUE);
        content.setHgap(GLOBALS.SPACING_VALUE);

        zone_login.getChildren().addAll(tire_Login,content);

        zone_login.setAlignment(Pos.TOP_CENTER);
        zone_login.setSpacing(GLOBALS.SPACING_VALUE);
        zone_login.setPrefWidth((GLOBALS.LOGIN_WIDTH/2)-1);
        zone_login.setPrefHeight(GLOBALS.LOGIN_CONTENT_HEIGHT);
        zone_login.setPadding(GLOBALS.PADDING_VALUE);

        return zone_login;
    }

    /**
     * Génère les champs relatif a la partie de création de compte
     * @return la box de création de compte
     */
    public VBox portieNouveauCompte(){
        zone_nouveauCompte = new VBox();
        GridPane content = new GridPane();

        Label tire_Account = new Label("Créer un compte");
        tire_Account.setFont(Font.font(GLOBALS.TITLE_SIZE));


        Label lab_pseudo = new Label("Pseudo  ");
        txt_newpseudo = new TextField();
        txt_newpseudo.setPromptText("NOM D'UTILISATEUR");
        content.add(lab_pseudo, 0,0 );
        content.add(txt_newpseudo, 1,0 );

        Label lab_email = new Label("Addresse email  ");
        txt_newemail = new TextField();
        txt_newemail.setPromptText("EMAIL");
        content.add(lab_email, 0,1 );
        content.add(txt_newemail, 1,1 );

        Label lab_pass = new Label("Mot de passe  ");
        txt_newpass = new PasswordField();
        txt_newpass.setPromptText("MOT DE PASSE");
        content.add(lab_pass, 0,2 );
        content.add(txt_newpass, 1,2 );

        Label lab_newpass = new Label("Confirmation ");
        txt_newpassconfirm = new PasswordField();
        txt_newpassconfirm.setPromptText("CONFIRMATION MOT DE PASSE");
        content.add(lab_newpass, 0,3 );
        content.add(txt_newpassconfirm, 1,3 );

        content.setVgap(GLOBALS.SPACING_VALUE);
        content.setHgap(GLOBALS.SPACING_VALUE);

        zone_nouveauCompte.getChildren().addAll(tire_Account,content);
        zone_nouveauCompte.setAlignment(Pos.TOP_CENTER);
        zone_nouveauCompte.setSpacing(GLOBALS.SPACING_VALUE);
        zone_nouveauCompte.setPrefWidth((GLOBALS.LOGIN_WIDTH/2)-1);
        zone_nouveauCompte.setPrefHeight(GLOBALS.LOGIN_CONTENT_HEIGHT);
        zone_nouveauCompte.setPadding(GLOBALS.PADDING_VALUE);

        return zone_nouveauCompte;
    }

    /**
     * Génère les champs relatif a la partie de validation
     * @return la box de validation
     */
    public HBox partieValidation(){
        zone_validation = new HBox();
        zone_validation.setPadding(new Insets(30));
        btn_valider = new Button("Connexion");
        btn_valider.getStyleClass().add("abandon");

        btn_valider.setOnAction(new HandlerLogin(this));

        zone_validation.getChildren().addAll(btn_valider);

        zone_validation.setPrefWidth(GLOBALS.LOGIN_WIDTH);
        zone_validation.setMinHeight(GLOBALS.LOGIN_SUBMIT_HEIGHT);
        zone_validation.setSpacing(GLOBALS.SPACING_VALUE);
        zone_validation.setAlignment(Pos.CENTER);

        return zone_validation;
    }

    /**
     * permet de griser la partie non utilisée par l'utilisateur et de décriser l'autre
     * @param partie qui a pour valeur PARTIE_LOGIN ou PARTIE_NOUVEAU_COMPTE
     */
    public void partieActive(int partie){
        switch (partie){
            case PARTIE_LOGIN:
                griserPartie(zone_nouveauCompte, true);
                griserPartie(zone_login,false);
                partieUtilise=PARTIE_LOGIN;
                btn_valider.setText("Connexion");
                break;
            case PARTIE_NOUVEAU_COMPTE:
                griserPartie(zone_login,true );
                griserPartie(zone_nouveauCompte,false );
                partieUtilise=PARTIE_NOUVEAU_COMPTE;
                btn_valider.setText("Créer le compte");
                break;
        }
    }

    /**
     * grise le élément d'une box
     * @param p
     * @param actif
     */
    private void griserPartie(Pane p, boolean actif){
        for(Node n : p.getChildren())
            n.setDisable(actif);
    }

    /**
     * Envoy du mail quand mot de pass oublier
     */
    public void motPasseOublier(){
        if(!txt_id.getText().equals("")){
            //TODO verif que pseudo existe
            String mailUser = "MailDeLutilisateur@email.mail";
            Mail.Mail(Mail.EMAIL_PROJET_ADMIN,"Password recovery", "L'utilisateur "+ txt_id.getText() + " à dit avoir oublier son mot de passe, son mail est "+mailUser);
            Alert info = new Alert(Alert.AlertType.INFORMATION,"Un mail a été envoyer a un des administrateur du systeme.");
            info.setHeaderText(null);
            info.showAndWait();
        }
    }

    public String getId(){
        if(partieUtilise==PARTIE_LOGIN)
            return this.txt_id.getText();
        else
            return this.txt_newpseudo.getText();
    }

    public String getPass(){
        if(partieUtilise==PARTIE_LOGIN)
            return this.txt_pass.getText();
        else
            return this.txt_newpass.getText();
    }

    public String getPassConfirm(){
        return this.txt_newpassconfirm.getText();
    }

    public String getMail(){
        return this.txt_newemail.getText();
    }


    public int getPartieUtilise() {
        return partieUtilise;
    }

    /**
     * fermer l'écran de connexion
     */
    public void close(){
        System.out.println("stage fermer");
        stage.close();
    }
}