package src.application;

import java.util.Date;
import java.lang.*;
public class AmisBD {
    private String PSEUDOUSER;
    private String USE_PSEUDOUSER;
   private int ACCEPTATIONAMIS;
   private Date DATEDEMANDEAMIS;
   public AmisBD(String PSEUDOUSER, String USE_PSEUDOUSER, int ACCEPTATIONAMIS, Date DATEDEMANDEAMIS){
       this.ACCEPTATIONAMIS=ACCEPTATIONAMIS;
       this.DATEDEMANDEAMIS=DATEDEMANDEAMIS;
       this.PSEUDOUSER=PSEUDOUSER;
       this.USE_PSEUDOUSER=USE_PSEUDOUSER;
   }

    public void setUSE_PSEUDOUSER(String USE_PSEUDOUSER) {
        this.USE_PSEUDOUSER = USE_PSEUDOUSER;
    }

    public String getUSE_PSEUDOUSER() {
        return USE_PSEUDOUSER;
    }

    public Date getDATEDEMANDEAMIS() {
        return DATEDEMANDEAMIS;
    }

    public String getPSEUDOUSER() {
        return PSEUDOUSER;
    }

    public int getACCEPTATIONAMIS() {
        return ACCEPTATIONAMIS;
    }

    @Override
    public String toString() {
        return  this.ACCEPTATIONAMIS+" ACCEPTATIONAMIS \n"+ this.DATEDEMANDEAMIS+" DATEDEMANDEAMIS \n"+ this.PSEUDOUSER +" PSEUDOUSER \n "+ this.USE_PSEUDOUSER+" USE_PSEUDOUSER;";
    }
}
