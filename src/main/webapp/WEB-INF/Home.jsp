<%--
  Created by IntelliJ IDEA.
  User: exesthef
  Date: 02/05/2023
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accueil</title>
</head>
<body>
<h1>Accueil</h1>
<c:if test="${ !empty sessionScope.user && !empty sessionScope.isConnected }">
    <p>Vous êtes ${ sessionScope.user } ${ sessionScope.isConnected } !</p>
</c:if>

<a href="<%=request.getContextPath() %>/ServletLogin">modif</a>
<a href="<%=request.getContextPath() %>/ServletProfile">Profil</a>
</body>
</html>
