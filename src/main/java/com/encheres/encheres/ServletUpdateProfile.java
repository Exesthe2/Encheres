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
import java.sql.SQLException;

@WebServlet("/ServletUpdateProfile")
public class ServletUpdateProfile extends HttpServlet {
    private UserBLL Userbll;
    private Users user;
    @Override
public void init() throws ServletException {
    Userbll = new UserBLL();
}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Users user = (Users) req.getSession().getAttribute("user");
        int id = user.getNo_utilisateur();
        String pseudo = req.getParameter("Pseudo");
        String nom = req.getParameter("Nom");
        String prenom = req.getParameter("Prenom");
        String email = req.getParameter("Email");
        String telephone = req.getParameter("Telephone");
        String rue = req.getParameter("Rue");
        String cp = req.getParameter("CodePostal");
        String ville = req.getParameter("Ville");
        String password = req.getParameter("Password");
        String newpassword = req.getParameter("NewPassword");
        String confirmation = req.getParameter("Confirmation");
        Users modifUser = new Users(id, pseudo, nom, prenom, email, telephone, rue, cp, ville, password);

        if (action.equals("supprimer")) {
            try {
                Userbll.DeleteUser(modifUser, password);
            } catch (BLLException e) {
                req.setAttribute("errorMessage", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/UpdateProfile.jsp").forward(req, resp);
            }
            session.removeAttribute("user");
            session.removeAttribute("isConnected");
            req.getRequestDispatcher("/WEB-INF/Home.jsp").forward(req, resp);
        } else if (action.equals("enregistrer")) {
            if (newpassword.isBlank()) {  // cas si pas de nouveau mot de passe
                try {
                    Userbll.Update(modifUser, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (BLLException e) {
                    req.setAttribute("errorMessage", e.getMessage());
                    req.getRequestDispatcher("/WEB-INF/UpdateProfile.jsp").forward(req, resp);
                }
                session.setAttribute("user", modifUser);

            } else{ // Cas si nouveau mot de passe
                try {
                    Userbll.UpdateWithNewPassWord(modifUser, password, newpassword, confirmation);
                } catch (BLLException e) {
                    req.setAttribute("errorMessage", e.getMessage());
                    req.getRequestDispatcher("/WEB-INF/UpdateProfile.jsp").forward(req, resp);
                }
                session.setAttribute("user", modifUser);
            }
            req.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
