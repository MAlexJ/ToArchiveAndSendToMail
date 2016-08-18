package com.malex;

import com.malex.util.CreateZipLogFile;
import com.malex.util.SendMail;

import java.io.IOException;
import java.util.Date;

/**
 * Start application.
 */
public class Main {

    public static void main(String[] args) {
        try {
            // STEP #1 ->
            String sender = "iposcashregister@gmail.com";
            String userEmail = "iposcashregister";
            String passwordEmail = "0672687484a";

            String recipient = "iposcashregister@gmail.com";
            String subjectMessage = new Date() + " Log files of the application IPOS.";

            // STEP #2 ->  create zip archive
            byte[] archiveArrayByte = CreateZipLogFile.getLogs();
            String nameAttachedFile = "report" + "_cashReg_number_1" + ".zip";

            // STEP #3 ->  send email
            SendMail.send(sender, userEmail, passwordEmail, recipient, subjectMessage, nameAttachedFile, archiveArrayByte);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
