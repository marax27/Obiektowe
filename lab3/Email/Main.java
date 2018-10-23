package Email;

/* Aplikacja pozwalajaca wyslac e-mail z domeny gmail.com.
 * Wymagane uzupelnienie 'username' i 'password' (poczatek
 * funkcji main).
 * Testowano na: Java wersja 9, javax.mail wersja 1.6.2.
 */

import javax.mail.MessagingException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class Main {

    public static void main(String[] args) {
        // gmail.com username & password.
        final String username = "example@gmail.com";
        final String password = "example_password";

        // Build an e-mail message.
        EmailMessage em = EmailMessage.builder()
                .addFrom("example@gmail.com")
                .addTo("recipient1@student.agh.edu.pl")
                .addTo("recipient2@gmail.com")
                .addSubject("Hello there!")
                .addContent("Sample text")
                .addBCC("secret@student.agh.edu.pl")
                .build();

        // gmail.com configuration.
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Send a message, handle potential errors.
        try {
            em.send(session);
            System.out.println("Message sent succesfully.");
        }catch(EmailMessage.EmailException exc){
            System.out.printf("Failed to send a message. Error: %s\n", exc);
        }

        System.out.println("Exiting...");
    }
}
