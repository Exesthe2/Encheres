package dal;

import bll.BLLException;
import bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> getAllArticlesInProgress(String articleName, String categorie);
    List<Article> getMyArticle(String articleName, String categorie, int id);
    List<Article> getMyWonArticle(String articleName, String categorie, int id);
    List<Article> getMyCurrentSell(String articleName, String categorie, int id);
    List<Article> getMySellNotStarted(String articleName, String categorie, int id);
    List<Article> getMySellEnded(String articleName, String categorie, int id);

    void insert(Article article);

    void update(Article article);

    void delete(int id);

    Article selectById(int id) throws BLLException;

    String canModify(int idArticle);


}
