package com.malex.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipLogFile {

    private static final String LOG_PATH = "log/";

    public static byte[] getLogs() throws IOException {
        File file = new File(LOG_PATH);

        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (containsLogFiles(files)) {
                return createZip(LOG_PATH);
            }
        }

        throw new FileNotFoundException("Log file not found!");
    }

    /**
     * Extension '.log' the file.
     */
    private static final String EXTENSION_LOG_FILE = ".log";

    /**
     * Check the directory for existence of files with the extension '.log'.
     *
     * @param files list files.
     * @return true if files contains log-file.
     */
    private static boolean containsLogFiles(File[] files) {
        for (File file : files)
            if (file.getName().endsWith(EXTENSION_LOG_FILE)) {
                return true;
            }
        return false;
    }


    /**
     * 1. how to zip a folder itself using java 8
     * http://stackoverflow.com/questions/15968883/how-to-zip-a-folder-itself-using-java
     * <p>
     * 2. How to send zip file without creating it on physical location.
     * http://stackoverflow.com/questions/9766420/how-to-send-zip-file-without-creating-it-on-physical-location
     * <p>
     * 3. In Java: How to zip file from byte[] array?
     * http://stackoverflow.com/questions/357851/in-java-how-to-zip-file-from-byte-array
     *
     * @param sourceDirPath log directory
     * @return byte array
     * @throws IOException
     */
    private static byte[] createZip(String sourceDirPath) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zs = new ZipOutputStream(byteArrayOutputStream)) {
            Path pathFile = Paths.get(sourceDirPath);
            Files.walk(pathFile)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        String sp = path.toAbsolutePath().toString().replace(pathFile.toAbsolutePath().toString(), "").replace(path.getFileName().toString(), "");
                        ZipEntry zipEntry = new ZipEntry(sp + "/" + path.getFileName().toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            zs.write(Files.readAllBytes(path));
                            zs.closeEntry();
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    });
        }
        return byteArrayOutputStream.toByteArray();
    }
}
