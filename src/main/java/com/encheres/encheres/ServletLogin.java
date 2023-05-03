package com.encheres.encheres;

import bll.BLLException;
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
        String redirection = "Login.jsp";

        // Keep email/pseudo and password in form.
        String emailOrPseudo = request.getParameter("emailOrPseudo");
        String password = request.getParameter("password");

        try {
            user = bll.loginBLL(emailOrPseudo, password);
        } catch (BLLException e) {
            request.setAttribute("BLLException", e.getErreurs());
        }
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("isConnected", true);
            redirection = "Home.jsp";
        } else if (user == null) {
            request.setAttribute("error", "Email/Pseudo ou mot de passe incorect.");

        }
        request.getRequestDispatcher("/WEB-INF/" + redirection).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
    }
}