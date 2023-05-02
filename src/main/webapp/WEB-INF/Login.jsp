<%--
  Created by IntelliJ IDEA.
  User: exesthef
  Date: 02/05/2023
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
<div>
    <form action="<%= request.getContextPath() %>/ServletLogin" method="post">
        <label>Email ou Pseudo</label>
        <input type="text" name="emailOrPseudo" required>

        <label>Mot de passe</label>
        <input type="password" name="password" required>

        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>
