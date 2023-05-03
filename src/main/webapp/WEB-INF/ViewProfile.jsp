<%@ page import="bo.Users" %><%--
  Created by IntelliJ IDEA.
  User: renau
  Date: 02/05/2023
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Users user = (Users) session.getAttribute("user"); %>
<% Users otherUser = (Users) request.getAttribute("user"); %>
<% Users ViewUser; %>
<html>
<head>
    <title>Profile</title>
</head>

<%--<%@include file="Header.jsp"%>--%>

<body>
<div>
    <% if(otherUser == null){
        ViewUser = user;
    }
    else{
        ViewUser = otherUser;
    }%>
    <p>Pseudo : <%=ViewUser.getPseudo()%></p>
    <p>Nom :  <%=ViewUser.getNom()%></p>
    <p>Prénom : <%=ViewUser.getPrenom()%></p>
    <p>Email : <%=ViewUser.getEmail()%></p>
    <p>Telephone : <%=ViewUser.getTelephone()%></p>
    <p>Rue : <%=ViewUser.getTelephone()%></p>
    <p>Code Postal : <%=ViewUser.getCode_postal()%></p>
    <p>Ville : <%=ViewUser.getVille()%></p>

    <% if(ViewUser.getNo_utilisateur() == user.getNo_utilisateur()){%>
    <form action="<%= request.getContextPath() %>/ServletProfile" method="post">
        <button type="submit">Modifier</button>
    </form>
    <%}%>


</div>
</body>
</html>
