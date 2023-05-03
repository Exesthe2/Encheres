<%@ page import="bo.Users" %><%--
  Created by IntelliJ IDEA.
  User: renau
  Date: 02/05/2023
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Users user = (Users) session.getAttribute("user"); %>
<html>
<head>
    <title>Profile</title>
</head>

<%--<%@include file="Header.jsp"%>--%>

<body>
<div>
    <p>Pseudo : <%=user.getPseudo()%></p>
    <p>Nom :  <%=user.getNom()%></p>
    <p>Prénom : <%=user.getPrenom()%></p>
    <p>Email : <%=user.getEmail()%></p>
    <p>Telephone : <%=user.getTelephone()%></p>
    <p>Rue : <%=user.getTelephone()%></p>
    <p>Code Postal : <%=user.getCode_postal()%></p>
    <p>Ville : <%=user.getVille()%></p>
    <form action="ViewProfile.jsp" method="post">
        <button type="submit">Modifier</button>
    </form>
</div>
</body>
</html>
