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
            // STEP #1 -> Create title the message.
            String subjectMessage = new Date() + " Log files of the application IPOS.";

            // STEP #2 ->  create zip archive log files
            byte[] archiveArrayByte = CreateZipLogFile.getLogs();
            String nameAttachedFile = "report" + "_cashReg_number_1" + ".zip";

            // STEP #3 ->  send email
            SendMail.send(subjectMessage, nameAttachedFile, archiveArrayByte);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
