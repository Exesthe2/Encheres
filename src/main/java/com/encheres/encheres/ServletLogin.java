package com.encheres.encheres;

import bll.UserBLL;
import bo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {

    private UserBLL bll;
    private Users user;

    @Override
    public void init() throws ServletException {
        bll = new UserBLL();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailOrPseudo = request.getParameter("emailOrPseudo");
        String password = request.getParameter("password");

        if (emailOrPseudo != null && !emailOrPseudo.isBlank() && password != null && !password.isBlank()) {
            user = bll.loginBLL(emailOrPseudo, password);
            //System.out.println("bll : "+ bll);
        } else {
            //System.out.println("else : ");
            request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
            // Ajouter message d'erreur.
        }
        if (bll != null) {
            System.out.println("if bll!=null : " + bll + " / " + user);

            HttpSession session = request.getSession();
            session.setAttribute("id", user.getNo_utilisateur());
            session.setAttribute("isConnected", true);


            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    }
}