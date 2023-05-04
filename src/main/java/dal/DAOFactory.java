package dal;

public class DAOFactory {
    private static AuthDAO authDAO;
    private static ArticleDAO articleDAO;
    private static CategorieDAO categorieDAO;
    private static RetraitDAO retraitDAO;

    public static AuthDAO getAuthDAO() {
        if (authDAO == null) {
            authDAO = new AuthJdbcImpl();
        }
        return authDAO;
    }

    public static ArticleDAO getArticleDAO() {
        if (articleDAO == null) {
            articleDAO = new ArticleJdbcImpl();
        }
        return articleDAO;
    }

    public static CategorieDAO getCategorieDAO() {
        if (categorieDAO == null) {
            categorieDAO = new CategorieJdbcImpl();
        }
        return categorieDAO;
    }

    public static RetraitDAO getRetraitDAO() {
        if (retraitDAO == null) {
            retraitDAO  = new RetraitJdbcImpl();
        }
        return retraitDAO;
    }
}
