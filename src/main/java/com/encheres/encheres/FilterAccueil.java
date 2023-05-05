package com.encheres.encheres;

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
        urlPatterns = { "/", "/ServletArticle", "/ServletProfile" ,"/ServletUpdateProfile" })
public class FilterAccueil implements Filter {
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
        boolean viewOtherProfile = false;

        if (request.getParameter("id") != null) {
            viewOtherProfile = true;
        }

        if (connect || viewOtherProfile) {
            ((HttpServletResponse) response).setHeader("Cache-Control","no-cache");
            ((HttpServletResponse) response).setHeader("Cache-Control","no-store");
            ((HttpServletResponse) response).setHeader("Pragma","no-cache");
            ((HttpServletResponse) response).setDateHeader ("Expires", 0);
            chain.doFilter(request, response);
//            httpServletResponse.sendRedirect(((HttpServletRequest) request).getRequestURI());
        } else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/ServletAccueil");
    }

    @Override
    public void destroy() {
    }
}
