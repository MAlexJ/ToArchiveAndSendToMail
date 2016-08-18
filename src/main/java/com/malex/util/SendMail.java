package com.malex.util;

import com.malex.model.EmailProperties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;

public class SendMail {

    public static void send(String subjectMessage, String pathFile, byte[] zip) {

        String sender = EmailProperties.getProperty("mail.email");
        String user = EmailProperties.getProperty("mail.user");
        String password = EmailProperties.getProperty("mail.password");
        String recipient = EmailProperties.getProperty("mail.recipient");

        // #########  Step #1: Get the Session object. ##########
        Session session = Session.getInstance(EmailProperties.getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        // #########  Step #2:  Create a default MimeMessage object. #########
        try {
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));

            // Set Subject: header field
            message.setSubject(subjectMessage);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("The email contains the following files: \n "
                    + pathFile + "\n "
                    + "Date: "
                    + new Date() + "\n \n"
                    + "Author: Malex");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            // DataSource source = new FileDataSource(pathFile);
            DataSource source = new ByteArrayDataSource(zip, "application/zip");
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("log.zip");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            //  #########  Step #3: Send message #########
            Transport.send(message);

        } catch (MessagingException e) {
            System.err.println(" >>> The message is not sent !!! <<< " + e.getMessage());
        }
    }
}
