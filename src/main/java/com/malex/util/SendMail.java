package com.malex.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Created by malex on 17.08.16.
 */
public class SendMail {

    // Assuming you are sending email
    private static final String HOST_EMAIL = "smtp.gmail.com";
    private static final String SMTP_AUTH_EMAIL = "true";
    private static final String SMTP_PORT_EMAIL = "587";
    private static final String START_TLS_EMAIL = "true";

    // Properties setting email
    private static final String HOST = "mail.smtp.host";
    private static final String SMTP_AUTH = "mail.smtp.auth";
    private static final String SMTP_PORT = "mail.smtp.port";
    private static final String START_TLS = "mail.smtp.starttls.enable";

    // Properties email
    private static final Properties properties;

    // Initialization Properties email
    static {
        properties = new Properties();
        properties.put(SMTP_AUTH, SMTP_AUTH_EMAIL);
        properties.put(START_TLS, START_TLS_EMAIL);
        properties.put(HOST, HOST_EMAIL);
        properties.put(SMTP_PORT, SMTP_PORT_EMAIL);
    }

    public static void send(String sender, final String userEmail, final String passwordEmail, String recipient, String subjectMessage, String pathFile) {

        // #########  Step #1: Get the Session object. ##########
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userEmail, passwordEmail);
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
            DataSource source = new FileDataSource(pathFile);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(pathFile);
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
