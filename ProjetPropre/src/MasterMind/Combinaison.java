package src.MasterMind;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/**
 * Modèle d'un combinaison :ArrayList.
 */
public class Combinaison extends ArrayList<String>
{
	/**
	 * constructeur combinaison
	 * @param c1 première couleur
	 * @param c2 deuxième couleur
	 * @param c3 troisième couleur 
	 * @param c4 quatrième couleur
	 */
	public Combinaison(String c1,String c2,String c3,String c4)
	{
		this.add(c1);
		this.add(c2);
		this.add(c3);
		this.add(c4);
	}
	/**
	 * constructeur combinaison vide
	 */
	public Combinaison() 
	{
		this.add("vide"); // Ajout du string vide pour que la combinaison prenne
		this.add("vide");
		this.add("vide");
		this.add("vide");

	}
	/**
	 * égalité  de deux combinaisons
	 * @return boolean
	 */
	public boolean equals(Object o){
		Combinaison j = (Combinaison) o;
		for (int i=0;i<this.size();i++)
		{
			if(j.get(i).compareTo(this.get(i))!=0 )
			{
				return false;
			}
		}
		return true;
	}
	/**
	 * change un couleur à une position
	 * @param s la nouvelle couleur
	 * @param indice la position dans la liste
	 */
	public void setCouleur(String s,int indice)
	{
		this.set(indice,s);
	}
	/**
	 * change toutes couleurs d'une combinaison
	 * @param c  nouvelle couleur
	 * @param c2 nouvelle couleur
	 * @param c3 nouvelle couleur 
	 * @param c4 nouvelle couleur
	 */
	public void setCombinaison(String c,String c2,String c3,String c4)
	{
		this.setCouleur(c,0);
		this.setCouleur(c2,1);
		this.setCouleur(c3,2);
		this.setCouleur(c4,3);
	}
	/**
	 * initialisation d'une combinaison à vide
	 */
	public void initialisation()
	{
		this.setCouleur("vide",0);
		this.setCouleur("vide",1);
		this.setCouleur("vide",2);
		this.setCouleur("vide",3);
	}
	/**
	 * Convertir la combinaison en String 
	 * @return res 
	 * @example ["rouge","vert","rouge","blue"] => "rvrb"
	 */
	public String convertir()
	{
		Map<String ,String> dico =new HashMap<>();
		dico.put("rouge","r");
		dico.put("noir","n");
		dico.put("bleu","b");
		dico.put("vert","v");
		dico.put("blanc","w");
		dico.put("jaune","j");
		String res="";
		for(int i=0;i<this.size();i++)
		{
			res+=dico.get(this.get(i));
		}
		return res;

	}

}
