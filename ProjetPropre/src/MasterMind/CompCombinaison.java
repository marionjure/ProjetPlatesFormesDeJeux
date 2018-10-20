package src.MasterMind;

import java.util.ArrayList;
import java.util.Comparator;

public class CompCombinaison implements Comparator<String> {
    public int compare(String c,String o){
        ArrayList<String> liste= new ArrayList<>();
        liste.add("noir");
        liste.add("blanc");
        liste.add("vide");
        return liste.indexOf(c)-liste.indexOf(o);

    }
}
