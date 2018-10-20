package src.application;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class GLOBALS {

    public static final int     LOGIN_WIDTH          = 700;
    public static final int     LOGIN_HEIGHT         = 400;
    public static final int     LOGIN_CONTENT_HEIGHT = 370;
    public static final int     LOGIN_SUBMIT_HEIGHT  = 30;

    public static final int     MAINACTIVITY_WIDTH   = 1200;
    public static final int     MAINACTIVITY_HEIGHT  = 800;
    public static final String  MAINACTIVITY_TITLE   = "Plateforme de jeux";

    public static final int     TCHAT_WIDTH          = (int)(MAINACTIVITY_WIDTH * 0.25);
    public static final int     TCHAT_HEIGHT         = (int)(0.8*MAINACTIVITY_HEIGHT);
    public static final int     TCHAT_CONTENT_HEIGHT = (int)(0.8*TCHAT_HEIGHT);

    public static final String  LOGO_ICON            = "img/logo.png";
    public static final String  ADMIN_JEU_TITLE      = "Jeux";
    public static final String  PROFILE_ICON         = "img/profile.png";
    public static final String  ADMIN_JEU_ICON       = "img/manetteIcon.png";
    public static final String  ADMIN_ADMIN_ICON     = "img/dbIcon.png";
    public static final String  VALIDER2_ICON        = "img/valider2.png";
    public static final String  JEU_TITLE            = "Jeux";
    public static final String  PROFILE_TITLE        = "Profil";
    public static final String  ADMIN_TITLE          = "Administration";
    public static final int     ADMIN_BORDER_WHIDHT  = (int)(0.375*MAINACTIVITY_WIDTH);
    public static final int     ADMIN_CENTER_WIDHT   = (int)(0.25*MAINACTIVITY_WIDTH);
    public static final int     ADMIN_TAB_HEIGHT     = MAINACTIVITY_HEIGHT;


    public static final double  P4_WIDTH             = 0.13*MAINACTIVITY_WIDTH;
    public static final double  P4_HEIGHT            = 0.18*MAINACTIVITY_HEIGHT;



    public static final int     TITLE_SIZE           = 20;
    public static final int     SPACING_VALUE        = 10;
    public static final Insets  PADDING_VALUE        = new Insets(5);
    public static final Border  BORDER_DEFAULT       = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    public static final Border  BORDER_BLUE          = new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));


    public static final ConnexionMySQL CONNEXION_BD  = new ConnexionMySQL("servinfo-db","dblorne","lorne","lorne");
    public static final Requete REQUETE_BD           = new Requete(CONNEXION_BD);


}
