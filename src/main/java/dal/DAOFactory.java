package dal;

public class DAOFactory {
    private static AuthDAO authDAO;

    public static AuthDAO getAuthDAO() {
        if (authDAO == null) {
            authDAO = new AuthJdbcImpl();
        }
        return authDAO;
    }
}
