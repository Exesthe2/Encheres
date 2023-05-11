package bll;

import bo.Article;
import bo.Categorie;
import bo.Users;
import dal.ArticleDAO;
import dal.DAOFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        String articleName = request.getParameter("articleName");
        String categorie = request.getParameter("categorie");

        if (articleName == null) {
            articleName = "";
        }
        if (categorie == null) {
            categorie = "";
        }

        HttpSession session = request.getSession();
        Object isConnected = session.getAttribute("isConnected");
        request.setAttribute("isConnected", isConnected);
        Users user = (Users) session.getAttribute("user");

        List<Article> articles = null;

        articles = dao.getAllArticlesInProgress(articleName, categorie);

        if (isConnected != null) {
            String radioButton = request.getParameter("buyOrSell");

            if (("buy").equals(radioButton)) {
                String openAuctions = request.getParameter("openAuctions");
                String myAuctions = request.getParameter("myAuctions");
                String myAuctionsWin = request.getParameter("myAuctionsWin");

                if (openAuctions != null) {
                    articles = dao.getAllArticlesInProgress(articleName, categorie);
                } else if (myAuctions != null) {
                    articles = dao.getMyArticle(articleName, categorie, user.getNo_utilisateur());
                } else if (myAuctionsWin != null) {
                    articles = dao.getMyWonArticle(articleName, categorie, user.getNo_utilisateur());
                }
            } else if (("sell").equals(radioButton)) {
                String myCurrentSales = request.getParameter("myCurrentSales");
                String mySalesNotStart = request.getParameter("mySalesNotStart");
                String mySalesEnd = request.getParameter("mySalesEnd");

                if (myCurrentSales != null) {
                    articles = dao.getMyCurrentSell(articleName, categorie, user.getNo_utilisateur());
                } else if (mySalesNotStart != null) {
                    articles = dao.getMySellNotStarted(articleName, categorie, user.getNo_utilisateur());
                } else if (mySalesEnd != null) {
                    articles = dao.getMySellEnded(articleName, categorie, user.getNo_utilisateur());
                }
            }
        }
        return articles;
    }

    public Article selectById(int id) throws BLLException{

        Article article = dao.selectById(id);
        return article;
    }

    public Boolean canModify(int idArticle, int idUtilisateur) throws BLLException {
        int idUtilisateurArticle = Integer.parseInt(dao.canModify(idArticle));
        if (idUtilisateur != idUtilisateurArticle) {
            throw new BLLException("Cet utilisateur ne peut pas modifier cet article car il ne lui appartient pas");
        }
        return true;
    }
}
