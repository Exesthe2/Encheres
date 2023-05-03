package com.encheres.encheres;

import bll.UserBLL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ServletProfile")
public class ServletProfile extends HttpServlet {

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       Boolean connect = (Boolean) request.getSession().getAttribute("isConnected");
        System.out.println(connect);
        if(connect) {
            System.out.println("connecter");
            request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }else{
            System.out.println("non connecter");
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);
        }
    }
    }
