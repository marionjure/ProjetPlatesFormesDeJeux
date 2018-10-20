package src.application;

import java.util.Date;

public class Message implements Comparable<Message>{
    public static final int TYPE_GLOBAL=1, TYPE_PRIVER=2;
    private int id;
    private String contenu;
    private int type;
    private Date date;
    private User emeteur,destinataire;
    private boolean lu;
    public Message(int id, String contenu, int type, Date date, User emeteur,User destinataire,int lecture){
        this.id=id;
        this.contenu=contenu;
        this.type = type;
        this.date = date;
        this.emeteur = emeteur;
        this.destinataire = destinataire;
        this.lu=lecture==1;
    }

    @Override
    public int compareTo(Message message) {
        return date.before(message.date) ? -1:1;
    }

    public int getType() {
        return type;
    }

    public String getContenu() {
        return contenu;
    }

    public User getEmeteur(){
        return emeteur;
    }

    public User getDestinataire() {
        return destinataire;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public int lectureMessage(){
        return this.lu?1:0;
    }
    @Override
    public boolean equals(Object o){
        if (o!=null)
            return this.id == ((Message)o).id;
        return false;
    }
}
