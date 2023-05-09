package com.encheres.encheres;

import bll.BLLException;
import bll.UserBLL;
import bo.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ServletProfile")
public class ServletProfile extends HttpServlet {
    private UserBLL bll;
    private Users user;

    @Override
    public void init() throws ServletException {
        bll = new UserBLL();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/UpdateProfile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean connect = (Boolean) request.getSession().getAttribute("isConnected");
        Users user =  (Users) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));

        if (connect == null && user == null) {
            try {
                user = bll.SelectById(id);
            } catch (BLLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }else if(Boolean.TRUE.equals(connect) && id == user.getNo_utilisateur()) {
          request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/ServletAccueil");
        }
    }
}
