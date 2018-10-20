package src.application;

import src.Exceptions.JeuInexistantException;
import src.Interfaces.ElementDeListe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Jeu implements ElementDeListe {
    public final static String TYPE_TOURATOUR = "touratour", TYPE_POINT="point", TYPE_TEMPS = "temps", TYPE_INCONNU="inconnu";
    private int id;
    private String nom,description,type;
    private boolean jeuActif;
    private byte[] fichier;


    /**
     * représentation d'un jeu dans la bd
     * @param id
     * @param nom
     * @param description
     * @param type
     * @param fichier
     * @param actif
     */
    public Jeu(int id, String nom, String description, String type, byte[] fichier, boolean actif){
        this.id = id ;
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.fichier = fichier;
        this.jeuActif=actif;

    }

    public Jeu(int id, String nom, String description, String type, byte[] fichier, int actif){
        this(id,nom,description,type,fichier, actif==1);
    }

    public boolean isJeuActif() {
        return jeuActif;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public File getFichierFile(){
        File f = new File(this.getNom());
        try {
            Files.write(f.toPath(), this.fichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public String getType() {
        return type;
    }

    public void setJeuActif(boolean jeuActif) {
        this.jeuActif = jeuActif;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    @Override
    public String toString() {
        return this.nom + this.description;
    }

    @Override
    public String getTexteAffichage() {
        return this.nom;
    }

    /**
     * indique si deux jeu sont les même
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if(o!=null)
            return this.id == ((Jeu) o).id;
        return false;
    }

    @Override
    public void majDansBd() {
        try {
            GLOBALS.REQUETE_BD.updateJeu(this);
        }catch (JeuInexistantException e){
            e.printStackTrace();
        }
    }

    public void creerJar(){
            File file = new File ("src/jar/"+nom+".jar");
            try{
                FileOutputStream writer = new FileOutputStream (file);
                writer.write(this.fichier);
            }catch (FileNotFoundException exception){
                System.out.println ("Le fichier n'a pas été trouvé");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
