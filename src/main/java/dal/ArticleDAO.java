package dal;

import bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> getAllArticlesInProgress(String articleName, String categorie);

    List<Article> getAllArticlesWithConnectedFilters(String articleName, String categorie, String openAuctions, String closeAuctions, String createAuctions, String myAuctions, String myAuctionsWin);

    List<Article> getAllArticlesForConnectedUser(String articleName, String categorie, int no_utilisateur, String myCurrentSales, String mySalesNotStart, String mySalesEnd);

    void insert(Article article);

    void update(Article article);

    void delete(int id);
}
