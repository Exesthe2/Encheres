<%@ page import="bo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Users user = (Users) session.getAttribute("user"); %>
<html>
<head>
    <title>Modifier son profil</title>
</head>
<body style="text-align: center">
<%@include file="Header.jsp" %>
<h1>Mon Profil</h1>
<div class="error">
    <c:if test="${!empty errorMessage}">
    <p>${errorMessage}</p>
    </c:if>
</div>
<form  action="<%= request.getContextPath() %>/ServletUpdateProfile" method="get">
  <table style="padding-left: 25%">
      <tbody>
      <tr>
          <th><label>Pseudo : </label></th>
          <th><input type="text" name="Pseudo" value="<%=user.getPseudo()%>"></th>
          <th><label>Nom : </label></th>
          <th><input type="text" name="Nom" value="<%=user.getNom()%>"></th>
      </tr>
      <tr>
          <th><label>Prénom : </label></th>
          <th><input type="text" name="Prenom" value="<%=user.getPrenom()%>"></th>
          <th><label>Email : </label></th>
          <th><input type="email" name="Email" value="<%=user.getEmail()%>"></th>
      </tr>
      <tr>
          <th><label>Téléphone : </label></th>
          <th><input type="tel" name="Telephone" value="<%=user.getTelephone()%>"></th>
          <th><label>Rue : </label></th>
          <th><input type="text" name="Rue" value="<%=user.getRue()%>"></th>
      </tr>
      <tr>
          <th><label>Code Postal : </label></th>
          <th><input type="String" name="CodePostal" value="<%=user.getCode_postal()%>"></th>
          <th><label>Ville : </label></th>
          <th><input type="text" name="Ville" value="<%=user.getVille()%>"></th>
      </tr>
      <tr>
          <th><label>Mot de passe actuel : </label></th>
          <th><input type="password" name="Password"></th>
      </tr>
      <tr>
          <th><label>Nouveau mot de passe : </label></th>
          <th><input type="password" name="NewPassword"></th>
          <th><label>Confirmation : </label></th>
          <th><input type="password" name="Confirmation"></th>
      </tr>
      <tr>
          <th>Credit : </th>
          <th><%=user.getCredit()%></th>
      </tr>
      <tr>
          <th></th>
          <th><button type="submit" name="action" value="enregistrer">Enregistrer</button></th>
          <th><button type="submit" name="action" value="supprimer">Supprimer mon compte</button></th>
          <th></th>
      </tr>
      </tbody>
  </table>
</form>
</body>
</html>
