package src.MasterMind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Biblio {
	/**
	 * permet obtenir le nombre d'essai à partir une chaine
	 * @param res l'action d'un joueur "coup/nbEssai/temps" 
	 * @return le nombre d'essai
	 */
    public static Integer nbEssai(String res){
        int debut= res.indexOf("/");
        int fin=res.lastIndexOf("/");
		return Integer.parseInt(res.substring(debut+1,fin));
    }
    /**
	 * permet obtenir le coup à partir une chaine
	 * @param res l'action d'un joueur "coup/nbEssai/temps"  => "rvrb/10/hh:mm:ss"
	 * @return le coup 
	 */
    public static String coup(String res){
        int debut= res.indexOf("/");
        return res.substring(0,debut);
    }
     /**
	 * permet obtenir le temps à partir une chaine
	 * @param res l'action d'un joueur "coup/nbEssai/temps" 
	 * @return le temps(String) 
	 */
    public static String temps(String res){
        int fin=res.lastIndexOf("/");
        return res.substring(fin+1);
    }
     /**
	 * Transforme la chaine temps en une date 
	 * @param res le temps "hh:mm:ss"
	 * @return le temps
	 */
    public static Date dateTemps(String res) throws ParseException{
        Date dateUser= new Date();
        try{
            dateUser=( new SimpleDateFormat("hh:mm:ss")).parse(Biblio.temps(res));
        }
        catch (ParseException e) {
            e.printStackTrace(); }
        return dateUser;
    }
    /**
	 * Transforme un String en une date 
	 * @param res la date "yyyy-MM-dd"
	 * @return la date  
	 */
    public static Date dateDate(String res) throws ParseException{
        Date dateUser= new Date();
        try{
            dateUser=( new SimpleDateFormat("yyyy-MM-dd")).parse(res);
        }
        catch (ParseException e) {
            e.printStackTrace(); }
        return dateUser;
    }

}
