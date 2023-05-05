package bll;

import bo.Article;
import bo.Categorie;
import dal.ArticleDAO;
import dal.DAOFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleBLL {

    private CategorieBLL categorieBLL = new CategorieBLL();

    private ArticleDAO dao;

    public ArticleBLL() {
        dao = DAOFactory.getArticleDAO();
    }

    public void insert(Article article) throws BLLException {
        // Recuperer la liste des categories
        List<Categorie> categories = categorieBLL.selectAll();
        String nom = article.getNom();
        String description = article.getDescription();
        LocalDateTime dateDebut = article.getDateDebut();
        LocalDateTime dateFin = article.getDateFin();
        Integer prixInitial = article.getPrixInitial();
        int no_categorie = article.getNo_categorie();

        if ("".equals(nom) || "".equals(description) || dateDebut == null || dateFin == null) {
            throw new BLLException("Un des champs obligatoires est vide");
        }
        if (nom.length() > 30) {
            throw new BLLException("le champ nom ne peux pas depasser 30 charactères");
        }
        if (description.length() > 300) {
            throw new BLLException("le champ description ne peux pas depasser 300 charactères");
        }
        if (dateDebut.isBefore(LocalDateTime.now())) {
            throw new BLLException("le champ date de début doit être supérieur à la date et heure actuelle");
        }
        if (dateFin.isBefore(LocalDateTime.now()) || dateFin.isBefore(dateDebut)) {
            throw new BLLException("le champ date de début doit être supérieur à la date de debut et la date et heure actuelle");
        }
        if (prixInitial < 0) {
            throw new BLLException("le champ prix initial n'est pas correct");
        }
        if (no_categorie < 0 || no_categorie > categories.size()) {
            throw new BLLException("le champ catégorie n'est pas correct");
        }

        dao.insert(article);
    }

    public void update(Article article) {
        dao.update(article);
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public List<Article> getAllArticlesWithFilters(HttpServletRequest request) throws BLLException {

        // Non connecté
        String articleName = request.getParameter("articleName");
        String categorie = request.getParameter("categorie");

        if (articleName == null) {
            articleName = "";
        }
        if (categorie == null) {
            categorie = "";
        }

        //Connecté
        boolean openAuctions = Boolean.parseBoolean(request.getParameter("openAuctions"));
        boolean myAuctions = Boolean.parseBoolean(request.getParameter("myAuctions"));
        boolean myAuctionsWin = Boolean.parseBoolean(request.getParameter("myAuctionsWin"));

        boolean myCurrentSales = Boolean.parseBoolean(request.getParameter("myCurrentSales"));
        boolean mySalesNotStart = Boolean.parseBoolean(request.getParameter("mySalesNotStart"));
        boolean mySalesEnd = Boolean.parseBoolean(request.getParameter("mySalesEnd"));

        HttpSession session = request.getSession();
        Object isConnected = session.getAttribute("isConnected");

        List<Article> articles = null;
        if (isConnected == null) {
            articles = dao.getAllArticlesInProgress(articleName, categorie);
        } else if (isConnected != null) {
            // Call methode pour utilisateur connecté.

        }

        return articles;
    }
}
