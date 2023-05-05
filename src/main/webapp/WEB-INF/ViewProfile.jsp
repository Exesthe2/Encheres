<%@ page import="bo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Users user = null;
    Users otherUser = (Users) request.getAttribute("user");
    Users ViewUser;
%>
<html>
<head>
    <title>Mon profil</title>
</head>

<%@include file="Header.jsp" %>

<body>
<div>
    <%
        if (otherUser == null) {
            user = (Users) session.getAttribute("user");
            ViewUser = user;
        } else {
            ViewUser = otherUser;
        }
    %>

    <p>Pseudo : <%=ViewUser.getPseudo()%></p>
    <p>Nom :  <%=ViewUser.getNom()%></p>
    <p>Prénom : <%=ViewUser.getPrenom()%></p>
    <p>Email : <%=ViewUser.getEmail()%></p>
    <p>Telephone : <%=ViewUser.getTelephone()%></p>
    <p>Rue : <%=ViewUser.getTelephone()%></p>
    <p>Code Postal : <%=ViewUser.getCode_postal()%></p>
    <p>Ville : <%=ViewUser.getVille()%></p>

    <% if (user != null) {
            if (ViewUser.getNo_utilisateur() == user.getNo_utilisateur()) {
    %>
                <form action="<%=request.getContextPath()%>/ServletProfile" method="post">
                    <button type="submit">Modifier</button>
                </form>
    <%
            }
        }
    %>

</div>
</body>
</html>
