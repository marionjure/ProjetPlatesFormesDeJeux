package src.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Ami extends Application {
    private String user;
    private Stage primaryStage;
    Ami(String user){
        this.user=user;
    }
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage= new Stage();
        Scene laScene = new Scene(genPageConnexion());
        this.primaryStage.setScene(laScene);
        this.primaryStage.setTitle("Duel sur la Toile");
        this.primaryStage.show();

    }
    private VBox listePas(){
        HBox res= new HBox();
        ArrayList<String> l=GLOBALS.REQUETE_BD.listeJoueur();
        ArrayList<String> m=GLOBALS.REQUETE_BD.listePasAmis("iuto");
        m.add(this.user);
        ArrayList<String>h= this.fusion(l,m);
        VBox res1=new VBox(5);

        Label text = new Label("Niveau");
        ToggleGroup groupe=new ToggleGroup ();
        //this.itemNiveau = new ItemNiveau(this.m,this);
        for( int i=0 ;i<h.size();i++){
            ActionAmie actionAmie=new ActionAmie(this.user,this);
            RadioButton rd1=new RadioButton(h.get(i));
            rd1.setPrefWidth(80);
            rd1.setToggleGroup(groupe);
            rd1.setOnAction(actionAmie);
            //this.add(rd1);
            res1.getChildren().add(rd1);
        }


        return res1;
    }
    public VBox genPageConnexion(){
        VBox root = new VBox();
        root.getChildren().add(listePas());
        return root;
    }
    public  ArrayList<String> fusion(ArrayList<String> listeTout,ArrayList<String> liste ){
        ArrayList<String> res= new ArrayList<>();
        for(int i=0;i<listeTout.size();i++){


            if(liste.contains(listeTout.get(i))==false){
                res.add(listeTout.get(i));

            }


        }
        return res;
    }
}
