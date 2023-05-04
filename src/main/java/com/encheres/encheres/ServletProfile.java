package com.encheres.encheres;

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
        System.out.println("redirection");
        request.getRequestDispatcher("/WEB-INF/UpdateProfile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       Boolean connect = (Boolean) request.getSession().getAttribute("isConnected");
        Users user =  (Users) request.getSession().getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(connect);
        if(connect && id == user.getNo_utilisateur()) {
            System.out.println("connecter");
            request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }else if(connect && id != user.getNo_utilisateur()) {
          user = bll.SelectById(id);
          request.setAttribute("user", user);
          request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }else{
            System.out.println("non connecter");
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        }
    }
    }
