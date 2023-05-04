<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Accueil</title>
</head>
<body>
<h1>Liste des enchères</h1>
<%@include file="Header.jsp" %>
<a href="<%=request.getContextPath()%>/ServletArticle">Creation article</a>
<a href="<%=request.getContextPath()%>/TestConnexionBDDServlet">Test connexion</a>

<div class="cards">
    <c:forEach var="article" items="${articles}">
        <div class="card">
            <img class="productImage" src="${article.image}">
            <div class="productInfos">
                <h3 class="productTitle">${article.nom}</h3>
                <p class="productPrice">prix : ${article.prixInitial} crédits</p>
                <fmt:parseDate value="${article.dateFin}" pattern="yyyy-MM-dd'T'HH:mm" var="date_fin_enchere"/>
                <fmt:formatDate value="${date_fin_enchere}" pattern="dd-MM-yyyy HH:mm" var="dateFin"/>
                <p class="productEndDate">Fin de l'enchère : ${dateFin}</p>
                <p class="productSeller">Vendeur : <a>${article.no_utilisateur}</a></p>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
