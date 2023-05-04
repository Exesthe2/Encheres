<%@ page import="bo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <a href="<%=request.getContextPath()%>/ServletAccueil">ENI-Enchères</a>
    <div>
        <c:choose>
            <c:when test="${sessionScope.isConnected == true}">
<%--                <% Users user = (Users) session.getAttribute("user"); %>--%>
                <div>
                    <a href="<%=request.getContextPath()%>/ServletArticle">Vendre un article</a>
                    <a href="<%=request.getContextPath()%>/">Enchères</a>
                    <a href="<%=request.getContextPath() %>/ServletProfile?id=${sessionScope.user.no_utilisateur}">Mon profil</a>
                    <a href="<%=request.getContextPath()%>/ServletLogout">Deconnexion</a>
                </div>
            </c:when>

            <c:when test="${sessionScope.isConnected != true}">
                <a href="<%=request.getContextPath()%>/ServletLogin">S'inscrire - Se connecter</a>
            </c:when>

            <c:otherwise/>
        </c:choose>
    </div>
</div>
