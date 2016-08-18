package com.malex;

import com.malex.util.CreateZipLogFile;
import com.malex.util.SendMail;

import java.io.IOException;
import java.util.Date;

import static com.malex.util.CreateZipLogFile.OUTPUT_ZIP;

/**
 * Start application.
 */
public class Main {

    public static void main(String[] args) {

        // STEP #1 ->
        String sender = "iposcashregister@gmail.com";
        String userEmail = "iposcashregister";
        String passwordEmail = "0672687484a";

        String recipient = "iposcashregister@gmail.com";
        String subjectMessage = new Date() + " Log files of the application IPOS.";
        String fileName = "log/iPOS_0_0.log";

        // STEP #2 ->  create zip archive
        CreateZipLogFile.getLogs();
        try {

            CreateZipLogFile.createZip(OUTPUT_ZIP, "zip/" + "log.zip");

        } catch (IOException e) {
            e.printStackTrace();
        }

        // STEP #3 ->  send email
//        SendMail.send(sender, userEmail, passwordEmail, recipient, subjectMessage, fileName);
    }

}
