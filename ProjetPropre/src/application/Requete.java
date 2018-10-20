package src.application;

import src.Exceptions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Requete {

    public ConnexionMySQL conexion;

    public Requete(ConnexionMySQL c){
        conexion = c;
    }


    public boolean isDemandeAmis(String ad, String user) throws SQLException{
        boolean ok=false;
        try{

            PreparedStatement p = conexion.prepareStatement("select * from AMIS  where use_pseudouser=? and  PSEUDOUSER=?;");
            p.setString(1,ad);
            p.setString(2,user);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                ok=true;
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ok;
    }

    public ArrayList<AmisBD> listeAmie( String user) throws SQLException{
        ArrayList<AmisBD> liste=new ArrayList<AmisBD>();
        try{

            PreparedStatement ps= this.conexion.prepareStatement("select PSEUDOUSER, USE_PSEUDOUSER, ACCEPTATIONAMIS, DATEDEMANDEAMIS from AMIS where  use_PSEUDOUSER=? and ACCEPTATIONAMIS=0");
            ps.setString(1,user);
            ResultSet r=ps.executeQuery();
            while (r.next()){
                //System.out.println("ok");

                //System.out.println(r.getString("PSEUDOUSER"));
                //System.out.println(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));
                liste.add(new AmisBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("ACCEPTATIONAMIS"),null));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return liste;
    }
    //Ajoute
    public void insererAmis(AmisBD amisBD) throws SQLException{
        try{
            System.out.println(amisBD);
            //String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(amisBD.getDATEDEMANDEAMIS());
            PreparedStatement ps= this.conexion.prepareStatement("insert into AMIS(PSEUDOUSER, USE_PSEUDOUSER, ACCEPTATIONAMIS, DATEDEMANDEAMIS) values (?,?,?,NULL);");
            System.out.println(amisBD.getPSEUDOUSER());
            ps.setString(1,amisBD.getPSEUDOUSER());
            System.out.println(amisBD.getUSE_PSEUDOUSER());
            ps.setString(2,amisBD.getUSE_PSEUDOUSER());
            System.out.println(amisBD.getACCEPTATIONAMIS());
            ps.setInt(3,amisBD.getACCEPTATIONAMIS());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insererAmis2(AmisBD amisBD) throws SQLException{
        try{
            System.out.println(amisBD);
            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(amisBD.getDATEDEMANDEAMIS());
            PreparedStatement ps= this.conexion.prepareStatement("insert into AMIS(PSEUDOUSER, USE_PSEUDOUSER, ACCEPTATIONAMIS, DATEDEMANDEAMIS) values (?,?,?,?);");
            System.out.println(amisBD.getPSEUDOUSER());
            ps.setString(1,amisBD.getPSEUDOUSER());
            System.out.println(amisBD.getUSE_PSEUDOUSER());
            ps.setString(2,amisBD.getUSE_PSEUDOUSER());
            System.out.println(amisBD.getACCEPTATIONAMIS());
            ps.setInt(3,amisBD.getACCEPTATIONAMIS());
            ps.setString(4,dateFormater);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void majAmis(AmisBD amisBD)throws SQLException{
        try{
            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(amisBD.getDATEDEMANDEAMIS());
            PreparedStatement ps = conexion.prepareStatement("update AMIS set ACCEPTATIONAMIS=1, DATEDEMANDEAMIS=? where pseudouser=? and use_pseudouser=? ;");

            ps.setString(1,dateFormater);
            ps.setString(2, amisBD.getPSEUDOUSER());
            ps.setString(3,amisBD.getUSE_PSEUDOUSER());
            ps.executeUpdate();}
        catch (SQLException y){y.printStackTrace();}
    }

    public void deleteAmis(AmisBD amisBD)throws SQLException{
        try{
            //String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(amisBD.getDATEDEMANDEAMIS());
            PreparedStatement ps = conexion.prepareStatement("delete from AMIS where pseudouser=? and use_pseudouser=? ;");

            //ps.setString(1,dateFormater);
            ps.setString(1, amisBD.getPSEUDOUSER());
            ps.setString(2,amisBD.getUSE_PSEUDOUSER());
            ps.executeUpdate();}
        catch (SQLException y){y.printStackTrace();}
    }

    public ArrayList<String> listeJoueur(){
        ArrayList<String> nomJeu =new ArrayList<String>();
        try{

            PreparedStatement p = conexion.prepareStatement("select PSEUDOUSER from USER");
            ResultSet r=p.executeQuery();
            while(r.next())
            {
                System.out.println(r.getString("PSEUDOUSER"));
                nomJeu.add(r.getString("PSEUDOUSER"));
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return nomJeu;
    }
    public ArrayList<String> listePasAmis(String user){
        ArrayList<String> nomJeu =new ArrayList<String>();
        try{

            PreparedStatement p = conexion.prepareStatement("select use_PSEUDOUSER from AMIS where use_pseudouser=? and ACCEPTATIONAMIS=1 and pseudouser!=?");
            p.setString(1,user);
            p.setString(2,user);
            ResultSet r=p.executeQuery();
            while(r.next())
            {
                //System.out.println(r.getString("PSEUDOUSER"));
                nomJeu.add(r.getString("use_PSEUDOUSER"));
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println(nomJeu);
        return nomJeu;
    }


    public ArrayList<String> listeMesAmis(String user){
        ArrayList<String> nomJeu =new ArrayList<String>();
        try{

            PreparedStatement p = conexion.prepareStatement("select PSEUDOUSER from AMIS where use_pseudouser=? and ACCEPTATIONAMIS=1 and pseudouser!=?");
            p.setString(1,user);
            p.setString(2,user);
            ResultSet r=p.executeQuery();
            while(r.next())
            {
                //System.out.println(r.getString("PSEUDOUSER"));
                nomJeu.add(r.getString("PSEUDOUSER"));
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println(nomJeu);
        return nomJeu;
    }




    public String nomJeu(int idJeu) throws SQLException{
        String nomJeu ="";
        try{

            PreparedStatement p = conexion.prepareStatement("select NOMJEU from JEU where IDJEU =?");
            p.setInt(1,idJeu);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                nomJeu=r.getString("NOMJEU");
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return nomJeu;


    }
    public String chercherAdversaire(int idJeu,String user) throws SQLException{
        String nomJeu ="";
        try{

            PreparedStatement p = conexion.prepareStatement("select PSEUDOUSER from INVITATION  where IDJEU =? and use_pseudouser=''and PSEUDOUSER!=? ;");
            p.setInt(1,idJeu);
            p.setString(2,user);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                nomJeu=r.getString("PSEUDOUSER");
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return nomJeu;


    }
    public boolean isAdversaire(int idJeu, String user) throws SQLException{
        boolean ok=false;
        try{

            PreparedStatement p = conexion.prepareStatement("select * from INVITATION  where IDJEU =? and use_pseudouser=''and  PSEUDOUSER!=?;");
            p.setInt(1,idJeu);
            p.setString(2,user);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                ok=true;
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ok;


    }
    public void supprimer(InvitationBD invitation) throws SQLException{

        try{

            System.out.println(invitation.getDate());
            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invitation.getDate());
            PreparedStatement ps = this.conexion.prepareStatement("delete from INVITATION where PSEUDOUSER = ? and USE_PSEUDOUSER=? and DATEINVITATION=? and idjeu=?;");
            ps.setString(1,invitation.getUser());
            ps.setString(2,invitation.getUSE_PSEUDOUSER());
            ps.setString(3,dateFormater);
            ps.setInt(4,invitation.getIdjeu());

            ps.executeUpdate();}
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insererParticiper(ParticiperBD participerBD) throws SQLException{
        try{

            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(participerBD.getDATEPARTICIPATION());
            PreparedStatement ps= this.conexion.prepareStatement("insert into PARTICIPER(PSEUDOUSER,USE_PSEUDOUSER,NUMPARTIE,DATEPARTICIPATION) values (?,?,?,?);");
            ps.setString(1,participerBD.getPSEUDOUSER());
            ps.setString(2,participerBD.getUSE_PSEUDOUSER());
            ps.setInt(3,participerBD.getNUMPARTIE());
            ps.setString(4,dateFormater);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }}

    public int maxParticiper() throws SQLException{
        int max=-1;
        try

        {
            Statement s=conexion.createStatement();
            ResultSet r=s.executeQuery("select max(NUMPARTIE) max from PARTICIPER;");
            r.next();
            max=r.getInt("max");
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return max;
    }
    public Date dateInvitation(int idJeu, String user,String adversaire) throws SQLException{
        Date d= new Date();
        try{

            PreparedStatement ps= this.conexion.prepareStatement("select DATEINVITATION from INVITATION where IDJEU=? and PSEUDOUSER=? and USE_PSEUDOUSER=? ");
            ps.setInt(1,idJeu);
            ps.setString(2,user);
            ps.setString(3,adversaire);
            ResultSet r=ps.executeQuery();
            while (r.next()){
                //System.out.println("ok");

                try{
                    d=( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(r.getString("DATEINVITATION"));
                }
                catch (ParseException e) {
                    e.printStackTrace(); }
                //System.out.println(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return d;

    }

    public ArrayList<String> getParticipantParNumPart(int num){
        ArrayList<String> res = new ArrayList<>();
        try{
            PreparedStatement ps =conexion.prepareStatement("SELECT PSEUDOUSER,USE_PSEUDOUSER FROM PARTICIPER WHERE NUMPARTIE=?;");
            ps.setInt(1,num);
            ResultSet rs = ps.executeQuery();
            rs.next();
            res.add(rs.getString(1));
            res.add(rs.getString(2));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<InvitationBD> Invitation(int idJeu, String user) throws SQLException{
        ArrayList<InvitationBD> liste=new ArrayList<InvitationBD>();
        try{

            PreparedStatement ps= this.conexion.prepareStatement("select PSEUDOUSER, USE_PSEUDOUSER, IDJEU, DATEINVITATION from INVITATION where IDJEU=? and USE_PSEUDOUSER=? ");
            ps.setInt(1,idJeu);
            ps.setString(2,user);
            ResultSet r=ps.executeQuery();
            while (r.next()){
                //System.out.println("ok");
                Date d= new Date();
                try{
                    d=( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(r.getString("DATEINVITATION"));
                }
                catch (ParseException e) {
                    e.printStackTrace(); }
                //System.out.println(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));
                liste.add(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return liste;

    }
    public ArrayList<InvitationBD> InvitationTout( String user) throws SQLException{
        ArrayList<InvitationBD> liste=new ArrayList<InvitationBD>();
        try{

            PreparedStatement ps= this.conexion.prepareStatement("select PSEUDOUSER, USE_PSEUDOUSER, IDJEU, DATEINVITATION from INVITATION where  USE_PSEUDOUSER=? ");
            ps.setString(1,user);
            ResultSet r=ps.executeQuery();
            while (r.next()){
                //System.out.println("ok");
                Date d= new Date();
                try{
                    d=( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(r.getString("DATEINVITATION"));
                }
                catch (ParseException e) {
                    e.printStackTrace(); }
                //System.out.println(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));
                liste.add(new InvitationBD(r.getString("PSEUDOUSER"),r.getString("USE_PSEUDOUSER"),r.getInt("IDJEU"),d));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return liste;

    }

    //Fin
//    public User getJoueurParPseudoOuMail(String id) throws JoueurPasTrouverException {
//        try {
//            PreparedStatement ps = conexion.prepareStatement("SELECT PSEUDOUSER,MDPUSER,EMAILUSER,ROLEUSER,ETATUSER FROM `USER` WHERE PSEUDOUSER=? OR EMAILUSER=?;");
//            ps.setString(1,id);
//            ps.setString(2,id);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next()){
//                return new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
//            }else{
//                throw new JoueurPasTrouverException("Le joueur demander n'est pas présent dans la bd");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public void insererInvitation(InvitationBD  invitationBD) throws ExceptionInvitation {
        try{

            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invitationBD.getDate());
            PreparedStatement ps= this.conexion.prepareStatement("insert into INVITATION(PSEUDOUSER,USE_PSEUDOUSER,IDJEU,DATEINVITATION) values (?,?,?,?);");
            ps.setString(1,invitationBD.getUser());
            ps.setString(2,invitationBD.getUSE_PSEUDOUSER());
            ps.setInt(3,invitationBD.getIdjeu());
            ps.setString(4,dateFormater);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ExceptionInvitation();
        }

    }

    public boolean invitationExistante(InvitationBD invitationBD) {
        try{

            String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invitationBD.getDate());
            PreparedStatement ps= this.conexion.prepareStatement("SELECT COUNT(*) FROM INVITATION WHERE PSEUDOUSER=? AND USE_PSEUDOUSER=? AND IDJEU=? AND DATEINVITATION=?;");
            ps.setString(1,invitationBD.getUser());
            ps.setString(2,invitationBD.getUSE_PSEUDOUSER());
            ps.setInt(3,invitationBD.getIdjeu());
            ps.setString(4,dateFormater);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1)==0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * va rechercher le max de l'idPartie
     * @return int derniere partie
     */
    public int maxIdPartie() throws SQLException{
        int max=-1;
        try

        {
            Statement s=conexion.createStatement();
            ResultSet r=s.executeQuery("select max(idpartie) max from PARTIE;");
            r.next();
            max=r.getInt("max");
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return max;
    }


    /**
     * insérer un nouveau coup
     * @param partie la partie du jeu
     */
    public void insererPartie(Partie partie) throws SQLException{
        try{
            int maxPartie=this.maxIdPartie()+1;
            System.out.println(maxPartie);
            String dateFormater = new SimpleDateFormat("yyyy-MM-dd").format(partie.getDatePartie());
            PreparedStatement ps= this.conexion.prepareStatement("insert into PARTIE(idpartie, idjeu, numpartie, datepartie, numetapepartie, actionjoueurpartie, joueuractionpartie, etatpartie, vainqueurpartie) values (?,?,?,?,?,?,?,?,?);");
            ps.setInt(1,maxPartie);
            ps.setInt(2,partie.getIdJeu());
            ps.setInt(5,partie.getNumEtapePartie());
            ps.setInt(3,partie.getNumPartie());
            ps.setString(4,dateFormater);
            ps.setString(6,partie.getActionJoueur());
            ps.setString(7,partie.getJoueurActionPartie());
            ps.setInt(8,partie.getEtatPartie());
            ps.setString(9,partie.getVainqueurPartie());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    /**
     * va rechercher la derniere etape de son adversaire
     * @param num la partie du jeu
     * @return int derniere etat de la partie ,si -1 l'adversaire n'a pas encore jouer
     */

    public String VainqueurPartie(int num) throws SQLException{
        String vainqueur ="";
        try{

            PreparedStatement p = conexion.prepareStatement("select VAINQUEURPARTIE from PARTIE where numpartie=? and NUMETAPEPARTIE=5;");
            p.setInt(1,num);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                vainqueur=r.getString("VAINQUEURPARTIE");
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return vainqueur;


    }
//    public void insererAmis(AmisBD amisBD) throws SQLException{
//        try{
//            String dateFormater="";
//            if(amisBD.getDATEDEMANDEAMIS()!=null){
//                dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(amisBD.getDATEDEMANDEAMIS());
//            }
//
//            System.out.println(dateFormater);
//            PreparedStatement ps= this.conexion.prepareStatement("insert into AMIS(PSEUDOUSER,USE_PSEUDOUSER,ACCEPTATIONAMIS , DATEDEMANDEAMIS ) values (?,?,?,NULL);");
//            ps.setString(1,amisBD.getPSEUDOUSER());
//            ps.setString(2,amisBD.getUSE_PSEUDOUSER());
//            ps.setInt(3,amisBD.getACCEPTATIONAMIS());
//
//            //ps.setString(4,dateFormater);
//            ps.executeUpdate();
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
    /**
     * va rechercher la derniere etape de son adversaire
     * @param j1 la partie du jeu
     * @param  j2  le joueur qui joue par son adversire
     * @return int derniere etat de la partie ,si -1 l'adversaire n'a pas encore jouer
     */
    // numetapePartie3
    public int  numPartie(String j1,String j2) throws SQLException{
        int max=-1;
        try{

            PreparedStatement p = conexion.prepareStatement("select  numpartie from PARTICIPER where PSEUDOUSER=? and USE_PSEUDOUSER=? and  DATEPARTICIPATION=(SELECT MAX(DATEPARTICIPATION) FROM PARTICIPER WHERE PSEUDOUSER=? and USE_PSEUDOUSER=?)");
            p.setString(1,j1);
            p.setString(2,j2);
            p.setString(3,j1);
            p.setString(4,j2);
            ResultSet r=p.executeQuery();
            if(r.next())
            {
                max=r.getInt("numpartie");
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return max;


    }

    //MaxNumPartie
    public int numEtapeJoueur(int num ,String user) throws SQLException{
        String joueur="";
        int numP=0;
        try
        {

            PreparedStatement p = conexion.prepareStatement("select IFNULL(NUMETAPEPARTIE,0) num from PARTIE where NUMPARTIE=? and NUMETAPEPARTIE =( select max(NUMETAPEPARTIE) from PARTIE where JOUEURACTIONPARTIE=? and NUMPARTIE=?) ; ");
            p.setInt(1,num);
            p.setString(2,user);
            p.setInt(3,num);
            ResultSet r=p.executeQuery();
            if(r.next())
            {
                numP=r.getInt("num");

            }
            else{
                numP=0;
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return numP;

    }
    //NumEtapePartieJoueur

    public String  actionJoueurPartie(int num ,String user,int numP) throws SQLException{
        String coup="";
        try
        {
            PreparedStatement p = conexion.prepareStatement("select ACTIONJOUEURPARTIE from PARTIE where NUMPARTIE=? and NUMETAPEPARTIE=? and JOUEURACTIONPARTIE=? ; ");
            p.setInt(1,num);
            p.setInt(2,numP);
            p.setString(3,user);

            ResultSet r=p.executeQuery();
            if(r.next())
            {
                coup=r.getString("ACTIONJOUEURPARTIE");

            }
            else{
                coup="erreur";
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return coup;
    }
    public String   coupPreMasterMind(int num ,String user,String adver) throws SQLException{
        int joueur1=this.numEtapeJoueur(num,user);
        int joueur2= this.numEtapeJoueur(num,adver);
        String etat="";
        if (joueur1<joueur2){
            System.out.println(joueur1<joueur2);
            try
            {
                PreparedStatement p = conexion.prepareStatement("select  ACTIONJOUEURPARTIE from PARTIE where NUMPARTIE=? and  NUMETAPEPARTIE=? and JOUEURACTIONPARTIE=? ; ");
                p.setInt(1,num);
                p.setInt(2,joueur1+1);
                System.out.println(joueur1+1);
                p.setString(3,adver);

                ResultSet r=p.executeQuery();
                if(r.next()){
                    etat= r.getString("ACTIONJOUEURPARTIE");
                }
                else{
                    etat= "ok";
                }
                r.close();

            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        else{
            etat= "ok";

        }
        System.out.println(etat+"etat");
        return etat;

    }

    public void  abandon(Partie partie) throws SQLException{

        PreparedStatement p = conexion.prepareStatement("update PARTIE set etatpartie=-2  where numpartie=? and JOUEURACTIONPARTIE = ?;");
        p.setInt(1,partie.getNumPartie());
        p.setString(2,partie.getJoueurActionPartie());
        p.executeUpdate();


    }
    public Partie partie(int numpartie,int idJeu,String joueur)throws SQLException{
        //Statement s=conexion.createStatement();
        Partie pa=null;
        try{
//                Statement s=conexion.createStatement();
            PreparedStatement p = conexion.prepareStatement("select idjeu, numpartie, datepartie, numetapepartie, actionjoueurpartie, joueuractionpartie, etatpartie, vainqueurpartie from PARTIE where numpartie=? and  ACTIONJOUEURPARTIE=?;");

            p.setInt(1,numpartie);
            p.setString(2,this.nbCoup(numpartie,joueur));
            ResultSet r=p.executeQuery();

            if(r.next()){

                Date d= new Date();
                try{
                    d=( new SimpleDateFormat("yyyy-MM-dd")).parse(r.getString("datepartie"));
                }
                catch (ParseException e) {
                    e.printStackTrace(); }
                pa=new Partie(idJeu,numpartie,d, r.getInt("numetapepartie"),r.getString("actionjoueurpartie"), r.getString("joueuractionpartie"), r.getInt("etatpartie"),r.getString("vainqueurpartie"));

                r.close();
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return pa;

    }
    public boolean isEnCours(int numpartie,String joueur)throws SQLException{
        //Statement s=conexion.createStatement();
        boolean ok=false;
        try{
//                Statement s=conexion.createStatement();
            PreparedStatement p = conexion.prepareStatement("select * from PARTIE where numpartie=? and JOUEURACTIONPARTIE=?;");

            p.setInt(1,numpartie);
            p.setString(2,joueur);
            ResultSet r=p.executeQuery();

            if(r.next()){
                ok=true;


            }
            else{ok=false;}
            r.close();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return ok;

    }
    public String nbCoup(int numpartie,String joueur)throws SQLException{
        //Statement s=conexion.createStatement();
        String res="";
        try{
//                Statement s=conexion.createStatement();
            PreparedStatement p = conexion.prepareStatement("select ACTIONJOUEURPARTIE from PARTIE where numpartie=? and JOUEURACTIONPARTIE=? and  NUMETAPEPARTIE =(select max(NUMETAPEPARTIE) from PARTIE where numpartie=? and JOUEURACTIONPARTIE=?);");

            p.setInt(1,numpartie);
            p.setString(2,joueur);
            p.setInt(3,numpartie);
            p.setString(4,joueur);
            ResultSet r=p.executeQuery();

            if(r.next()){
                res= r.getString("ACTIONJOUEURPARTIE");
            }

            r.close();

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return res;

    }
    //F

    /**
     * va rechercher tout les utilisateur dans la bd
     * @return Arraylist de user
     */
    public ArrayList<User> getListeUtilisateur(){
        ArrayList<User> res = new ArrayList<>();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT PSEUDOUSER,MDPUSER,EMAILUSER,ROLEUSER,ETATUSER FROM USER;");
            while (rs.next()){
                User usr = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                res.add(usr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * renvoit la liste de tout les jeu
     * @return
     */
    public ArrayList<Jeu> getListeJeu(){
        ArrayList<Jeu> res = new ArrayList<>();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT * FROM JEU;");
            while (rs.next()){
                Jeu jeu = new Jeu(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBytes(5),rs.getInt(6));
                res.add(jeu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public Jeu getJeu(int numJeu){
        try{
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM JEU WHERE IDJEU=?");
            ps.setInt(1, numJeu);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new Jeu(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBytes(5),rs.getInt(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * va rechercher tout les utilisateur non banni dans la bd
     * @return Arraylist de user
     */
    public ArrayList<User> getListeUtilisateurActif(){
        ArrayList<User> res = new ArrayList<>();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT PSEUDOUSER,MDPUSER,EMAILUSER,ROLEUSER,ETATUSER FROM USER WHERE ETATUSER=1;");
            while (rs.next()){
                User usr = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
                res.add(usr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public byte[] chargerIcone(User u){
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("SELECT ICONE FROM USER WHERE PSEUDOUSER=?;");
            ps.setString(1,u.getPseudo());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBytes(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * renvoi la liste des jeu actifs
     * @return
     */
    public ArrayList<Jeu> getListeJeuActif(){
        ArrayList<Jeu> res = new ArrayList<>();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT * FROM JEU WHERE ETATJEU=1;");
            while (rs.next()){
                Jeu jeu = new Jeu(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBytes(5),rs.getInt(6));
                res.add(jeu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean joueurExiste(User j){
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT COUNT(*) FROM USER WHERE PSEUDOUSER=? OR EMAILUSER=?;");
            ps.setString(1,j.getPseudo());
            ps.setString(2,j.getMail());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1)==0)
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }

    public void addJoueur(User j) throws JoueurDejaExistantException {
        if (joueurExiste(j))
            throw new JoueurDejaExistantException("Le joueur "+j.getPseudo()+"existe déjà dans la base de donnée il ne peut pas être ajouter");
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO `USER`(`PSEUDOUSER`, `MDPUSER`, `EMAILUSER`, `ROLEUSER`, `ETATUSER`, `ICONE`) VALUES (?,?,?,?,?,?);");
            ps.setString(1,j.getPseudo());
            ps.setString(2,j.getMdp());
            ps.setString(3,j.getMail());
            ps.setString(4,j.getRole());
            ps.setInt(5,j.getActifInt());

            File f = new File(GLOBALS.LOGO_ICON);
            Blob b = conexion.createBlob();
            b.setBytes(1, Files.readAllBytes(f.toPath()));
            ps.setBlob(6, b);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxNumJeu(){
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT MAX(IDJEU) FROM JEU;");
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * ajoute un objet jeu a la bd
     * @param j
     * @throws JoueurDejaExistantException
     */
    public void addJeu(Jeu j) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO JEU VALUES (?,?,?,?,?,?);");
            ps.setInt(1,getMaxNumJeu()+1);
            ps.setString(2,j.getNom());
            ps.setString(3,j.getDescription());
            ps.setString(4,j.getType());
            Blob b = conexion.createBlob();
            b.setBytes(1, j.getFichier());
            ps.setBlob(5,b);
            ps.setInt(6,j.isJeuActif()?1:0);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean jeuExiste(Jeu j){
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT COUNT(*) FROM JEU WHERE IDJEU=?");
             ps.setInt(1, j.getId());
             ResultSet rs = ps.executeQuery();
             rs.next();
             return rs.getInt(1)>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * permet de récupérer un jouer par un id et son mot de passe
     * @param id
     * @param mdp
     * @return
     * @throws JoueurPasTrouverException
     */
    public User getJoueur(String id, String mdp) throws JoueurPasTrouverException {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT PSEUDOUSER,MDPUSER,EMAILUSER,ROLEUSER,ETATUSER FROM `USER` WHERE MDPUSER=? AND "+(id.contains("@") ? "EMAILUSER":"PSEUDOUSER")+"=?;");
            ps.setString(1,mdp);
            ps.setString(2,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
            }else{
                throw new JoueurPasTrouverException("Le joueur demander n'est pas présent dans la bd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * renvoit un joueur sans se préocuper de son mot de passe
      * @param id
     * @return
     * @throws JoueurPasTrouverException
     */
    public User getJoueurParPseudoOuMail(String id) throws JoueurPasTrouverException {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT PSEUDOUSER,MDPUSER,EMAILUSER,ROLEUSER,ETATUSER FROM `USER` WHERE PSEUDOUSER=? OR EMAILUSER=?;");
            ps.setString(1,id);
            ps.setString(2,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5));
            }else{
                throw new JoueurPasTrouverException("Le joueur demander n'est pas présent dans la bd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**

     * met a jour le joueur passe en paramètre dans la bd
     * @param u
     */
    public void updateJoueur(User u){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE USER SET ROLEUSER=?, ETATUSER=? WHERE PSEUDOUSER=?;");
            ps.setString(1,u.getRole());
            ps.setInt(2,u.getActifInt());
            ps.setString(3,u.getPseudo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    /**
     * met a jour le joueur passe en paramètre dans la bd
     * @param u
     */
    public void updateJoueurPseudoMdp(User u){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE USER SET MDPUSER=? WHERE PSEUDOUSER=?;");
            ps.setString(1,u.getMdp());
            ps.setString(2,u.getPseudo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateJoueurIcone(User u){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE USER SET ICONE=? WHERE PSEUDOUSER=?;");
            Blob b = conexion.createBlob();
            b.setBytes(1, u.getIcone());
            ps.setBlob(1,b);

            ps.setString(2,u.getPseudo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAmis(User u){
        ArrayList<User> res = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("SELECT PSEUDOUSER,USE_PSEUDOUSER FROM AMIS WHERE ACCEPTATIONAMIS=1 AND (PSEUDOUSER=? OR USE_PSEUDOUSER=?);");
            ps.setString(1, u.getPseudo());
            ps.setString(2, u.getPseudo());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if(u.getPseudo().equals(rs.getString(1))) {
                    User us = getJoueurParPseudoOuMail(rs.getString(2));
                    if(!res.contains(us)) {
                        res.add(us);
                    }
                }else{
                    User us = getJoueurParPseudoOuMail(rs.getString(1));
                    if(!res.contains(us)) {
                        res.add(us);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
        }
        return res;

    }

    /**
     * met a jour le joueur passe en paramètre dans la bd
     * @param j
     */
    public void updateJeu(Jeu j) throws JeuInexistantException {
        if(jeuExiste(j)) {
            try {
                PreparedStatement ps = conexion.prepareStatement("UPDATE JEU SET NOMJEU=?,DESCRIPTIONJEU=?, TYPEJEU=?, FICHIERJEU=?, ETATJEU=? WHERE IDJEU=?;");
                ps.setString(1, j.getNom());
                ps.setString(2, j.getDescription());
                ps.setString(3, j.getType());
                Blob b = conexion.createBlob();
                b.setBytes(1, j.getFichier());
                ps.setBlob(4, b);
                ps.setInt(5, j.isJeuActif() ? 1 : 0);
                ps.setInt(6, j.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            throw new JeuInexistantException();
        }


    }

    public void deleteJeu(Jeu j){
        try {
            PreparedStatement ps1 = conexion.prepareStatement("DELETE FROM INVITATION WHERE IDJEU=?;");
            ps1.setInt(1, j.getId());
            ps1.executeUpdate();
            ps1 = conexion.prepareStatement("DELETE FROM PARTIE WHERE IDJEU=?");
            ps1.setInt(1, j.getId());
            ps1.executeUpdate();
            ps1 = conexion.prepareStatement("DELETE FROM JEU WHERE IDJEU=?");
            ps1.setInt(1,j.getId());
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * renvoit la date de la dernière partie
     * @return
     * @throws PasDePartieException
     */
    public Date getDateMaxPartie() throws PasDePartieException{
        Date d=new Date();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT MAX(DATEPARTIE) FROM PARTIE;");
            if(!rs.next())
                throw new PasDePartieException();
            d =  (new SimpleDateFormat("yyyy-MM-dd")).parse(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * renvoit la date de la dernière partie
     * @return
     * @throws PasDePartieException
     */
    public Date getDateMinPartie() throws PasDePartieException {
        Date d=new Date();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT MIN(DATEPARTIE) FROM PARTIE;");
            if(!rs.next())
                throw new PasDePartieException();
            d =  (new SimpleDateFormat("yyyy-MM-dd")).parse(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * renvoit le nombre de partie d'un a une date donnée
     * @param d
     * @param j
     * @return
     */
    public int statNbJeuParDate(Date d, Jeu j){
        String dateFormater = new SimpleDateFormat("yyyy-MM-dd").format(d);
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT DATEPARTIE,COUNT(DISTINCT NUMPARTIE) cpt FROM PARTIE WHERE DATEPARTIE=? AND IDJEU=? GROUP BY DATEPARTIE;");
            ps.setString(1,dateFormater);
            ps.setInt(2 ,j.getId());
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("cpt");
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * renvoit le nombre total de partie effectuée a un jeu
     * @param j
     * @return
     */
    public int statNbPartieParJeu(Jeu j){
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("SELECT COUNT(DISTINCT NUMPARTIE) FROM PARTIE WHERE IDJEU=?;");
            ps.setInt(1, j.getId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * renvoit le nombre de partie en cour pour un jeu donner
     * @param j
     * @return
     */
    public int statNbPartieEnCourPourJeu(Jeu j){
        int cpt = 0 ;
        try {
            PreparedStatement ps =ps = conexion.prepareStatement("SELECT NUMPARTIE,SUM(ETATPARTIE) s FROM PARTIE WHERE IDJEU=? GROUP BY NUMPARTIE HAVING s=0;");
            ps.setInt(1,j.getId() );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cpt++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpt;
    }


    /**
     * renvoit la liste des message qui sont globeaux
     * @return
     */
    public ArrayList<Message> tchatListeMessageGlobaux(){
        ArrayList<Message> messageGlob = new ArrayList<>();
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT * FROM MESSAGE NATURAL JOIN ENVOYER WHERE USE_PSEUDOUSER='';");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()){
                messageGlob.add(new Message(rs.getInt(1),rs.getString(2),Message.TYPE_GLOBAL,df.parse(rs.getString(3)),getJoueurParPseudoOuMail(rs.getString(4)),null,rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("pase des datetime beuger dans tchatListeGlobaux");
        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
            System.out.println("pas de jouer beuger dans tchatListeGlobaux");
        }
        return messageGlob;
    }


    /**
     * renvoit la liste des message qui sont priver a un utilisateur
     * @return
     */
    public ArrayList<Message> tchatListeMessagePriver(User u){
        ArrayList<Message> messageGlob = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM MESSAGE NATURAL JOIN ENVOYER WHERE USE_PSEUDOUSER!='' AND (PSEUDOUSER=? OR USE_PSEUDOUSER=?);");
            ps.setString(1,u.getPseudo());
            ps.setString(2,u.getPseudo());
            ResultSet rs = ps.executeQuery();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (rs.next()){
                messageGlob.add(new Message(rs.getInt(1),rs.getString(2),Message.TYPE_PRIVER,df.parse(rs.getString(3)),getJoueurParPseudoOuMail(rs.getString(4)),getJoueurParPseudoOuMail(rs.getString(5)),rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("pase des datetime beuger dans tchatListeGlobaux");
        } catch (JoueurPasTrouverException e) {
            e.printStackTrace();
            System.out.println("pas de jouer beuger dans tchatListeGlobaux");
        }
        return messageGlob;
    }

    /**
     * renvoit l'id maximum des messages
     * @return
     */
    public int tchatMaxIdMessage(){
        int res =-1 ;
        try {
            ResultSet rs = conexion.createStatement().executeQuery("SELECT IFNULL(MAX(IDMSG),-1) FROM MESSAGE;");
            rs.next();
            res=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * ajoute un message a la base de donnée
     * @return
     */
    public void tchatAddMessage(Message msg){
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PreparedStatement psM = conexion.prepareStatement("INSERT INTO MESSAGE VALUES(?,?,?);");
            PreparedStatement psE = conexion.prepareStatement("INSERT INTO ENVOYER VALUES(?,?,?,?);");
            psM.setInt(1, msg.getId());
            psM.setString(2, msg.getContenu());
            psM.setString(3, df.format(msg.getDate()));

            psE.setString(1, msg.getEmeteur().getPseudo());
            psE.setString(2, msg.getDestinataire().getPseudo());
            psE.setInt(3, msg.getId());
            psE.setInt(4, msg.lectureMessage());


            psM.executeUpdate();
            psE.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * indique que un message est lu dans la bd
     */
    public void tchatLitMessage(Message m){
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE ENVOYER SET LECTUREMESSAGE=1 WHERE IDMSG=?;");
            ps.setInt(1, m.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * donne le nombre de parties jouées en fonction d'un jeu
     */

    public int getNombrePartiesJouéesSurJeu(String nomJoueur,int idJeu){
        try{
            PreparedStatement ps = conexion.prepareStatement("select count(PSEUDOUSER) from PARTICIPER natural join PARTIE where (PSEUDOUSER=? or USE_PSEUDOUSER=?) and ETATPARTIE=-1 and IDJEU=?;");
            ps.setString(1, nomJoueur);
            ps.setString(2, nomJoueur);
            ps.setInt(3, idJeu);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * donne le nombre de parties gagnées en  fonction d'un jeu
     */
    public int getNombrePartiesGagnéesSurJeu(String nomJoueur,int idJeu){
        try{
            PreparedStatement ps = conexion.prepareStatement("select count(PSEUDOUSER) from PARTICIPER natural join PARTIE where (PSEUDOUSER=? or USE_PSEUDOUSER=?) and VAINQUEURPARTIE=? and IDJEU=?;");
            ps.setString(1, nomJoueur);
            ps.setString(2, nomJoueur);
            ps.setString(3, nomJoueur);
            ps.setInt(4, idJeu);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * donne les parties en cours en fonction d'un joueur
     */
    public ArrayList<Integer> getPartiesEnCours(String nomJoueur){
        ArrayList<Integer> res=new ArrayList<Integer>();
        try{
            PreparedStatement ps = conexion.prepareStatement("select  distinct(NUMPARTIE) numPartie from PARTIE natural join PARTICIPER where (PSEUDOUSER=? or USE_PSEUDOUSER=?)  and NUMPARTIE NOT IN(select distinct(NUMPARTIE) from PARTIE where ETATPARTIE<0);");
            ps.setString(1, nomJoueur);
            ps.setString(2, nomJoueur);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                res.add(rs.getInt(1));}
            return res;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }


    /**
     * donne lne nom du jeu en fonction du numPartie
     */
    public String getNomJeu(int numPartie){
        try{
            PreparedStatement ps = conexion.prepareStatement(" select NOMJEU from PARTIE natural join JEU where NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getNomAdversaire(int numPartie, String pseudoUser){
        try{
            PreparedStatement ps = conexion.prepareStatement("SELECT PSEUDOUSER,USE_PSEUDOUSER FROM PARTICIPER WHERE NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs=ps.executeQuery();
            rs.next();
            if(rs.getString(1).equals(pseudoUser))
                return rs.getString(1);
            return rs.getString(2);


        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public String getDatePartie(int numPartie){
        try{
            PreparedStatement ps = conexion.prepareStatement("SELECT DATEPARTICIPATION FROM PARTICIPER WHERE NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * donne la description du jeu en fonction du numPartie
     */
    public String getDescriptionJeu(int numPartie){
        try{
            PreparedStatement ps = conexion.prepareStatement("select DESCRIPTIONJEU from JEU natural join PARTIE where NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public boolean isInsererInvitation(InvitationBD  invitationBD){
        boolean ok= false;
        try{
            // String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(invitationBD.getDate());
            PreparedStatement p = conexion.prepareStatement("select * from INVITATION where PSEUDOUSER =? and USE_PSEUDOUSER=? and IDJEU=? ;");
            //System.out.println(invitationBD.getUser());
            p.setString(1,invitationBD.getUser());
            p.setString(2,invitationBD.getUSE_PSEUDOUSER());
            p.setInt(3,invitationBD.getIdjeu());

            ResultSet r=p.executeQuery();
            if(r.next()){
                ok=true;
            }
            else{
                ok=false;
            }

        }
        catch (SQLException e)
        {
            return false;
        }
        System.out.println(ok);
        return ok;

    }

    /**
     * donne l"id du jeu en fonction du numPartie
     */
    public int getIdJeu(int numPartie){
        try{
            PreparedStatement ps = conexion.prepareStatement("select IDJEU from JEU natural join PARTIE where NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }


    /********************************************************************************************< Mastermind> ***********************************************************************************************/
    /**
     * va rechercher le max de l'idPartie
     * @return int derniere partie
     */
    public int getEtatAdverse(String adversaire,Partie partie){
        int res = 0;
        try
        {
            PreparedStatement p = conexion.prepareStatement("select ETATPARTIE from PARTIE where JOUEURACTIONPARTIE = ? and NUMPARTIE = ?");
            p.setString(1,adversaire);
            p.setInt(2,partie.getNumPartie());
            ResultSet r=p.executeQuery();
            if (r.next()){
                res = r.getInt("ETATPARTIE");
            }
            else{
                return res;
            }
            r.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return res;
    }



/********************************************************************************************</Mastermind> ***********************************************************************************************/

/********************************************************************************************<FourInARow> ***********************************************************************************************/

    /**
     * va rechercher la derniere etape de son adversaire
     *
     * @param j1 la partie du jeu
     * @param j2 le joueur qui joue par son adversire
     * @return int derniere etat de la partie ,si -1 l'adversaire n'a pas encore jouer
     */
    public int numetapePartie3(String j1, String j2) throws SQLException {
        int max = -1;
        try {

            PreparedStatement p = conexion.prepareStatement("select  numpartie from PARTICIPER where PSEUDOUSER=? and USE_PSEUDOUSER=? and  DATEPARTICIPATION=(SELECT MAX(DATEPARTICIPATION) FROM PARTICIPER WHERE PSEUDOUSER=? and USE_PSEUDOUSER=?)");
            p.setString(1, j1);
            p.setString(2, j2);
            p.setString(3, j1);
            p.setString(4, j2);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                max = r.getInt("numpartie");
            }
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }

    /**
     * Nous permet de recupérer le dernier coup d'un partie
     *
     * @param numPartie, l'identifiant d'une partie
     * @return un couple qui contient 2 Strings, le Pseudo du joueur et le coup
     * qu'il a efféctué
     */
    public Couple<String, String> getCoupPrecP4(int numPartie) {
        String res = "";
        String res2 = "";
        try {
            //System.out.println(numPartie + " getCoupPrecP4 REQUETE");
            PreparedStatement p = conexion.prepareStatement("SELECT JOUEURACTIONPARTIE, ACTIONJOUEURPARTIE FROM PARTIE WHERE IDPARTIE IN (SELECT MAX(IDPARTIE) FROM PARTIE WHERE NUMPARTIE=?)");
            p.setInt(1, numPartie);
            ResultSet r = p.executeQuery();
            if (r.next()) {
                res = r.getString(1);
                res2 = r.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("nom:" + res + ";coup:" + res2);
        return new Couple<String, String>(res, res2);
    }


    /**
     * Récupère une partie encore en cours
     *
     * @param numPartie, un Entier
     * @return Arraylist<String>, une liste de String
     */
    public ArrayList<String> getPartieEnCours(int numPartie) {
        boolean terminer = this.getGagneOuAbandon(numPartie);
        ArrayList<String> resultat = new ArrayList<String>();
        if (terminer == true) {
            try {
                PreparedStatement ps = this.conexion.prepareStatement(" SELECT JOUEURACTIONPARTIE,ACTIONJOUEURPARTIE FROM PARTIE WHERE NUMPARTIE=? ORDER BY IDPARTIE ASC");
                ps.setInt(1, numPartie);
                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    resultat.add(res.getString(2));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultat;
    }

    /**
     * Nous permet de savoir si l'on sait une partie est gagné ou abandonnée
     *
     * @param numPartie, un Entier
     * @return un booléen
     */
    public boolean getGagneOuAbandon(int numPartie) {
        boolean terminer = false;
        Couple coupleDernierCoup = this.getCoupPrecP4(numPartie);
        if (!coupleDernierCoup.getElem2().equals("NULL")) {
            terminer = true;
        }
        return terminer;
    }

    /**
     * Nous permet de récupérer les Pseudos des joueur qui joue à une partie toujours en cours
     *
     * @param numPartie, un Entier
     * @return un Couple<String,String>
     */
    public Couple<String, String> getPseudoPartieEnCours(int numPartie) {
        Couple<String, String> listeJoueur = new Couple<String, String>("", "");
        try {
            PreparedStatement ps = this.conexion.prepareStatement("SELECT PSEUDOUSER,USE_PSEUDOUSER FROM PARTICIPER WHERE NUMPARTIE=?;");
            ps.setInt(1, numPartie);
            ResultSet rs = ps.executeQuery();
            rs.next();
            listeJoueur = new Couple(rs.getString(1), rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeJoueur;
    }

    /**
     * Récupère la date d'une partie en Cours
     *
     * @param numPartie, Un Entier
     * @return une Date
     */
    public Date getDateEnCours(int numPartie) {
        Date d = new Date();
        try {
            PreparedStatement ps = this.conexion.prepareStatement("SELECT DATEPARTICIPATION FROM PARTICIPER WHERE NUMPARTIE=?");
            ps.setInt(1, numPartie);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String s = rs.getString(1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                d = sdf.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * Nous permet de récupérer un id d'un Jeu
     *
     * @param numPartie, Un Entier
     * @return un Entier
     */
    public int getIDJeu(int numPartie) {
        int res = -1;
        try {
            PreparedStatement ps = this.conexion.prepareStatement("SELECT DISTINCT IDJEU FROM PARTIE NATURAL JOIN JEU WHERE NUMPARTIE=?");
            ps.setInt(1, numPartie);
            ResultSet rs = ps.executeQuery();
            rs.next();
            res = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("getIDJeu() DANS REQUETE : " + res);
        return res;
    }


    /**
     * va rechercher le max du numpartie dans participer
     *
     * @return int derniere numpartie d'un jeu ,-1 si aucune partie exciter pour ce jour
     */
    public int maxIdPartieJeu() throws SQLException {
        int max = -1;
        try {
            PreparedStatement p = conexion.prepareStatement("select IFNULL(max(NUMPARTIE),-1) FROM PARTICIPER;");
            ResultSet r = p.executeQuery();
            if (r.next()) {
                max = r.getInt(1);
            }
            r.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }

    public void insertPseudoPartie(User j1, User j2, int numPartie) throws SQLException {
        String dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        PreparedStatement p = conexion.prepareStatement("insert into PARTICIPER values(?,?,?,?)");
        p.setString(1, j1.getPseudo());
        p.setString(2, j2.getPseudo());
        p.setInt(3, numPartie);
        p.setString(4, dateFormater);
        p.executeUpdate();
    }



    /**
     * Permet de savoir si une Partie est en cour ou nom
     *
     * @param numPart, le numPartie
     * @return un booléean
     */
    public boolean partieEncour(int numPart) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT MIN(ETATPARTIE) FROM PARTIE WHERE NUMPARTIE=?;");
            ps.setInt(1, numPart);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Permet de savoir si une Partie est abandonnée ou non
     *
     * @param numPart, un Entier
     * @return un booléen
     */
    public boolean abandonPartie(int numPart) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT MIN(ETATPARTIE) FROM PARTIE WHERE NUMPARTIE=?;");
            ps.setInt(1, numPart);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return -2 == rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



/********************************************************************************************<FourInARow> ***********************************************************************************************/

}
