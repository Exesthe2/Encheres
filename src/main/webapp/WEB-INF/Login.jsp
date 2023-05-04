<%--
  Created by IntelliJ IDEA.
  User: exesthef
  Date: 02/05/2023
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
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

	<c:if test="${!empty BLLException}">
		<p>La BLLException est : ${BLLException}</p>
		<div>
			<strong>Erreur de saisie.</strong>
			<ul>
				<c:forEach var="code" items="${BLLException }">
					<c:choose>
						<c:when test="${code == 1 }">
							<li>Le nom de la liste ne doit pas être vide.</li>
						</c:when>
						<c:when test="${code == 2 }">
							<li>Le nom de l'article ne doit pas être vide.</li>
						</c:when>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
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
