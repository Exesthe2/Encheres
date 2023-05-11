package com.encheres.encheres;

import bll.ArticleBLL;
import bll.BLLException;
import bll.CategorieBLL;
import bll.ImageBLL;
import bo.Article;
import bo.Categorie;
import bo.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletAccueil")
public class ServletAccueil extends HttpServlet {

    private ArticleBLL articleBLL;
    private CategorieBLL categorieBLL;
    private ImageBLL imageBLL;
    private List<Article> articles;
    private List<Categorie> categories;
    private List<Image> images;

    @Override
    public void init() throws ServletException {
        articleBLL = new ArticleBLL();
        categorieBLL = new CategorieBLL();
        imageBLL = new ImageBLL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            articles = articleBLL.getAllArticlesWithFilters(request);
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            images = imageBLL.selectAll();
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        try {
            categories = categorieBLL.selectAll();
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("articles", articles);
        request.setAttribute("images", images);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
    }
}