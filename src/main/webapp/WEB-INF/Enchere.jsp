<%@ page import="bo.Article" %>
<%@ page import="bo.Enchere" %>
<%@ page import="bo.Users" %>
<%@ page import="bo.Retrait" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: renau
  Date: 05/05/2023
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Article article = (Article) request.getAttribute("article");%>
<% String Categorie = (String) request.getAttribute("categorie");%>
<% Users vendeur = (Users) request.getAttribute("vendeur");%>
<% Retrait retrait = (Retrait) request.getAttribute("retrait");%>
<% Enchere enchere =  (Enchere) request.getAttribute("enchere");%>
<% Users encherisseur = (Users) request.getAttribute("encherisseur");%>
<% Users current = (Users) request.getSession().getAttribute("user");%>
<% String erreur = null;%>
<% if(request.getAttribute("errorMessage") != null){erreur = request.getAttribute("errorMessage").toString();}%>
<% int value_enchere;%>
<%if(enchere != null){
    value_enchere = enchere.getMontant_enchere();
}else{
    value_enchere = article.getPrixInitial();
} %>
<html>
<head>
    <title>Enchere</title>
</head>
<body>
<%@include file="Header.jsp"%>

<h1>Details vente</h1>
<%if(erreur != null){ %>
<p><%=erreur%></p>
<%}%>
<table>
    <tbody>
    <tr>
        <th><%=article.getNom()%></th>
    </tr>
    <tr>
        <th>Description :</th>
        <th><%=article.getDescription()%></th>
    </tr>
    <tr>
        <th>Catégorie : </th>
        <th><%=Categorie%></th>
    </tr>
    <% if(enchere != null){%>
    <tr>
        <th>Meilleure offre :</th>
        <th><%=enchere.getMontant_enchere()%> pts par <a href="<%=request.getContextPath() %>/ServletProfile?id=<%=encherisseur.getNo_utilisateur()%>"><%=encherisseur.getPseudo()%></a></th>
    </tr>
    <%}%>
    <tr>
        <th>Mise à prix :</th>
        <th><%=article.getPrixInitial()%> points</th>
    </tr>
    <tr>
        <th>Fin de l'enchère :</th>
        <th><%= article.getDateFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></th>
    </tr>
    <tr>
        <th>Retrait :</th>
        <th><%=retrait%></th>
    </tr>
    <tr>
        <th>Vendeur :</th>
        <th><a href="<%=request.getContextPath() %>/ServletProfile?id=<%=vendeur.getNo_utilisateur()%>"><%=vendeur.getPseudo()%></a></th>
    </tr>
    <% if(article.getEtatVente().equals("EC")){%>
    <form action="<%= request.getContextPath() %>/ServletUniqueEnchere?id=<%=article.getNo_article()%>" method="post">
        <tr>
            <th>Ma proposition :</th>
            <th><input type="number" name="offre" value="<%=value_enchere+1%>" min="<%=value_enchere+1%>" max="<%=current.getCredit()%>"></th>
            <th><button type="submit" name="encherir" value="encherir">Enchérir</button></th>
        </tr>
    </form>
    <% } %>
    </tbody>
</table>

</body>
</html>
