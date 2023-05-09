package com.encheres.encheres;

import bll.*;
import bo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/ServletModificationArticle")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ServletModificationArticle extends HttpServlet {

    private ArticleBLL articleBLL = new ArticleBLL();
    private CategorieBLL categorieBLL = new CategorieBLL();
    private RetraitBLL retraitBLL = new RetraitBLL();
    private ImageBLL imageBLL = new ImageBLL();
    private FunctionImage functionImage = new FunctionImage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorie> categories = null;
        Article article = null;
        Image image = null;

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            categories = categorieBLL.selectAll();
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            article = articleBLL.selectById(id);
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            imageBLL.selectById(id);
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("categories", categories);
        request.setAttribute("article", article);
        request.setAttribute("image", image);
        request.getRequestDispatcher("/WEB-INF/UpdateArticle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        int idArticle = Integer.parseInt(request.getParameter("id"));
        boolean error = false;
        try {
            articleBLL.canModify(idArticle ,user.getNo_utilisateur());
        } catch (BLLException e) {
            error = true;
            request.setAttribute("error", e.getMessage());
        }

        if (error) {
            doGet(request, response);
        }
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

        Article article = new Article(idArticle ,nomArticle, description, dateDebut, dateFin, prixInitial, prixVente, no_utilisateur, no_categorie, etatVente);
        try {
            articleBLL.update(article);
            try {
                String appPath = request.getServletContext().getRealPath("");
                Part part = request.getPart("pictureFile");
                String fileName = functionImage.saveFile(appPath, part);

                Image image = new Image(article.getNo_article(), fileName);
                imageBLL.update(image);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", e.getMessage());
            }
            Retrait retrait = new Retrait(article.getNo_article(), rue, codePostal, ville);
            retraitBLL.update(retrait);
            response.sendRedirect(request.getContextPath() + "/ServletAccueil");
        } catch (BLLException e) {
            request.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/ServletArticle");
        }
    }
}
