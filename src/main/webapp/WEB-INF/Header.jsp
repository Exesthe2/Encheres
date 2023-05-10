<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<style>
    <%@include file="style/style.css" %>
</style>
<div class="header">
    <div class="home">
        <a href="<%=request.getContextPath()%>/ServletAccueil">ENI-Enchères</a>
    </div>
    <div class="menu">
        <c:choose>
            <c:when test="${sessionScope.isConnected == true}">
                <a href="<%=request.getContextPath()%>/ServletArticle">Vendre un article</a>
                <a href="<%=request.getContextPath() %>/ServletProfile?id=${sessionScope.user.no_utilisateur}">Mon
                    profil</a>
                <a href="<%=request.getContextPath()%>/ServletLogout">Deconnexion</a>
            </c:when>

            <c:when test="${sessionScope.isConnected != true}">
                <a href="<%=request.getContextPath()%>/ServletLogin">S'inscrire - Se connecter</a>
            </c:when>

            <c:otherwise/>
        </c:choose>
    </div>
</div>
