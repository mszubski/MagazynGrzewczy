package com.vistula.magazyn.service.email;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class SendEmailService {

    public void sendEmailWithAttachments(String host, String port, String userName, String password,
                                         String toAddress, String subject, String message, String[] attachFiles)
        throws AddressException, MessagingException {
        // ustaw wlaściwości dla obiektu Properties - SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);

        // stwórz nową sesję z autentykacją
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);

        // utwórz wiadomosc email
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());

        // stwórz wiadomosc
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html; charset=UTF-8");

        // stwórz multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // dodaj zalaczniki
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

        // stwórz the multi-part as e-mail's content
        msg.setContent(multipart);

        // sends the e-mail
        Transport.send(msg);

    }

    /**
     * Wysyłka maila z gmaila wraz z załącznikami.
     */
    //@Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendMail() throws IOException {
        // SMTP properties
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "mszubski92@gmail.com";
        String password = "Pomidorowa1";
        String mailTo = "szubskimateusz@gmail.com";
        String subject = "Nowy mail";

        String message = getStringContent("src/main/resources/templates/mail/mailTemplate.html");

        // zalaczniki
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

    private String getStringContent(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        FileReader fileReader = new FileReader(filePath);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            contentBuilder.append(str);
        }
        bufferedReader.close();

        String message = contentBuilder.toString();
        System.out.println(message);
        return message;
    }
}
