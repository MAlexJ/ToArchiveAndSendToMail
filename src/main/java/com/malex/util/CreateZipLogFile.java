package com.malex.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipLogFile {

    private static String logPath = "log/";

    public static final String OUTPUT_ZIP = logPath;

    public static void getLogs() {
        File file = new File(logPath);

        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            System.err.println(Arrays.toString(files));
            //TODO create gzip

        }
    }

    public static void createZip(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));

        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
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
    }
}
