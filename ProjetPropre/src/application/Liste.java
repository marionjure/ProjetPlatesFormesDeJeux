package src.application;


import com.sun.istack.internal.Nullable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import src.Interfaces.ElementDeListe;
import src.Interfaces.IonChangeEvent;
import src.application.widget.LabelObject;



import java.util.ArrayList;

public class Liste extends VBox {
    public static final int TYPE_LISTEJOUEUR = 1;
    public static final int TYPE_LISTEJEUX   = 2;
    public static final int TYPE_LISTEAMIS   = 3;

    private Label titre;
    private User user;
    private TextField recherche;
    private VBox liAffichage;
    private ArrayList<? extends ElementDeListe> li;
    private int type;
    private boolean afficherBanni;

    IonChangeEvent eventChange;

    private ElementDeListe selection;

    public Liste(int type, boolean afficherBanni){
        this(type,afficherBanni,null);
    }

    public Liste(int type, boolean afficherBanni, User u){
        super();
        this.type=type;
        this.afficherBanni = afficherBanni;
        this.user = u;
        switch (type){
            case TYPE_LISTEJOUEUR:
                titre = new Label("Liste des joueurs");
                break;
            case TYPE_LISTEJEUX:
                titre = new Label("Liste des jeux");
                break;
            case TYPE_LISTEAMIS:
                titre = new Label("Liste de vos amis");
                break;
        }


        selection = null;
        li = new ArrayList<>();
        recherche = new TextField();
        liAffichage = new VBox();
        liAffichage.setSpacing(2);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(liAffichage);
        remplir();

        recherche.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                actualiser();
            }
        });

        getChildren().addAll(titre,recherche,scroll);
    }

    /**
     * Met a jour la liste avec la bd et actualise l'affichage
     */
    public void remplir(){
        switch (type){
            case TYPE_LISTEJEUX:
                if(this.afficherBanni){
                    li = GLOBALS.REQUETE_BD.getListeJeu();
                    System.out.println(li);
                }else{
                    li= GLOBALS.REQUETE_BD.getListeJeuActif();
                }
                break;

            case TYPE_LISTEJOUEUR:
                if(this.afficherBanni){
                    li = GLOBALS.REQUETE_BD.getListeUtilisateur();
                }else{
                    li = GLOBALS.REQUETE_BD.getListeJeuActif();
                }
                break;

            case TYPE_LISTEAMIS:
                if(user!=null){
                    li = GLOBALS.REQUETE_BD.getAmis(user);
                }else{
                    li = new ArrayList<>();
                }
        }

        actualiser();
    }

    /**
     * actualise l'affichage
     */
    public void actualiser(){
        boolean find = false;  //a on trouvre la valeur séléctionnée
        liAffichage.getChildren().clear();
        for(ElementDeListe elem : li){  // pour chaque élément a mettre dans la liste
            if(elem.getTexteAffichage().contains(recherche.getText())){ // corespond a la recherche

               LabelObject l = new LabelObject(elem);


               l.setOnMouseEntered(new EventHandler<MouseEvent>() { //on change la couleur au survol de la souris
                   @Override
                   public void handle(MouseEvent event) {
                       if (l.getObjet().equals(selection)){
                           l.setBackground(new Background(new BackgroundFill(Color.DARKORANGE, null, null)));
                       }
                       else {
                           l.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
                       }
                   }
               });


               l.setOnMouseExited(new EventHandler<MouseEvent>() { //on change la couleur a la sortie de survol de la souris
                   @Override
                   public void handle(MouseEvent event) {
                       if (l.getObjet().equals(selection)){
                           l.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
                       }
                       else {
                           l.setBackground(null);
                       }
                   }
               });


               l.setOnMouseClicked(new EventHandler<MouseEvent>() { //click de souris
                   @Override
                   public void handle(MouseEvent event) {
                       if(event.getButton()==MouseButton.PRIMARY) { //selection de l'élément au click gauche
                           selection = elem;
                           if (eventChange != null)
                               eventChange.onChangeAction();
                           actualiser();
                       }
                       if(event.getButton()==MouseButton.SECONDARY){
                           if(type==TYPE_LISTEJEUX){ // affichage du menu contextuelle si liste de jeu
                               // Create ContextMenu
                               ContextMenu contextMenu = new ContextMenu();

                                // TODO
                               MenuItem item1 = new MenuItem("Statistique de "+l.getText());
                               item1.setOnAction(new EventHandler<ActionEvent>() {

                                   @Override
                                   public void handle(ActionEvent event) {
                                       new StatistiqueJeu((Jeu)l.getObjet());
                                   }
                               });
                               MenuItem item2 = new MenuItem("Deselectionner");
                               item2.setOnAction(new EventHandler<ActionEvent>() {

                                   @Override
                                   public void handle(ActionEvent event) {
                                       setSelection(null);
                                       if(eventChange!=null)
                                           eventChange.onChangeAction();
                                   }
                               });

                               // Add MenuItem to ContextMenu
                               contextMenu.getItems().addAll(item1, item2);
                               l.setContextMenu(contextMenu);
                           }else if(type==TYPE_LISTEJOUEUR){ //deselection de l'élément si listede joueur
                               selection=null;
                               actualiser();
                           }
                       }
                   }
               });


               l.setBorder(new Border(new BorderStroke(null,null,new CornerRadii(5),null))); //stylisation


               if (selection!=null && selection.equals(l.getObjet())) { //si le label et le label selectionnel
                   find = true;
                   l.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
               }

               liAffichage.getChildren().add(l);
            }
        }
        if(!find)
            selection=null;
    }


    public void setOnChangeEvent(IonChangeEvent o){
        eventChange = o;
    }

    public ElementDeListe getSelection(){return selection;}

    public void setSelection(@Nullable ElementDeListe selection){this.selection=selection;actualiser();}

    public ArrayList<? extends ElementDeListe> getLi(){return li;}

    public boolean equals(Liste li){
        return titre.getText().equals(li.titre.getText());
    }
    public boolean contains(String str){
        return li.contains(str);
    }



}
