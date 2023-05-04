package com.encheres.encheres;

import bll.ArticleBLL;
import bll.BLLException;
import bo.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletAccueil")
public class ServletAccueil extends HttpServlet {

    private ArticleBLL bll;
    private List<Article> articles;

    @Override
    public void init() throws ServletException {
        bll = new ArticleBLL();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            articles = bll.getAllArticlesWithFilters(request);
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("articles", articles);
        request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
    }
}