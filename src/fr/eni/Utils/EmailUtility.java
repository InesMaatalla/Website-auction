package fr.eni.Utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Class utilitaire pour l'envoie de mail
 *
 */
public class EmailUtility {

    public static void sendEmail(String host, String port,final String senderEmail, String senderName, final String password,
                                 String recipientEmail, String subject, String message) throws AddressException,MessagingException, UnsupportedEncodingException {

        // recupération de la config SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creation d'une nouvelle session avec l'authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // creation d'un nouveau message mail
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(senderEmail, senderName));
        InternetAddress[] toAddresses = { new InternetAddress(recipientEmail) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        // envoie de l'email
        Transport.send(msg);

    }
}
