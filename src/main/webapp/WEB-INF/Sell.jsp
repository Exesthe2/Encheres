<%@ page import="java.util.List" %>
<%@ page import="bo.Categorie" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <input type="text" name="article">
        <textarea name="description"/>
        <select>
            <option value="" disabled></option>
            <% for (Categorie categorie: categories) {
                pw.println("<option value=" + categorie.getNo_categorie() + ">" +
                            categorie.getLibelle() + "</option>"
                            );
             } %>
        </select>
        <input type="number" min="0" name="prixInitial">
        <input type="datetime-local" name="dateDepart">
        <input type="datetime-local" name="dateFin">
        <table>
            <tr>
                <td>
                    <div>
                        <input type="text">
                        <input type="text">
                        <input type="text">
                    </div>
                </td>
            </tr>
        </table>
        <input type="submit" name="action" value="Enregistrer">
        <input type="submit" name="action" value="Annuler">
    </form>
</body>
</html>
