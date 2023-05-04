package dal;

import bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> getAllArticlesInProgress(String articleName, String categorie);

    void insert(Article article);

    void update(Article article);

    void delete(int id);
}
