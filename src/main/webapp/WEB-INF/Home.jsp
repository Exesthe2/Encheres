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

<div class="filter">
    <p>Filtres :</p>
    <form action="<%= request.getContextPath() %>/ServletAccueil" method="get">
        <label>Nom de l'article :</label>
        <input type="text" name="articleName">

        <label>Catégories :</label>
        <select name="categorie">
            <option value="" selected>Toutes categories</option>
            <c:forEach var="categorie" items="${categories}">
                <option value="${categorie.no_categorie}">${categorie.libelle}</option>
            </c:forEach>
        </select>

        <button type="submit">Rechercher</button>
    </form>
</div>
<div class="cards">
    <c:forEach var="article" items="${articles}">
        <div class="card">
            <div class="productInfos">
                <a href="<%=request.getContextPath() %>/ServletUniqueEnchere?id=${article.no_article}">
                <h3 class="productTitle">${article.nom}</h3>
                </a>
                <c:if test="${article.enchere != null}">
                    <p class="productPrice">Prix : ${article.enchere.montant_enchere} points</p>
                </c:if>
                <c:if test="${empty(article.enchere)}">
                    <p class="productPrice">Prix : ${article.prixInitial} points</p>
                </c:if>
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