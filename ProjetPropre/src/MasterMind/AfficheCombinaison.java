package src.MasterMind;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.RadioButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


import java.io.File;
import java.util.ArrayList;
import javafx.scene.control.Button;
/**
 * Génère la vue d'une combinaison 
 * le choix ici est d'un faire un héritié d'un HBox
 */
public class AfficheCombinaison extends HBox{
	/**
     * constructeur du clavier
     * @param liste une combinaison
     * @param taille de l'image
     */
	AfficheCombinaison (Combinaison liste,int taille){
		for(int i=0; i<liste.size();i++){
			//Création de l'image 
			ImageView img=new ImageView(new File("img/" +liste.get(i)+".png").toURI().toString());
			img.setFitWidth(taille);
			img.setPreserveRatio(true);
			//Ajout sur panel
			this.getChildren().add(img);
			}
			// Style //
			this.setStyle("-fx-border-style: solid inside;");
			this.setSpacing(19);
			// Fin Style //
			
		}
	
	
	} 
