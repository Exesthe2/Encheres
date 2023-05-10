package com.encheres.encheres;

import bll.*;
import bo.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ServletUniqueEnchere")
public class ServletUniqueEnchere extends HttpServlet {

    private ArticleBLL Articlebll;
    private Article article;
    private EnchereBLL Encherebll;
    private Enchere enchere;
    private CategorieBLL Categoriebll;
    private String categorie;
    private UserBLL Userbll;
    private Users vendeur;
    private Users encherisseur = null;
    private RetraitBLL Retraitbll;
    private Retrait retrait;
    private ImageBLL imageBLL;
    private Image image;
    @Override
    public void init() throws ServletException {

        Articlebll = new ArticleBLL();
        Encherebll = new EnchereBLL();
        Userbll = new UserBLL();
        Categoriebll = new CategorieBLL();
        Retraitbll = new RetraitBLL();
        imageBLL = new ImageBLL();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idArticle = Integer.parseInt(req.getParameter("id"));
        try {
            article = Articlebll.selectById(idArticle);
        } catch (BLLException e) {
            req.getRequestDispatcher("/WEB-INF/Home.jsp").forward(req, resp);
        }
        try {
            enchere = Encherebll.selectByArticleId(article.getNo_article());
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            categorie = Categoriebll.SelectCategorieByID(article.getNo_categorie());
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            vendeur = Userbll.SelectById(article.getNo_utilisateur());
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            retrait = Retraitbll.selectById(article.getNo_article());
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }

        if(enchere!=null){
            try {
                encherisseur = Userbll.SelectById(enchere.getNo_utilisateur());
            } catch (BLLException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            image = imageBLL.selectById(article.getNo_article());
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("article", article);
        req.setAttribute("enchere", enchere);
        req.setAttribute("categorie", categorie);
        req.setAttribute("vendeur", vendeur);
        req.setAttribute("retrait", retrait);
        req.setAttribute("encherisseur", encherisseur);
        req.setAttribute("image", image);
        req.getRequestDispatcher("/WEB-INF/Enchere.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
