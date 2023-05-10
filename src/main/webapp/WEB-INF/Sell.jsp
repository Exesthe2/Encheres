<%@ page import="java.util.List" %>
<%@ page import="bo.Categorie" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bo.Users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
    Users user = (Users) session.getAttribute("user");
    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
    PrintWriter pw = response.getWriter();
%>
<html>
<head>
    <title>Vendre un article</title>
</head>
<body>
<%@include file="Header.jsp" %>
    <form method="POST" action="<%=request.getContextPath()%>/ServletArticle" enctype="multipart/form-data">
        <label for="article">Article*</label>
        <input type="text" name="article" id="article">
        <label for="description">Description*</label>
        <textarea name="description" id="description"></textarea>
        <label for="categorie">Categorie</label>
        <select name="categorie" id="categorie">
            <c:forEach items="${categories}" var="categorie" >
                <option value="<c:out value="${categorie.getNo_categorie()}"/>"><c:out value="${categorie.getLibelle()}"/></option>
            </c:forEach>
        </select>
        <label for="pictureFile">Photo</label>
        <input type="file" id="pictureFile" name="pictureFile" accept="image/png, image/jpeg" onchange="PreviewImage()"/>
        Preview de la nouvelle image <img id="uploadPreview" style="width: 100px; height: 100px;"/>
        <script type="text/javascript">
            function PreviewImage() {
                var test;
                var oFReader = new FileReader();
                oFReader.readAsDataURL(document.getElementById("pictureFile").files[0]);

                oFReader.onload = function (oFREvent) {
                    test = document.getElementById("uploadPreview").src = oFREvent.target.result;
                    test.value = "image"
                    console.log(test)
                };
            };
        </script>

        <label for="prixInital">Mise à prix</label>
        <input type="number" min="0" name="prixInitial" id="prixInital">
        <label for="dateDebut">Debut de l'enchère*</label>
        <input type="datetime-local" name="dateDebut" id="dateDebut">
        <label for="dateFin">Fin de l'enchère*</label>
        <input type="datetime-local" name="dateFin" id="dateFin">
        Retrait <table>
            <tr>
                <td>
                    <div>
                        <label for="rue">Rue*</label>
                        <input type="text" name="rue" id="rue" value="<%=user.getRue()%>">
                        <label for="codePostal">Code postal*</label>
                        <input type="text" name="codePostal" id="codePostal" value="<%=user.getCode_postal()%>">
                        <label for="ville">Ville* </label>
                        <input type="text" name="ville" id="ville" value="<%=user.getVille()%>">
                    </div>
                </td>
            </tr>
        </table>
        <input type="submit" value="Enregistrer">
    </form>
    <c:if test="${!empty error}">
        <p>${error}</p>
    </c:if>
</body>
</html>
