package Email;

import java.util.*;
import java.lang.*;
import javax.mail.*;
import javax.mail.internet.*;

//************************************************************

public class EmailMessage {
    // Required fields.
    private InternetAddress from;
    private LinkedList<InternetAddress> to;  //at least one e-mail

    // Optional fields.
    private String subject;
    private String content;
    private String mimeType;
    private LinkedList<InternetAddress> cc;
    private LinkedList<InternetAddress> bcc;

    // Constructor.
    public EmailMessage(){
        to = new LinkedList<InternetAddress>();
        cc = new LinkedList<InternetAddress>();
        bcc = new LinkedList<InternetAddress>();
    }

    // Return true if EmailMessage has its required fields filled.
    public boolean verify(){
        return from != null && to != null && !to.isEmpty();
    }

    // Send a message.
    public boolean send(Session session){
        if(!verify())
            throw new EmailException("Cannot send an unitialized e-mail");

        try{
            // Prepare arrays of recipients.
            InternetAddress[] to_arr = to.toArray(new InternetAddress[to.size()]);
            InternetAddress[] cc_arr = cc.toArray(new InternetAddress[cc.size()]);
            InternetAddress[] bcc_arr = bcc.toArray(new InternetAddress[bcc.size()]);

            Message message = new MimeMessage(session);
            message.setFrom(from);
            message.setRecipients(Message.RecipientType.TO, to_arr);
            message.setRecipients(Message.RecipientType.CC, cc_arr);
            message.setRecipients(Message.RecipientType.BCC, bcc_arr);
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

        }catch(MessagingException exc){
            throw new EmailException(exc.toString());
        }

        return true;
    }

    //************************************************************

    public static class EmailException extends RuntimeException{
        public EmailException(String errorMessage){
            super(errorMessage);
        }
    }

    //************************************************************

    public static Builder builder(){
        return new EmailMessage.Builder();
    }

    public static class Builder{
        Builder(){
            email = new EmailMessage();
        }

        Builder addFrom(String from){
            try {
                InternetAddress ia = new InternetAddress(from);
                ia.validate();
                email.from = ia;
            }catch(AddressException aex){
                throw new EmailException("Invalid e-mail address 'from'.");
            }
            return this;
        }
        Builder addTo(String to){
            try {
                InternetAddress ia = new InternetAddress(to);
                ia.validate();
                email.to.add(ia);
            }catch(AddressException aex){
                throw new EmailException("Invalid e-mail address 'to'.");
            }
            return this;
        }
        Builder addSubject(String subject){
            email.subject = subject;
            return this;
        }
        Builder addContent(String content){
            email.content = content;
            return this;
        }
        Builder addMimeType(String mimeType){
            email.mimeType = mimeType;
            return this;
        }
        Builder addCC(String cc){
            try {
                InternetAddress ia = new InternetAddress(cc);
                ia.validate();
                email.cc.add(ia);
            }catch(AddressException aex){
                throw new EmailException("Invalid e-mail address 'cc'.");
            }
            return this;
        }
        Builder addBCC(String bcc){
            try {
                InternetAddress ia = new InternetAddress(bcc);
                ia.validate();
                email.bcc.add(ia);
            }catch(AddressException aex){
                throw new EmailException("Invalid e-mail address 'bcc'.");
            }
            return this;
        }

        EmailMessage build() {
            if(email.verify())
                return email;
            throw new EmailException("Failed to build an e-mail. Perhaps a required field is empty.");
        }

        //************************************************************

        private EmailMessage email;
    }
}

//************************************************************