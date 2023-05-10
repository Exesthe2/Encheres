<%@ page import="bo.Users" %>
<%@ page import="bo.Article" %>
<%@ page import="bo.Image" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Users users = (Users) request.getSession().getAttribute("user");
  Article article = (Article) request.getAttribute("article");
  Image image = (Image) request.getAttribute("image");
%>
<html>
<head>
    <title>Modification de l'article</title>
</head>
<body>

<%@include file="Header.jsp" %>
<form method="POST" action="<%=request.getContextPath()%>/ServletModificationArticle" enctype="multipart/form-data">
  <c:if test="${'' != image.getImage()}">
    <img src="${pageContext.request.contextPath}/uploads/${image.getImage()}" alt="${article.getNom()}" />
  </c:if>
  <input type="hidden" name="id" value="<%=article.getNo_article()%>">
  <label for="article">Article*</label>
  <input type="text" name="article" id="article" value="<%=article.getNom()%>">
  <label for="description">Description*</label>
  <textarea name="description" id="description"><%=article.getDescription()%></textarea>
  <label for="categorie">Categorie</label>
  <select name="categorie" id="categorie">
    <c:forEach items="${categories}" var="categorie" >
      <c:if test="${categorie.getNo_categorie() == article.getNo_categorie()}">
        <option value="<c:out value="${categorie.getNo_categorie()}"/>" selected><c:out value="${categorie.getLibelle()}"/></option>
      </c:if>
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
  <input type="number" min="0" name="prixInitial" id="prixInital" value="<%=article.getPrixInitial()%>">
  <label for="dateDebut">Debut de l'enchère*</label>
  <input type="datetime-local" name="dateDebut" id="dateDebut" value="<%=article.getDateDebut()%>">
  <label for="dateFin">Fin de l'enchère*</label>
  <input type="datetime-local" name="dateFin" id="dateFin" value="<%=article.getDateFin()%>">
  Retrait <table>
  <tr>
    <td>
      <div>
        <label for="rue">Rue*</label>
        <input type="text" name="rue" id="rue" value="<%=users.getRue()%>">
        <label for="codePostal">Code postal*</label>
        <input type="text" name="codePostal" id="codePostal" value="<%=users.getCode_postal()%>">
        <label for="ville">Ville* </label>
        <input type="text" name="ville" id="ville" value="<%=users.getVille()%>">
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
