package src.application;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mail {
    public static final String EMAIL_PROJET_ADMIN = "Projet1A42B@gmail.com";
    public static void Mail(String to,String sujet, String contenu) {
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//        String username  = "Projet1A42B@gmail.com";
//        String password = "motdepasseprojet1A42B";
//        String from = "Projet1A42B@gmail.com";
//        String subject = sujet+ " " + (new Date(System.currentTimeMillis())).toString();
//        String message = contenu;
//
//        Session session = Session.getDefaultInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username,password);
//            }
//        });
//
//        try{
//            Message m = new MimeMessage(session);
//            m.setFrom(new InternetAddress(from));
//            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            m.setSubject(subject);
//            m.setText(message);
//
//            Transport.send(m);
//        }catch (MessagingException e){
//            e.printStackTrace();
//        }
//
//

    }

}