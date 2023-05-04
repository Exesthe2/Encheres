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
import java.util.List;

@WebServlet("/ServletRegister")
public class ServletRegister extends HttpServlet {

    private UserBLL bll;
    private Users user;
    private List<String> pseudosAndEmails;

    @Override
    public void init() throws ServletException {
        bll = new UserBLL();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String redirection = "Register.jsp";

            String pseudo = request.getParameter("pseudo");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String phoneNumber = request.getParameter("phoneNumber");
            String postalCode = request.getParameter("postalCode");
            String street = request.getParameter("street");
            String town = request.getParameter("town");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            // Check pseudo and email
            try {
                pseudosAndEmails = bll.pseudosAndEmailsBLL();
            } catch (BLLException e) {
                throw new RuntimeException(e);
            }
            for (String item : pseudosAndEmails) {
                if (item.equals(pseudo)) {
                    request.setAttribute("error", "Ce pseudo est déjà utilisé.");
                    request.getRequestDispatcher("/WEB-INF/" + redirection).forward(request, response);
                }
                if (item.equals(email)) {
                    request.setAttribute("error", "Cet adresse mail est déjà utilisé.");
                    request.getRequestDispatcher("/WEB-INF/" + redirection).forward(request, response);
                }
            }

            // Check password
            if (!password.equals(confirmPassword)) {
                request.setAttribute("error", "Votre mot de passe n'est pas correct.");
                request.getRequestDispatcher("/WEB-INF/" + redirection).forward(request, response);
            }

            // Register user
            try {
                user = bll.newUserBLL(pseudo, firstname, lastname, phoneNumber, postalCode, street, town, email, password, confirmPassword);
            } catch (BLLException e) {
                throw new RuntimeException(e);
            }

            // Redirect
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("isConnected", true);
                redirection = "Home.jsp";
            } else if (user == null) {
                request.setAttribute("error", "Une erreur c'est produite. Veuillez re-essayer.");
            }
            request.getRequestDispatcher("/WEB-INF/" + redirection).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
    }
}