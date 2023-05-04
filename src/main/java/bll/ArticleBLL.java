package bll;

import bo.Article;
import dal.ArticleDAO;
import dal.DAOFactory;

public class ArticleBLL {

    private ArticleDAO dao;

    public ArticleBLL() {
        dao = DAOFactory.getArticleDAO();
    }

    public void insert(Article article) {dao.insert(article);}

    public void update(Article article) {
        dao.update(article);
    }

    public void delete(int id) {
        dao.delete(id);
    }
}
