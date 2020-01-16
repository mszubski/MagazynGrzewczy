package com.vistula.magazyn.service.email;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class SendEmailService {

    public void sendEmailWithAttachments(String host, String port, String userName, String password,
                                         String toAddress, String subject, String message, String[] attachFiles)
        throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // creates a new e-mail message
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds attachments
        if (attachFiles != null && attachFiles.length > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();

                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(attachPart);
            }
        }

        // sets the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);

    }

    /**
     * Wysyłka maila z gmaila wraz z załącznikami.
     */
    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendMail() throws FileNotFoundException {
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "mszubski92@gmail.com";
        String password = "Pomidorowa1";

        //
        String mailTo = "szubskimateusz@gmail.com";
        String subject = "Nowy mail";
        String message = "Mam dla Ciebie nową wiadomość i załączniki";

        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "D:/PIMK/magazyn-grzewczy/src/main/resources/templates/mail/zalaczniki/test.txt";

        try {
            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            System.out.println("Email został wysłany.");
        } catch (Exception ex) {
            System.out.println("Nie można wysłać maila.");
            ex.printStackTrace();
        }
    }
}
