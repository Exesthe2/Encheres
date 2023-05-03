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

@WebServlet("/ServletUpdateProfile")
public class ServletUpdateProfile extends HttpServlet {
    private UserBLL bll;
    private Users user;
    @Override
public void init() throws ServletException {
    bll = new UserBLL();
}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Users user = (Users) req.getSession().getAttribute("user");
        int id = user.getNo_utilisateur();
        if(action.equals("supprimer")){
            bll.DeleteUser(id);
            session.removeAttribute("user");
            session.removeAttribute("isConnected");
            req.getRequestDispatcher("/WEB-INF/Home.jsp").forward(req, resp);
        }else if (action.equals("enregistrer")){
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
