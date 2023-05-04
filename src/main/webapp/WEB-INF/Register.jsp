<%--
  Created by IntelliJ IDEA.
  User: exesthef
  Date: 03/05/2023
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>Register</title>
</head>
<body>
<%@include file="Header.jsp" %>
<c:if test="${!empty error}">
	<p>${error}</p>
</c:if>
<form action="<%= request.getContextPath() %>/ServletRegister" method="post">
	<label>Pseudo</label>
	<input type="text" name="pseudo" required>

	<label>Prénom</label>
	<input type="text" name="firstname" required>

	<label>Nom</label>
	<input type="text" name="lastname" required>

	<label>Téléphone</label>
	<input type="text" name="phoneNumber" required>

	<label>Code postal</label>
	<input type="text" name="postalCode" required>

	<label>Rue</label>
	<input type="text" name="street" required>

	<label>Ville</label>
	<input type="text" name="town" required>

	<label>Email</label>
	<input type="text" name="email" required>

	<label>Mot de passe</label>
	<input type="password" name="password" required>

	<label>Confirmer votre mot de passe</label>
	<input type="password" name="confirmPassword" required>

	<button type="submit">Créer</button>
</form>
<a href="<%=request.getContextPath()%>/ServletLogin">Annuler</a>
</body>
</html>
