package dal;

import bo.Article;

public interface ArticleDAO {

    void insert(Article article);
    void update(Article article);
    void delete(int id);
}
