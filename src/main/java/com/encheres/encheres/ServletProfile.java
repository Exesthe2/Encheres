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

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServlet session = (HttpServlet) request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        Boolean connect = Boolean.valueOf(request.getParameter("isConnected"));
        if(connect) {

            request.getRequestDispatcher("/WEB-INF/ViewProfile.jsp").forward(request, response);
        }
    }
    }
