package bll;

import bo.Article;
import dal.ArticleDAO;
import dal.DAOFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ArticleBLL {

    private ArticleDAO dao;

    public ArticleBLL() {
        dao = DAOFactory.getArticleDAO();
    }

    public void insert(Article article) {
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
