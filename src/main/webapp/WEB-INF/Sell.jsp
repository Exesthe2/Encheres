<%@ page import="java.util.List" %>
<%@ page import="bo.Categorie" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
    PrintWriter pw = response.getWriter();
%>
<html>
<head>
    <title>Vendre un article</title>
</head>
<body>
    <form method="POST" action="<%=request.getContextPath()%>/ServletArticle">
        Article* <input type="text" name="article">
        Description* <textarea name="description"></textarea>
        Categorie <select name="categorie">
            <c:forEach items="${categories}" var="categorie" >
                <option value="<c:out value="${categorie.getNo_categorie()}"/>"><c:out value="${categorie.getLibelle()}"/></option>
            </c:forEach>
        </select>
        Photo <input type="file" name="image" accept="image/png, image/jpeg">
        Mise à prix <input type="number" min="0" name="prixInitial">
        Debut de l'enchère* <input type="datetime-local" name="dateDebut">
        Fin de l'enchère* <input type="datetime-local" name="dateFin">
        Retrait <table>
            <tr>
                <td>
                    <div>
                        Rue* <input type="text" name="rue">
                        Code postal* <input type="text" name="codePostal">
                        Ville* <input type="text" name="ville">
                    </div>
                </td>
            </tr>
        </table>
        <input type="submit" name="action" value="Enregistrer">
        <input type="submit" name="action" value="Annuler">
    </form>
    <c:if test="${!empty error}">
        <p>${error}</p>
    </c:if>
</body>
</html>
