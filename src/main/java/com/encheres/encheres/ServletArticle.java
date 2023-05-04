package com.encheres.encheres;

import bll.BLLException;
import bll.CategorieBLL;
import bo.Categorie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ServletArticle")
public class ServletArticle extends HttpServlet {

    private CategorieBLL categorieBLL = new CategorieBLL();

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
        String action = request.getParameter("action");
        System.out.println(action);
    }
}
