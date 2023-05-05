package dal;

import bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> getAllArticlesInProgress(String articleName, String categorie);

    List<Article> getAllArticlesWithConnectedFilters(String articleName, String categorie, String openAuctions, String closeAuctions, String createAuctions, boolean myAuctions, boolean myAuctionsWin, boolean myCurrentSales, boolean mySalesNotStart, boolean mySalesEnd);

    void insert(Article article);

    void update(Article article);

    void delete(int id);
}
