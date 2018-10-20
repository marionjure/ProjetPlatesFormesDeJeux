package src.application;

import src.Interfaces.Jeux;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ChargeurJeu{
    private String repertoireJar;
    ChargeurJeu(String repertoireJar){
        if (repertoireJar.endsWith("/"))
            this.repertoireJar=repertoireJar;
        else
            this.repertoireJar=repertoireJar+"/";
    }

    @SuppressWarnings("deprecation")
    Jeux chargerJeu(String nomJar, String nomClasse) throws ClassNotFoundException,InstantiationException,IllegalAccessException,MalformedURLException {
        File ficJar = new File(this.repertoireJar+nomJar);
        URL[] listeURL = {ficJar.toURL()};
        ClassLoader loader = new URLClassLoader(listeURL);
        return (Jeux)(Class.forName("src."+nomClasse+"."+nomClasse,true,loader).newInstance());
    }
}
