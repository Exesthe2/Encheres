package com.encheres.encheres;

import bll.*;
import bo.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@WebServlet("/ServletArticle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB)
public class ServletArticle extends HttpServlet {

    private ArticleBLL articleBLL = new ArticleBLL();
    private CategorieBLL categorieBLL = new CategorieBLL();
    private RetraitBLL retraitBLL = new RetraitBLL();
    private ImageBLL imageBLL = new ImageBLL();
    private static final String SAVE_DIRECTORY = "uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorie> categories = null;
        try {
            categories = categorieBLL.selectAll();
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/Sell.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomArticle = request.getParameter("article");
        String description = request.getParameter("description");

        // Test si les champs dates sont vides;
        String sDateDebut = request.getParameter("dateDebut");
        LocalDateTime dateDebut = null;
        String sDateFin = request.getParameter("dateFin");
        LocalDateTime dateFin = null;
        if (!"".equals(sDateDebut)) {
            dateDebut = LocalDateTime.parse(request.getParameter("dateDebut"));
        }
        if (!"".equals(sDateFin)) {
            dateFin = LocalDateTime.parse(request.getParameter("dateFin"));
        }

        int prixInitial = Integer.parseInt(request.getParameter("prixInitial"));
        Integer prixVente = null; // Parametre prix vente n'existe pas et est donc defini à null

        // Recuperation de l'id user
        HttpSession session = request.getSession();
        Users users = (Users) session.getAttribute("user");

        int no_utilisateur = users.getNo_utilisateur();
        int no_categorie = Integer.parseInt(request.getParameter("categorie"));
        String etatVente = null;

        // Information de retrait
        String rue = request.getParameter("rue");
        String codePostal = request.getParameter("codePostal");
        String ville = request.getParameter("ville");

        Article article = new Article(nomArticle, description, dateDebut, dateFin, prixInitial, prixVente, no_utilisateur, no_categorie, etatVente);
        try {
            articleBLL.insert(article);
            try {
                String appPath = request.getServletContext().getRealPath("");
                Part part = request.getPart("pictureFile");
                String fileName = saveFile(appPath, part);

                Image image = new Image(article.getNo_article(), fileName);
                imageBLL.insert(image);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }
            Retrait retrait = new Retrait(article.getNo_article(), rue, codePostal, ville);
            retraitBLL.insert(retrait);
            response.sendRedirect(request.getContextPath() + "/ServletAccueil");
        } catch (BLLException e) {
            request.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/ServletArticle");
        }
    }

    /**
     * Sauvegarder le fichier image
     * @param appPath
     * @param part
     * @return
     * @throws IOException
     */
    private String saveFile(String appPath, Part part) throws IOException {
        appPath = appPath.replace('\\', '/');

        // The directory to save uploaded file
        String fullSavePath;
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

        String filePath;
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

    private String extractFileName(Part part) {
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
