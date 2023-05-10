<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="style/style.css">
	<title>Connexion</title>
</head>
<body>
<%@include file="Header.jsp" %>
<div class="error">
	<c:if test="${!empty error}">
		<p>${error}</p>
	</c:if>
</div>
<div>
	<form action="<%= request.getContextPath() %>/ServletLogin" method="post">
		<label>Email ou Pseudo</label>
		<input type="text" name="emailOrPseudo" required>

		<label>Mot de passe</label>
		<input type="password" name="password" required>

		<button type="submit">Login</button>
	</form>
	<a href="<%=request.getContextPath()%>/ServletRegister">Créer un compte</a>
</div>
</body>
</html>
