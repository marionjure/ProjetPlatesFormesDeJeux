package src.application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import src.Interfaces.IonChangeEvent;


import java.io.File;
import java.io.ByteArrayInputStream;


public class Joueur extends HBox implements IonChangeEvent {

    private MainActivity mainActivity;
    private Liste listeJeux;
    private Jeu jeuActuel;
    private User u;
    private HBox container;
    private Label labelDescriptionDuJeu,nbPartiesJouees;
    private float pourcentageVictoire;
    private Invitation invitation;
    private PieChart camembert;
    private Button jouer;

    private HBox Haut(){
        Button Deconnexion = new Button("Deconnexion");

        Deconnexion.setId("Deconnexion");
        Deconnexion.getStyleClass().add("abandon");
        Deconnexion.setOnAction(new ActionBouton(this.u, this.mainActivity));
        Deconnexion.setAlignment(Pos.CENTER);

        u.loadIcon();
        Image img;
        if(u.getIcone()!=null) {
            img = new Image(new ByteArrayInputStream(this.u.getIcone()));
        }else{
            img = new Image(GLOBALS.LOGO_ICON);
        }

        //ajout de l'icone
        //tesr
        ImageView icone = new ImageView(img);
        icone.setFitHeight(30);
        icone.setFitWidth(30);
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(GLOBALS.PADDING_VALUE);
        box.setPrefWidth(GLOBALS.MAINACTIVITY_WIDTH);
        icone.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainActivity.swapTab(1);
            }
        });

        HBox boxbis = new HBox(Deconnexion);
        boxbis.setAlignment(Pos.CENTER_LEFT);
        boxbis.setPrefWidth(GLOBALS.MAINACTIVITY_WIDTH);

        box.getChildren().addAll(boxbis,icone);
        return box;
    }

    private VBox Gauche(){
        VBox gauche = new VBox();
        listeJeux=new Liste(Liste.TYPE_LISTEJEUX, false);
        listeJeux.setOnChangeEvent(this);
        gauche.getChildren().add(listeJeux);
        gauche.setPadding(new Insets(0,10,0,10));
        return gauche;

    }

    private VBox Centre(){

        VBox centre = new VBox();

        VBox centreHaut = new VBox();
        TabPane tabCentre = new TabPane();
        Tab desc = new Tab();
        tabCentre.getStyleClass().add("info");
        desc.setText("Description");
        VBox partieDescription = new VBox();
        partieDescription.setPrefSize(200,200);

        centreHaut.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: lightgrey;");


        Label labelTitreDescription =new Label("Description");
        labelTitreDescription.setFont(Font.font(20));
        labelTitreDescription.setPadding(new Insets(5,0,10,5));

        if(jeuActuel==null)
            this.labelDescriptionDuJeu.setText("Veuillez selectionnez un jeu pour voir sa description");
        else
            this.labelDescriptionDuJeu.setText(jeuActuel.getDescription());

        partieDescription.getChildren().addAll(labelTitreDescription,this.labelDescriptionDuJeu);

        desc.setContent(partieDescription);
        Tab stat = new Tab();
        stat.setText("Statistiques");
        HBox content = new HBox();
        this.nbPartiesJouees = new Label();
        this.nbPartiesJouees.setFont(Font.font(20));
        this.nbPartiesJouees.setPadding(new Insets(5,0,10,5));
        this.nbPartiesJouees.setAlignment(Pos.CENTER);
        if(jeuActuel==null)
            this.nbPartiesJouees.setText("Veuillez selectionnez un jeu pour voir vos statistiques");

        camembert = new PieChart();
        camembert.setMaxSize(400, 400);
        camembert.setLegendVisible(false);
        content.getChildren().addAll(this.nbPartiesJouees,camembert);
        stat.setContent(content);

        tabCentre.getTabs().addAll(desc,stat);
        tabCentre.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        centreHaut.getChildren().addAll(tabCentre);

        Jeu jeu=(Jeu) this.listeJeux.getSelection();
        BorderPane centreBas = new BorderPane();
        this.jouer = new Button("Jouer");
        this.jouer.setText("Jouer");
        this.jouer.getStyleClass().add("abandon");
        this.jouer.setId("Jouer");
        //Jouer.setOnAction(new ActionBouton(this.u,this.mainActivity));
        this.jouer.setFont(Font.font(20));
        this.jouer.setPrefSize(150,50);
        Button Parties = new Button("Parties");
        Parties.setText("Parties");
        Parties.setId("Parties");
        Parties.setOnAction(new ActionBouton(this.u, this.mainActivity));
        Parties.setFont(Font.font(20));
        Parties.setPrefSize(150,50);
        Parties.getStyleClass().add("abandon");
        centreBas.setLeft(this.jouer);
        centreBas.setRight(Parties);
        centreBas.setPadding(new Insets(10,20,10,20));

        centre.getChildren().addAll(centreHaut,centreBas);

        this.invitation=new Invitation(this.u.getPseudo(),jeu!=null ?jeu:null,1);

        centre.getChildren().add(this.invitation);
        centre.setPadding(new Insets(5,5,5,5));
        return centre;
    }

    private VBox Droite(){
        VBox tchat = new VBox();
        tchat.getChildren().add(new Tchat(this.u));
        tchat.setPadding(new Insets(0,10,0,10));
        return tchat;
    }


    public Joueur(User u,MainActivity mainActivity){
        this.mainActivity=mainActivity;
        this.u=u;
        this.labelDescriptionDuJeu=new Label();
        BorderPane cont=new BorderPane();
        cont.setTop(this.Haut());
        cont.setLeft(this.Gauche());
        cont.setCenter(this.Centre());
        cont.setRight(this.Droite());
        getChildren().addAll(cont);

        cont.getStylesheets().add(new File("style/style.css").toURI().toString());
    }



    public void sauvegarde(){
        this.container=this;
    }

    @Override
    public void onChangeAction() {
        jeuActuel = (Jeu) this.listeJeux.getSelection();
        this.labelDescriptionDuJeu.setText(jeuActuel.getDescription());
        if(jeuActuel!=null){
        this.jouer.setOnAction(new ActionJouer(this.u.getPseudo(),jeuActuel));}
        else{
            this.jouer.setOnAction(null );
        }
        this.invitation.start(this.u.getPseudo(),jeuActuel!=null ?jeuActuel:null,1);
        if(this.u.getNbPartiesJouées(this.u.getPseudo(),jeuActuel.getId())==0)
            this.nbPartiesJouees.setText("Vous n'avez pas joué de parties");
        else{
            this.pourcentageVictoire=(this.u.getNbPartiesGagnées(this.u.getPseudo(),jeuActuel.getId())/this.u.getNbPartiesJouées(this.u.getPseudo(),jeuActuel.getId()))*100;
            this.nbPartiesJouees.setText("Vous avez joué :"+this.u.getNbPartiesJouées(this.u.getPseudo(),jeuActuel.getId())+" parties\n"+"Votre taux de victoire est de :" + this.pourcentageVictoire+"%");
        }
        ObservableList<PieChart.Data> li = FXCollections.observableArrayList(
                new PieChart.Data("Victoire",pourcentageVictoire),
                new PieChart.Data("Défaites",100-pourcentageVictoire)
        );

        this.camembert.setData(li);



    }
}

