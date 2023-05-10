package com.encheres.encheres;

import bll.ArticleBLL;
import bll.BLLException;
import bo.Article;
import bo.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE,
        DispatcherType.ERROR
        },
        urlPatterns = { "/", "/ServletArticle", "/ServletProfile" ,"/ServletUpdateProfile", "/ServletModificationArticle", "/ServletRegister" })
public class FilterAccueil implements Filter {

    private ArticleBLL articleBLL = new ArticleBLL();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Boolean connect = (Boolean) ((HttpServletRequest) request).getSession().getAttribute("isConnected");
        if (connect == null) {
            connect = false;
        }

        if (((HttpServletRequest) request).getRequestURI().contains("/ServletProfile")) {
            if (request.getParameter("id") != null) {
                cacheClearBeforeRedirect(request, response, chain);
            } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
        }

        if (((HttpServletRequest) request).getRequestURI().contains("/ServletModificationArticle")) {
            if (connect && request.getParameter("id") != null) {
                Article article = null;
                Users current = (Users) ((HttpServletRequest) request).getSession().getAttribute("user");
                try {
                    article = articleBLL.selectById(Integer.parseInt(request.getParameter("id")));
                } catch (BLLException e) {
                    throw new RuntimeException(e);
                }
                if (article.getNo_utilisateur() == current.getNo_utilisateur()) {
                    cacheClearBeforeRedirect(request, response, chain);
                } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
            } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
        }

        if (((HttpServletRequest) request).getRequestURI().contains("/ServletArticle")) {
            if (connect) {
                cacheClearBeforeRedirect(request, response, chain);
            } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
        }

        if (((HttpServletRequest) request).getRequestURI().contains("/ServletRegister")) {
            if (!connect) {
                cacheClearBeforeRedirect(request, response, chain);
            } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
        }

        if (((HttpServletRequest) request).getRequestURI().contains("/ServletUpdateProfile")) {
            if (connect) {
                cacheClearBeforeRedirect(request, response, chain);
            } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
        }

    }

    public void cacheClearBeforeRedirect(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        ((HttpServletResponse) response).setHeader("Cache-Control","no-cache");
        ((HttpServletResponse) response).setHeader("Cache-Control","no-store");
        ((HttpServletResponse) response).setHeader("Pragma","no-cache");
        ((HttpServletResponse) response).setDateHeader ("Expires", 0);
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }
}
