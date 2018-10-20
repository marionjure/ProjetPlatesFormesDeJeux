package src.application;

import java.sql.*;

public class ConnexionMySQL {
    private Connection mysql;
    private boolean connecte=false;

    /**
     * génère une connexion mysql avec la paramètre donné
     * @param nomServeur
     * @param nomBase
     * @param nomLogin
     * @param motDePasse
     */
    public ConnexionMySQL(String nomServeur, String nomBase, String nomLogin, String motDePasse) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connecter(nomServeur,nomBase,nomLogin,motDePasse);
        }catch (ClassNotFoundException e)
        {
            System.out.println("le export du driver jdbc est pas fait /usr/share/java/mysql.jar");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ConnexionMysql échouée");
        }
    }

    /**
     * génère une connexion mysql avec la paramètre donné
     * @param nomServeur
     * @param nomBase
     * @param nomLogin
     * @param motDePasse
     */
    public void connecter(String nomServeur, String nomBase, String nomLogin, String motDePasse) throws SQLException {
        try{
            mysql = DriverManager.getConnection("jdbc:mysql://"+nomServeur+":3306/"+nomBase,nomLogin,motDePasse);
            connecte = true;
        }catch(SQLException e){
            throw e;
        }
    }

    /**
     * ferme une connexion mysql avec la base
     */
    public void close() throws SQLException {
        mysql.close();
        connecte = false;
    }

    /**
     * renvoi l'état de la connexion
     * @return
     */
    public boolean isConnecte(){ return this.connecte;}


    /**
     * renvoit un objet blob
     * @return
     * @throws SQLException
     */
    public Blob createBlob()throws SQLException{
        return this.mysql.createBlob();
    }

    /**
     * renvoit un statement
     * @return
     * @throws SQLException
     */
    public Statement createStatement() throws SQLException {
        return this.mysql.createStatement();
    }

    /**
     * renvoit un preparedstatement
     * @param requete
     * @return
     * @throws SQLException
     */
    public PreparedStatement prepareStatement(String requete) throws SQLException{
        return this.mysql.prepareStatement(requete);
    }
}
