package src.MasterMind;

import java.util.*;
/**
 * Modèle du jeu 
 */
public class CombinaisonMystere
{
	/**
	 * la combinaison à trouver
	 */
	private Combinaison combinaisonATrouver;
	private int nbEssai;
	/**
	 * constructeur combinaisonMystere
	 * Choix aléatoire de la combinaison
	 */
	CombinaisonMystere()
	{
		this.combinaisonATrouver= new Combinaison();
		//Choix aléatoire de la combinaison
		this.nouvelleCombinaisonATrouver();
	}
	/**
	 * @return la combinaison à trouver
	 */
	public Combinaison getCombinaisonATrouver()
	{
		return this.combinaisonATrouver;
	}
	/**
	 * Choisi une combinaison aléatoire
	 */
	public void nouvelleCombinaisonATrouver()
	{
		//Choisi une combinaison aléatoire
		ArrayList<String> couleur = new ArrayList<String>();
		couleur.add("bleu");
		couleur.add("noir");
		couleur.add("vert");
		couleur.add("blanc");
		couleur.add("jaune");
		couleur.add("rouge");
		for (int i = 0; i < 4; i++) 
		{
			Random random = new Random();
			int nb;
			nb = random.nextInt(6);
			this.combinaisonATrouver.setCouleur(couleur.get(nb), i);
		}
	}
	/**
	 * 
	 * @return un booléen indiquant si le joueur a gangé une manche
	 */
	public boolean gagner(Combinaison combinaisonActuelle)
	{
		return this.combinaisonATrouver.equals(combinaisonActuelle);
	}
	public String toString()
	{
		return this.combinaisonATrouver.toString();
	}
	/**
	 * Création de la combinaison avec les indications des pions bien ou mal placés
	 * @param combinaisonActuelle 
	 * @return la combinaison noir/blanc trie par ordre noir,blanc,vide
	 */
	public Combinaison noirBlanc(Combinaison combinaisonActuelle)
	{
		Combinaison res = new Combinaison();
		Combinaison liste=new Combinaison();
		liste.setCombinaison(this.combinaisonATrouver.get(0),this.combinaisonATrouver.get(1),this.combinaisonATrouver.get(2),this.combinaisonATrouver.get(3));
		for (int i=0;i<4;i++)
		{
			if (combinaisonActuelle.get(i).compareTo(this.combinaisonATrouver.get(i))==0)
			{
				res.setCouleur("noir",i);
				liste.remove(combinaisonActuelle.get(i));
			}

		}
		for (int i=0;i<4;i++)
		{
			if (liste.size()!=0){
				if (liste.contains(combinaisonActuelle.get(i))==true && res.get(i).compareTo("vide")==0)
				{
					res.setCouleur("blanc",i);
					liste.remove(combinaisonActuelle.get(i));
				}
			}
		}
		Collections.sort(res,new CompCombinaison());

		return res;
	}
	/**
	 * Changer la combinaison à trouver avec un String  
	 * @param mot  nouvelle combinaison
	 */
	public void convertirC(String mot)
	{
		Map<String ,String> dico =new HashMap<>();
		dico.put("r","rouge");
		dico.put("n","noir");
		dico.put("b","bleu");
		dico.put("v","vert");
		dico.put("w","blanc");
		dico.put("j","jaune");
		String res="";
		for(int i=0;i<4;i++)
		{
			System.out.println(mot.charAt(i)+"");
			this.combinaisonATrouver.set(i,dico.get(mot.charAt(i)+""));
		}
	}
}
