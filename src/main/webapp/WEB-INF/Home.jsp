<%@ page import="java.util.List" %>
<%@ page import="bo.Image" %>
<%@ page import="bo.Article" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    List<Image> images = (List<Image>) request.getAttribute("images");
    List<Article> articles = (List<Article>) request.getAttribute("articles");
%>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<%@include file="Header.jsp" %>

<div class="filter">
    <form action="<%= request.getContextPath() %>/ServletAccueil" method="get">
        <div class="noConnected">
            <label>Nom de l'article : </label>
            <input type="text" name="articleName">
            <label>Catégories : </label>
            <select name="categorie">
                <option value="" selected>Toutes categories</option>
                <c:forEach var="categorie" items="${categories}">
                    <option value="${categorie.no_categorie}">${categorie.libelle}</option>
                </c:forEach>
            </select>
        </div>

        <c:if test="${isConnected == true}">
            <div>
                <label>Achats :</label>
                <input type="radio" name="buyOrSell" value="buy">
                <div>
                    <input type="checkbox" name="openAuctions" id="openAuctions" value="EC">
                    <label for="openAuctions">Enchères ouvertes</label>

                    <input type="checkbox" name="myAuctions" id="myAuctions" value="1">
                    <label for="myAuctions">Mes enchères</label>

                    <input type="checkbox" name="myAuctionsWin" id="myAuctionsWin" value="1">
                    <label for="myAuctionsWin">Mes enchères remportées</label>
                </div>
                <label>Ventes :</label>
                <input type="radio" name="buyOrSell" value="sell">
                <div>
                    <input type="checkbox" name="myCurrentSales" id="myCurrentSales" value="EC">
                    <label for="myCurrentSales">Mes ventes en cours</label>

                    <input type="checkbox" name="mySalesNotStart" id="mySalesNotStart" value="CR">
                    <label for="mySalesNotStart">Mes ventes non débutées</label>

                    <input type="checkbox" name="mySalesEnd" id="mySalesEnd" value="VD">
                    <label for="mySalesEnd">Mes ventes terminées</label>
                </div>
            </div>
        </c:if>

        <button type="submit">Rechercher</button>
    </form>
</div>
<div class="cards">
    <c:forEach var="article" items="${articles}">
        <div class="card">
            <div class="productInfos">
                <c:if test="${'' != images.get(article.no_article - 1).getImage() }">
                    <img src="${pageContext.request.contextPath}/uploads/${images.get(article.no_article - 1).getImage()}" alt="${article.nom}" />
                </c:if>
                <a href="<%=request.getContextPath() %>/ServletUniqueEnchere?id=${article.no_article}">
                    <h3 class="productTitle">${article.nom}</h3>
                </a>
                <c:if test="${article.enchere != null}">
                    <p class="productPrice">Prix : </p>
                    <p>${article.enchere.montant_enchere} points</p>
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