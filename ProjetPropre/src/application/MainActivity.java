package src.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainActivity extends Application {

    private User user;
    private TabPane root;

    private Tab jeu,admin, profile;
    private Pane joueurPane;
    private Stage pm;

    /**
     * créer une application avec l'utilisateru demander
     * @param user
     */
    public MainActivity(User user){
        this.user = user;
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ferme la fenetre
     */
    public void close(){
        pm.close();
    }


    /**
     * lance l'application
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        root = new TabPane();
        root.getStylesheets().add("style/style.css");
        root.getStyleClass().add("jeux");


        jeu = new Tab(GLOBALS.JEU_TITLE);
        jeu.setGraphic(buildImage(GLOBALS.ADMIN_JEU_ICON));
        jeu.setClosable(false);
        this.joueurPane = new Joueur(this.user,this);
        jeu.setContent(this.joueurPane);

        root.getTabs().add(jeu);

        profile = new Tab(GLOBALS.PROFILE_TITLE);
        profile.setGraphic(buildImage(GLOBALS.PROFILE_ICON));
        profile.setClosable(false);
        profile.setContent(new Profil(this.user,this));

        root.getTabs().add(profile);

        if(this.user.isAdmin()) {
            admin = new Tab(GLOBALS.ADMIN_TITLE);
            admin.setGraphic(buildImage(GLOBALS.ADMIN_ADMIN_ICON));
            admin.setClosable(false);
            admin.setContent(new Administrateur(this.user, this));
            root.getTabs().add(admin);
        }

        pm = primaryStage;
        pm.setResizable(false);
        Scene laScene = new Scene(root);
        pm.setScene(laScene);
        pm.show();
    }

    private ImageView buildImage(String imgPath) {
        javafx.scene.image.Image i = new Image(imgPath);
        ImageView imageView = new ImageView();

        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        imageView.setImage(i);
        return imageView;
    }

    public void changeJoueur(Pane p){
        if(p!=null)
            this.jeu.setContent(p);
        else
            this.jeu.setContent(this.joueurPane);
    }

    public void swapTab(int index){
        root.getSelectionModel().select(index);
    }
}
