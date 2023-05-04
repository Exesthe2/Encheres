package com.encheres.encheres;

import bll.ArticleBLL;
import bll.CategorieBLL;
import bll.RetraitBLL;
import bo.Article;
import bo.Categorie;
import bo.Retrait;
import bo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/ServletArticle")
public class ServletArticle extends HttpServlet {

    private ArticleBLL articleBLL = new ArticleBLL();
    private CategorieBLL categorieBLL = new CategorieBLL();
    private RetraitBLL retraitBLL = new RetraitBLL();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorie> categories = categorieBLL.selectAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/Sell.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("Enregistrer".equals(action)) {
            String nomArticle = request.getParameter("article");
            String description = request.getParameter("description");
            LocalDateTime dateDepart = LocalDateTime.parse(request.getParameter("dateDepart"));
            LocalDateTime dateFin = LocalDateTime.parse(request.getParameter("dateFin"));
            int prixInitial = Integer.parseInt(request.getParameter("prixInitial"));
            int prixVente = 0; // Parametre prix vente n'existe pas et est donc defini à null
            // Recuperation de l'id user
            HttpSession session = request.getSession();
            Users users = (Users) session.getAttribute("user");
            int no_utilisateur = users.getNo_utilisateur();
            int no_categorie = Integer.parseInt(request.getParameter("categorie"));
            String etatVente = "CR";
            String image = request.getParameter("image");

            // Information de retrait
            String rue = request.getParameter("rue");
            String codePostal = request.getParameter("codePostal");
            String ville = request.getParameter("ville");

            Article article = new Article(nomArticle, description, dateDepart, dateFin, prixInitial, prixVente, no_utilisateur, no_categorie, etatVente, image);
            articleBLL.insert(article);
            Retrait retrait = new Retrait(article.getNo_article(), rue, codePostal, ville);
            retraitBLL.insert(retrait);
        } else {
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        }
    }
}
