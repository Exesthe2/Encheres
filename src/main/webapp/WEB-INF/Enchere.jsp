<%@ page import="bo.Article" %>
<%@ page import="bo.Enchere" %>
<%@ page import="bo.Users" %>
<%@ page import="bo.Retrait" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Article article = (Article) request.getAttribute("article");%>
<% String Categorie = (String) request.getAttribute("categorie");%>
<% Users vendeur = (Users) request.getAttribute("vendeur");%>
<% Retrait retrait = (Retrait) request.getAttribute("retrait");%>
<% Enchere enchere = (Enchere) request.getAttribute("enchere");%>
<% Users encherisseur = (Users) request.getAttribute("encherisseur");%>
<% Users current = (Users) request.getSession().getAttribute("user");%>
<% String erreur = null;%>
<% if (request.getAttribute("errorMessage") != null) {
    erreur = request.getAttribute("errorMessage").toString();
}%>
<% int value_enchere;%>
<% Boolean connected = (Boolean) request.getSession().getAttribute("isConnected"); %>
<% Integer montant = (Integer) session.getAttribute("montant"); %>
<%
    if (enchere != null) {
        value_enchere = enchere.getMontant_enchere();
    } else {
        value_enchere = article.getPrixInitial();
    }
%>
<%String messageEnchere = ""; %>
<% if (article.getEtatVente().equals("VD")) {
    if (current.getNo_utilisateur() == encherisseur.getNo_utilisateur()) {
        messageEnchere = "Vous avez remporté la vente !";
    } else {
        messageEnchere = encherisseur.getNom() + " a remporté l'enchere.";
    }
} %>
<html>
<head>
    <title>Enchere</title>
</head>
<body>
<%@include file="Header.jsp" %>

<%if (messageEnchere.equals("")) {%>
<h1 class="title">Details vente</h1>
<%} else {%>
<h1 class="title">
    <%=messageEnchere%>
</h1><%}%>
<%if (erreur != null) { %>
<p><%=erreur%>
</p>
<%}%>
<c:if test="${'' != image.getImage()}">
    <img src="${pageContext.request.contextPath}/uploads/${image.getImage()}" alt="${article.getNom()}"
         class="image imageEnchere"/>
</c:if>
<table class="enchereTable">
    <tbody>
    <tr>
        <th>Nom :</th>
        <th><%=article.getNom()%>
        </th>
    </tr>
    <tr>
        <th>Description :</th>
        <th><%=article.getDescription()%>
        </th>
    </tr>
    <tr>
        <th>Catégorie :</th>
        <th><%=Categorie%>
        </th>
    </tr>
    <% if (enchere != null) {%>
    <tr>
        <th>Meilleure offre :</th>
        <th><%=enchere.getMontant_enchere()%> pts par <a
                href="<%=request.getContextPath() %>/ServletProfile?id=<%=encherisseur.getNo_utilisateur()%>"><%=encherisseur.getPseudo()%>
        </a></th>
    </tr>
    <%}%>
    <tr>
        <th>Mise à prix :</th>
        <th><%=article.getPrixInitial()%> points</th>
    </tr>
    <tr>
        <th>Fin de l'enchère :</th>
        <th><%= article.getDateFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%>
        </th>
    </tr>
    <tr>
        <th>Retrait :</th>
        <th><%=retrait%>
        </th>
    </tr>
    <tr>
        <th>Vendeur :</th>
        <th>
            <a href="<%=request.getContextPath() %>/ServletProfile?id=<%=vendeur.getNo_utilisateur()%>"><%=vendeur.getPseudo()%>
            </a></th>
    </tr>
    <% if (article.getEtatVente().equals("EC")) {%>
    <form action="<%= request.getContextPath() %>/ServletUniqueEnchere?id=<%=article.getNo_article()%>" method="post">
        <tr>
            <th>Ma proposition :</th>
            <% if (montant != null && connected != null) { %>
            <th><input type="number" name="offre" value="<%=montant%>" min="<%=value_enchere+1%>"
                       max="<%=current.getCredit()%>"></th>
            <% } else { %>
            <th><input type="number" name="offre" value="<%=value_enchere+1%>" min="<%=value_enchere+1%>"></th>
            <% } %>
            <th>
                <button type="submit" name="encherir" value="encherir">Enchérir</button>
            </th>
        </tr>
    </form>
    <% } %>
    </tbody>
</table>

<% if (connected != null && current.equals(vendeur) && "CR".equals(article.getEtatVente())) { %>
<a href="<%=request.getContextPath()%>/ServletModificationArticle?id=<%=article.getNo_article()%>" id="modification">Modifier
    mon article</a>
<% } %>

</body>
</html>
