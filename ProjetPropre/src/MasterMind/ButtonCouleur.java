package src.MasterMind;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
/**
 * Button Couleur
 */
public class ButtonCouleur extends Button 
{
	/**
	 * le nom de la couleur 
	 */
	private String nomCouleur;
	/**
	 * l'image associer au bouton 
	 */
	private ImageView img;
	/**
	 * postion dans une combinaison
	 */
	private int indice;
	/**
     * constructeur du buttonCouleur
     * @param couleur du bouton
     * @param indice dans la combinaison
     */
	ButtonCouleur(String couleur,int indice)
	{
		super();
		//Création de l'image
		this.img=new ImageView(new File("img/" +couleur+".png").toURI().toString());
		this.img.setFitWidth(60);
		img.setPreserveRatio(true);
		//Associer l'image au button
		this.setGraphic(img);
		this.nomCouleur=couleur;
		this.indice=indice;
	}
	/**
     * constructeur du buttonCouleur
     * @param couleur du bouton
     */
	ButtonCouleur(String couleur)
	{
		super();
		//Création de l'image
		this.img=new ImageView(new File("img/" +couleur+".png").toURI().toString());
		img.setFitWidth(60);
		img.setPreserveRatio(true);
		//Associer l'image au button
		this.setGraphic(img);
		this.nomCouleur=couleur;
		this.indice=-1;
	}
	/**
	 * @return le nom de la couleur
	 */
	public String getNomCouleur()
	{
		return this.nomCouleur;
	}
	/**
	 * @return l'indice dans une liste
	 */
	public int getIndice()
	{
		return this.indice;
	}
	/**
	 * change le nom de la couleur
	 * @param n la nouvelle couleur
	 */
	public void setNomCouleur(String n)
	{
		this.nomCouleur=n;
	}
	/**
	 * Change l'image associer au bouton
	 * @param t la nouvelle couleur
	 */
	public void changerImage(String t)
	{
		ImageView img=new ImageView(new File("img/" +t+".png").toURI().toString());
		img.setFitWidth(60);
		img.setPreserveRatio(true);
		this.setGraphic(img);
		this.setNomCouleur(t);
	}
}
