package com.encheres.encheres;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FunctionImage {

    private static final String SAVE_DIRECTORY = "uploads";

    public FunctionImage() {}

    /**
     * Sauvegarder le fichier image
     * @param appPath
     * @param part
     * @return
     * @throws IOException
     */
    public String saveFile(String appPath, Part part) throws IOException {
        appPath = appPath.replace('\\', '/');
        // The directory to save uploaded file
        String fullSavePath = null;
        if (appPath.endsWith("/")) {
            fullSavePath = appPath + SAVE_DIRECTORY;
        } else {
            fullSavePath = appPath + "/" + SAVE_DIRECTORY;
        }
        // Creates the save directory if it does not exists
        File fileSaveDir = new File(fullSavePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String filePath=null;
        String fileName = extractFileName(part);
        String[] fn = fileName.split("(\\.)");
        fileName = fn[0];
        String ext = fn[(fn.length-1)];
        if(!ext.isEmpty()) {
            //generate a unique file name
            UUID uuid = UUID.randomUUID();
            fileName = fileName + "_" + uuid.toString() + "." + ext ;
            if (fileName != null && fileName.length() > 0) {
                filePath = fullSavePath + File.separator + fileName;
                // Write to file
                part.write(filePath);
            }
        }
        return fileName;
    }

    /**
     * extraire le nom du fichier provenant du client
     * @param part
     * @return
     */
    public String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                return clientFileName.substring(i + 1);
            }
        }
        return null;
    }

}
