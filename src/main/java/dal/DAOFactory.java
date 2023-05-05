package dal;

public class DAOFactory {
    private static AuthDAO authDAO;
    private static ArticleDAO articleDAO;
    private static CategorieDAO categorieDAO;
    private static RetraitDAO retraitDAO;
    private static EnchereDAO enchereDAO;
    private static ImageDAO imageDAO;

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
            retraitDAO = new RetraitJdbcImpl();
        }
        return retraitDAO;
    }

    public static EnchereDAO getEnchereDAO() {
        if (enchereDAO == null) {
            enchereDAO = new EnchereJdbcImpl();
        }
        return enchereDAO;
    }

    public static ImageDAO getImageDAO() {
        if (imageDAO == null) {
            imageDAO = new ImageJdbcImpl();
        }
        return imageDAO;
    }
}
