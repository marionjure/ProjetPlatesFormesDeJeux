package src.MasterMind;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import java.io.File;

import javafx.scene.control.Button;
import src.Exceptions.JoueurPasTrouverException;
import src.Interfaces.Jeux;
import src.application.GLOBALS;
import src.application.Partie;
import src.application.Tchat;

/**
 * Vue du jeu du mastermind
 */
public class MasterMind extends Application implements Jeux {

	/**
     * modèle du jeu
     */
    private MasterMindModele masterMindModele;
     /**
     * le chronomètre
     */
    private Chronometre chrono;
     /**
     * le clavier
     */
    private Clavier clavier;
    /**
    * la liste des combinaisons incorrectes
    */
    private VBox listeCombinaisonTrouver;
    /**
    * la liste des combinaisons avec les indications des pions bien ou mal placés
    */
    private VBox listeCombinaisonNoirBlanc;
    /**
    * la combinaison actuelle
    */
    private ElaboreCombinaison elaboreCombinaison;
     /**
    * la combinaison actuelle
    */
    private Combinaison combinaisonActuelle;
    /**
    * le nombre de manche
    */
    private Label manche;
    /**
    * le boutons gérant la validation d'une combinaison
    */
    private Button valider;
    /**
    * le boutons gérant le resert d'une combinaison
    */
    private Button annuler;
    /**
    * la barre pour faire défiler les combinaisons
    */
    private ScrollPane milieu;
    /**
    * la scène
    */
    private Stage primaryStage;
	/**
     * Programme principal
     * @param args inutilisé
     */
    public static void main(String[] args)
    {

    }

	/**
     * @return la liste des combinaisons incorrectes c'est un VBox
     */
    public VBox getListeCombinaisonTrouver()
    {
        return this.listeCombinaisonTrouver;
    }
    /**
     * @return la listes de combinaison avec les indications des pions bien ou mal placés c'est un VBox
     */
    public VBox getListeCombinaisonNoirBlanc()
    {
        return this.listeCombinaisonNoirBlanc;
    }

	/**
	 * @return le panel du haut du Plateau c'est à dire le bouton valider,annuler et ElaboreCombinaison
	 */
    private HBox hautPlateau()
    {
        HBox res= new HBox();
        HBox b= new HBox();
		// Création du bouton valider //
        ActionValider actionValider =new ActionValider(this,this.masterMindModele);
        this.valider =new Button();
        ImageView img =new ImageView(new File("img/valide.png").toURI().toString());
        this.valider.setGraphic(img);

        this.valider.setOnAction(actionValider);
        this.valider.setDisable(true); ;

		// Création du bouton annuler //
        ActionAnnuler actionAnnuler=new ActionAnnuler(this);
        ImageView img2 =new ImageView(new File("img/annule.png").toURI().toString());
        this.annuler=new Button();
        this.annuler.setGraphic(img2);

        annuler.setOnAction(actionAnnuler);

		// Création de la combinaison //
        this.combinaisonActuelle=new Combinaison();

        ActionChoix action=new ActionChoix(this);
        this.elaboreCombinaison=new ElaboreCombinaison(action);

		//Ajout sur  les panels
        b.getChildren().add(this.valider);
        b.getChildren().add(annuler);
        b.setSpacing(20);

        res.getChildren().add(this.elaboreCombinaison);
        res.getChildren().add(b);

		// Style //
        res.getStyleClass().add("mastermind");
        res.setSpacing(50);
        res.setStyle("-fx-padding: 5;-fx-border-style: solid inside;");
        // Fin Style //
        return res;

    }

	/**
	 * @return le panel du clavier
	 */
    private HBox ClavierCouleur()
    {
        ActionClavier actionClavier =new ActionClavier(this);
        this.clavier= new Clavier(actionClavier);
        this.clavier.getStyleClass().add("mastermind");
        return this.clavier;
    }
    /**
	 * @return le panel du milieu de Plateau
	 */
    private GridPane milieuPlateau(){
        GridPane res = new GridPane();
        // Création de la liste des combinaisons incorrectes //
        this.listeCombinaisonTrouver= new VBox();
        this.listeCombinaisonTrouver.setStyle("-fx-padding-top: 0;");

		// Création de la listes de combinaison avec les indications des pions bien ou mal placés //
        this.listeCombinaisonNoirBlanc=new VBox();
        this.listeCombinaisonNoirBlanc.setStyle("-fx-padding-top: 0;-fx-padding-left: 5");

		// Style //
        this.listeCombinaisonTrouver.setMinSize(280,700);
        this.listeCombinaisonNoirBlanc.setMinSize(280,700);
        // Fin Style //
        //Ajout sur  le panel
        res.add(this.listeCombinaisonTrouver,0,0);
        res.add(this.listeCombinaisonNoirBlanc,1,0);
        res.getStyleClass().add("mastermind");
        return res;

    }
	/**
	 * @return le panel du Plateau du mastermind
	 */
    private BorderPane plateauDuMasterMind()
    {
        BorderPane res = new BorderPane();
        // Scroll milieu
        this.milieu = new ScrollPane();
        milieu.setContent(milieuPlateau());
        milieu.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        milieu.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        milieu.setStyle("-fx-border-style: solid inside;" + "-fx-border-color: black;"+"-fx-padding: 0;");
        milieu.setVvalue(1);
        // Fin Scroll
        HBox clavierCo = ClavierCouleur();
        HBox haut = hautPlateau();
        //Ajout sur  le panel
        res.setCenter(milieu);
        res.setTop(haut);
        res.setBottom(clavierCo);
        // Style //
        res.getStyleClass().add("mastermind");
		// Fin Style //
        return res;

    }
	/**
	 * @return le panel du chronomètre
	 */
    private VBox leChrono()
    {
        VBox res = new VBox(5);
        // Création Chrono //
        chrono = new Chronometre();
        ImageView img=new ImageView(new File("img/chrono.png").toURI().toString());
        chrono.setPadding(new Insets(50,60,40,40));
        //Ajout sur panel
        res.getChildren().add(img);
        res.getChildren().add(chrono);
        res.setPadding(new Insets(50,60,40,10));
        // Style //
        // Fin Style //
        return res;
    }
    /**
	 * @return le panel du nom de l'adversaire
	 */
    private HBox Adversaire()
    {
        HBox panel = new HBox();
        // Ecriture intérieur HBox //
        Label res = new Label("Adversaire : "+this.masterMindModele.getAdversaire());
        // Ajout sur panel //
        panel.getChildren().add(res);
        panel.setPadding(new Insets(30,60,20,-10));
        return panel;
    }
    /**
	 * @return le panel du nombre de manche
	 */
    private HBox nbManche()
    {
        HBox panel = new HBox();
        // Ecriture intérieur HBox //
        this.manche = new Label("Manche : "+this.masterMindModele.getGame().getManche()+"/"+this.masterMindModele.getGame().getNbManche());
        // Ajout sur panel //
        panel.getChildren().add(manche);
        panel.setPadding(new Insets(50,60,40,10));
        return panel;
    }
    private HBox abandonner()
    {
        HBox panel = new HBox();
        Button abandonner = new Button("Abandonner");
        abandonner.getStyleClass().add("abandon");
        abandonner.setPadding(new Insets(20,20,20,20));
        abandonner.setOnAction(new ActionAbandon(this,this.masterMindModele.getCombinaisonMystere()));
        panel.getChildren().add(abandonner);
        panel.setPadding(new Insets(0,0,0,25));
        return panel;
    }
	/**
	 * @return le panel composé de : NbManche / Résultat / Chrono / Adversaire
	 */
    public VBox rightPanel()
    {
        // Ajout du right Panel
        HBox manche = nbManche();
        //HBox result = resultat();
        VBox chrono = leChrono();
        HBox adversaire = Adversaire();
        HBox abandonner = abandonner();
        // Ajout de tout dans le panel

        VBox panel = new VBox();
        panel.getStyleClass().add("right_panel");
        panel.setSpacing(10);
        panel.setPadding(new Insets(100));
        panel.getChildren().addAll(manche,chrono,adversaire,abandonner);
        return panel;
    }
    public VBox Tchat(){
        VBox panel = new VBox();
        try {
           panel = new Tchat(GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(this.masterMindModele.getUser()), GLOBALS.REQUETE_BD.getJoueurParPseudoOuMail(this.masterMindModele.getAdversaire()));
           panel.setAlignment(Pos.CENTER);
           panel.setPadding(new Insets(0,25,0,25));
        }
        catch(JoueurPasTrouverException e){
            System.out.println("ERROR");
        }
        return panel;
    }
	 /**
     * @return  le graphe de scène de la vue
     */
    public Scene ensemble()
    {
        BorderPane panel = new BorderPane();
        // Ajout sur panel //
        panel.getStyleClass().add("jeux");
        BorderPane plateau = plateauDuMasterMind();
        panel.setRight(rightPanel());
        panel.setCenter(plateau);
        panel.setLeft(Tchat());
        // Style //
        panel.getStylesheets().add("style/style.css");
        // Fin Style //
        return new Scene(panel,1294,900);
    }
    /**
     * Réinitialisation l'affichage d'une nouvelle manche
     */
    public void nouvelleManche()
    {
        this.cleanListe();
        this.masterMindModele.getGame().setEssai(0);
        chrono.stop();
        // Enregistrement BD temps chrono //

        // Réinitialisation //
        this.chrono.resetTime();
        this.manche.setText(("Manche : "+this.masterMindModele.getGame().getManche()+"/"+this.masterMindModele.getGame().getNbManche()));
    }
    /**
     * Mise à jour des combinaisons en cours & disable bouton quand toutes cases remplient
     */
    public void majAffichage()
    {
		//Mise à jour des combinaisons en cours
        for(int i=0;i<4;i++)
        {
            this.getElabore().ChangerCouleur(this.combinaisonActuelle.get(i),i);
        }
        //disable bouton quand toutes cases remplient
        if(this.getElabore().IndicePremiereBoutonVide()==-1)
        {
            this.clavier.activeBouton(false);
            this.valider.setDisable(false);
            this.masterMindModele.getGame().IncrementEssai();
        }
        else
        {
            this.clavier.activeBouton(true);
            this.valider.setDisable(true);
        }
        this.milieu.setVvalue(1);
    }

	/**
     * @return  la combinaison actuelle
     */
    public Combinaison getCombinaisonActuelle()
    {
		return this.combinaisonActuelle;
	}
    /**
     * @return la scrollbar des combinaisons
     */

    public ElaboreCombinaison getElabore()
    {
        return this.elaboreCombinaison;
    }
	/**
     * @return  le chronometre
     */
    public Chronometre getChrono()
    {
        return this.chrono;
    }
    /**
     * @return  le clavier
     */
    public Clavier getClavier() {
        return clavier;
    }
    /**
     * @return  le bouton annuler
     */
    public Button getAnnuler(){
        return annuler;
    }

	/**
     * Ferme la fenetre
     */
    public void close()
    {
     this.primaryStage.close();
    }
    /**
     * Vide les HBox des liste des combinaisons incorrectes et des combinaisons avec les indications des pions bien ou mal placés
     */
    public void cleanListe()
    {
        this.listeCombinaisonNoirBlanc.getChildren().clear();
        this.listeCombinaisonTrouver.getChildren().clear();
        //vide les HBox des listeTrouver et NoirBlanc
    }
	 /**
     * @return  le modele du jeu
     */
    public MasterMindModele getMasterMindModele()
    {
        return masterMindModele;
    }



	 /**
     * créer le graphe de scène et lance le jeu
     */
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.getIcons().add(new Image(new File("img/logo.png").toURI().toString()));
        this.primaryStage.setTitle("MasterMind");
        this.primaryStage.setScene(ensemble());
        this.primaryStage.show();
        this.chrono.start();
    }
	 /**
	  * Vérifier si son adversaire a fini ses 3 manches , si oui enregistrer le vainqueur dans la base de donnée
      */
    public void listeGagner()throws SQLException{
        //ArrayList<Boolean> res =new ArrayList<>();
        //Vérifie si son adversaire a abandonné
        if(GLOBALS.REQUETE_BD.getEtatAdverse(this.masterMindModele.getAdversaire(),this.masterMindModele.getPartie())==-2){
            this.masterMindModele.getPartie().setVainqueurPartie(this.masterMindModele.getUser());
            this.masterMindModele.getPartie().setNumEtapePartie(5);
            this.masterMindModele.getPartie().setEtatPartie(-1);
            GLOBALS.REQUETE_BD.insererPartie(this.masterMindModele.getPartie());
        }
		//Vérifier si son adversaire a fini
        if(GLOBALS.REQUETE_BD.numEtapeJoueur(this.masterMindModele.getPartie().getNumPartie(),this.masterMindModele.getAdversaire())>=GLOBALS.REQUETE_BD.numEtapeJoueur(this.masterMindModele.getPartie().getNumPartie(),this.masterMindModele.getUser()))
        {
		// Trouver le vainqueur pour chaque manche ,i est le nombre de la manche
        for (int i=2;i<5;i++ )
        {
            try
            {
				String coupUser=GLOBALS.REQUETE_BD.actionJoueurPartie(this.masterMindModele.getPartie().getNumPartie() ,this.masterMindModele.getUser(),i);
				String coupAdversaire=GLOBALS.REQUETE_BD.actionJoueurPartie(this.masterMindModele.getPartie().getNumPartie() ,this.masterMindModele.getAdversaire(),i);
				//Vérifier que le user et son adversaire sont execo , si oui compare le user et l'adversaire au niveau du temps
				if(Biblio.nbEssai(coupUser).compareTo(Biblio.nbEssai(coupAdversaire))==0)
				{
					Date dateUser= Biblio.dateTemps(coupUser);
					Date dateAdversaire= Biblio.dateTemps(coupAdversaire);
					//res.add(dateUser.compareTo(dateAdversaire)<0);

					//Vérifier si c'est le user qui a gagné
					if(dateUser.compareTo(dateAdversaire)<0)
					{
						this.masterMindModele.getGame().gagnerManche();
					}
					else
                    {
						this.masterMindModele.getGame().perduManche();
					}
				}
				else{
					if(Biblio.nbEssai(coupUser).compareTo(Biblio.nbEssai(coupAdversaire))<0)
					{
                        this.masterMindModele.getGame().gagnerManche();
                    }
					else
                    {
                        this.masterMindModele.getGame().perduManche();
                    }

				}
			}
            catch (SQLException e)
            {
                System.out.println("ERROR 404");
            }
			catch (ParseException e)
			{
				e.printStackTrace(); }
			}
            this.masterMindModele.getPartie().setNumEtapePartie(this.masterMindModele.getGame().getManche()+1);
            //Trouver le vainqueur pour la partie
            if (this.masterMindModele.getGame().resultatJeu()==false)
            {
                this.masterMindModele.getPartie().setVainqueurPartie(this.masterMindModele.getAdversaire());
                this.masterMindModele.getPartie().setEtatPartie(-1);
            }
            else
                {
                this.masterMindModele.getPartie().setVainqueurPartie(this.masterMindModele.getUser());
                this.masterMindModele.getPartie().setEtatPartie(-1);
                }
            //Enregistrer le vainqueur dans la base de donnée
            GLOBALS.REQUETE_BD.insererPartie(this.masterMindModele.getPartie());
		}
    }

    @Override
    public void jouerCoup(int idPartie, String numJoueur, Object partage)
    {
        String time = this.chrono.getText();
        try
        {
            String coup = GLOBALS.REQUETE_BD.coupPreMasterMind(idPartie,numJoueur,this.masterMindModele.getAdversaire());
            this.masterMindModele.getCombinaisonMystere().nouvelleCombinaisonATrouver();
            if (coup.compareTo("ok")==0)
            {

                this.masterMindModele.getPartie().setActionJoueur( this.masterMindModele.getCombinaisonMystere().getCombinaisonATrouver().convertir()+"/"+this.masterMindModele.getGame().getEssai()+"/"+time);
                System.out.println(this.masterMindModele.getPartie().getActionJoueur());

            }
            else
            {
                coup=coup.substring(0,4) +"/"+this.masterMindModele.getGame().getEssai()+"/"+time;
                this.masterMindModele.getPartie().setActionJoueur(coup);
                System.out.println(this.masterMindModele.getPartie().getActionJoueur());
                this.masterMindModele.getCombinaisonMystere().convertirC(this.masterMindModele.getPartie().getActionJoueur());

            }
            GLOBALS.REQUETE_BD.insererPartie(this.masterMindModele.getPartie());

        }
        catch (SQLException e)
        {
            System.out.println("ERROR 404");
        }

    }

    @Override
    public void creerPartie(int idJeu, String idJoueur1, String idJoueur2, Object partage)
    {

        String actionj="";
        String partage2= (String) partage;
        String adversaire="";
        if(idJoueur1.compareTo(partage2)==0)
        {
            adversaire=idJoueur2;
        }

        else
        {
            adversaire=idJoueur1;
        }

        try
        {
            this.masterMindModele = new MasterMindModele(new Game(3),new CombinaisonMystere(),partage2,adversaire);
            int numP=GLOBALS.REQUETE_BD.numPartie(idJoueur1,idJoueur2);
            String coup = GLOBALS.REQUETE_BD.coupPreMasterMind(numP,this.masterMindModele.getUser(),this.masterMindModele.getAdversaire());
            if (coup.compareTo("ok")==0)
            {
                actionj= this.masterMindModele.getCombinaisonMystere().getCombinaisonATrouver().convertir()+"/"+this.masterMindModele.getGame().getEssai()+"/"+this.chrono;
            }
            else
            {
                actionj=coup;
                this.masterMindModele.getCombinaisonMystere().convertirC(actionj);
            }
            this.masterMindModele.setPartie(new Partie(idJeu, numP, new Date(), this.masterMindModele.getGame().getManche(),actionj,this.masterMindModele.getUser(),0,null));
            GLOBALS.REQUETE_BD.insererPartie(this.masterMindModele.getPartie());
        }
        catch (SQLException e)
        {
            System.out.println("ERROR 404");
        }
        this.start(new Stage());
    }
    public void reprendrePartie(int idJeu, String idJoueur1, String idJoueur2, Object partage,int numPartie){
        String partage2= (String) partage;
        String adversaire="";
        if(idJoueur1.compareTo(partage2)==0)
        {
            adversaire=idJoueur2;
        }

        else
        {
            adversaire=idJoueur1;
        }

        try
        {
            this.masterMindModele = new MasterMindModele(new Game(3),new CombinaisonMystere(),partage2,adversaire);
            System.out.println("ok"+GLOBALS.REQUETE_BD.isEnCours(numPartie,this.masterMindModele.getUser()));
            if (!GLOBALS.REQUETE_BD.isEnCours(numPartie,this.masterMindModele.getUser())){
                this.creerPartie(idJeu,idJoueur1,idJoueur2,partage);
            }
           else
            {
                this.masterMindModele.setUser(partage2);
                this.masterMindModele.setPartie(GLOBALS.REQUETE_BD.partie(numPartie,idJeu,this.masterMindModele.getUser()));
                String coup = GLOBALS.REQUETE_BD.nbCoup(numPartie,this.masterMindModele.getUser());
                if(this.masterMindModele.getPartie().getNumEtapePartie()!=1)
                {
                this.masterMindModele.getGame().setManche(GLOBALS.REQUETE_BD.numEtapeJoueur(numPartie,this.masterMindModele.getUser()));
                }
                this.masterMindModele.getCombinaisonMystere().convertirC(Biblio.coup(coup));
                this.start(new Stage());
            }
    }
        catch (SQLException e)
    {
        System.out.println("ERROR 404");
    }
    }
}
