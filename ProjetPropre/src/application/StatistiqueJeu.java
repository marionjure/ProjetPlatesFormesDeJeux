package src.application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import src.Exceptions.PasDePartieException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StatistiqueJeu extends Application {
    private Jeu jeu;
    private HBox dateSelect;
    private DatePicker debut,fin;
    private Button btnChercher;

    private XYChart.Series serie;
    private BarChart<String,Number> bc;

    public static void main(String[] args){
        launch(args);
    }


    public StatistiqueJeu(Jeu j){
        jeu = j;
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * génère et affiche la page de statistique sur le jeu
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene maScene = new Scene(genererPage());
        primaryStage.setScene(maScene);
        primaryStage.setTitle(GLOBALS.MAINACTIVITY_TITLE);
        primaryStage.setWidth(GLOBALS.MAINACTIVITY_WIDTH);
        primaryStage.setHeight(GLOBALS.MAINACTIVITY_HEIGHT);
        primaryStage.setTitle("Statistique du jeu : "+jeu.getNom());
        primaryStage.show();

    }


    /**
     * crée la page de statistique pour le jeu
     * @return
     */
    public VBox genererPage(){
        //plot hystograme

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2018, Calendar.MARCH, 28);  // année , numMois-1 , num jour
        Date date = cal.getTime(); // get back a Date object

        VBox res =new VBox(genererHystograme(), genererStats());
        res.setSpacing(GLOBALS.SPACING_VALUE);
        res.setAlignment(Pos.CENTER);
        return res;
    }

    public VBox genererHystograme(){
        debut = new DatePicker();
        fin = new DatePicker();
        Calendar min = new GregorianCalendar();
        Calendar max = new GregorianCalendar();
        try {
            min.setTime(GLOBALS.REQUETE_BD.getDateMinPartie());
            max.setTime(GLOBALS.REQUETE_BD.getDateMaxPartie());
        } catch (PasDePartieException e) {
            min.setTime(new Date(1));
            max.setTime(new Date(0));
        }



        DatePicker maxDate = new DatePicker(); // DatePicker, used to define max date available, you can also create another for minimum date
        maxDate.setValue(LocalDate.of(max.get(Calendar.YEAR),max.get(Calendar.MONTH)+1,max.get(Calendar.DAY_OF_MONTH))); // Max date available will be 2015-01-01
        DatePicker minDate = new DatePicker();
        minDate.setValue(LocalDate.of(min.get(Calendar.YEAR),min.get(Calendar.MONTH)+1,min.get(Calendar.DAY_OF_MONTH)));
        final Callback<DatePicker, DateCell> dayCellFactory;

        dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(maxDate.getValue()) || item.isBefore(minDate.getValue())) { //Disable all dates after required date
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); //To set background on different color
                }
            }
        };


    //Finally, we just need to update our DatePicker cell factory as follow:
        debut.setDayCellFactory(dayCellFactory);
        fin.setDayCellFactory(dayCellFactory);

        debut.setValue(LocalDate.of(min.get(Calendar.YEAR),min.get(Calendar.MONTH)+1,min.get(Calendar.DAY_OF_MONTH)));
        fin.setValue(LocalDate.of(max.get(Calendar.YEAR),max.get(Calendar.MONTH)+1,max.get(Calendar.DAY_OF_MONTH)));

        btnChercher = new Button("Rafraichir");

        btnChercher.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                majHystograme();
            }
        });


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Nombre de partie par date");
        bc.setLegendVisible(false);
        xAxis.setLabel("Dates");
        yAxis.setLabel("Nombre de partie");
        majHystograme();



        HBox barreSelection = new HBox(new Label("Afficher de "),debut,new Label("jusqu'à "),fin,btnChercher); // bare de séléction de la date
        barreSelection.setSpacing(GLOBALS.SPACING_VALUE);
        barreSelection.setPrefWidth(GLOBALS.MAINACTIVITY_WIDTH);
        barreSelection.setAlignment(Pos.CENTER);


        return new VBox(bc,barreSelection);
    }


    public GridPane genererStats(){
        GridPane conteneur = new GridPane();
        conteneur.setAlignment(Pos.CENTER);
        conteneur.setHgap(GLOBALS.SPACING_VALUE);
        conteneur.setVgap(GLOBALS.SPACING_VALUE);

        conteneur.add(new Label("Nombre total de partie"), 0,0 );
        conteneur.add(new Label(""+GLOBALS.REQUETE_BD.statNbPartieParJeu(this.jeu)), 1, 0);
        conteneur.add(new Label("Nombre de partie en cours"), 0,1 );
        conteneur.add(new Label(""+GLOBALS.REQUETE_BD.statNbPartieEnCourPourJeu(this.jeu)), 1,1 );



        return conteneur;
    }

    public void majHystograme(){
        serie = new XYChart.Series();
        Date dtactu = java.sql.Date.valueOf(debut.getValue());
        Date dtfin = new Date(java.sql.Date.valueOf(fin.getValue()).getTime() + (1000*60*60*24));
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        while (dtactu.before(dtfin)){
            serie.getData().add(new XYChart.Data(dateFormater.format(dtactu).toString(),GLOBALS.REQUETE_BD.statNbJeuParDate(dtactu, this.jeu)));
            dtactu = new Date(dtactu.getTime() + (1000 * 60 * 60 * 24));
        }

        bc.getData().clear();
        bc.getData().add(serie);
    }
}
