package dal;

import bo.Retrait;

import java.sql.*;

public class RetraitJdbcImpl implements RetraitDAO {

    private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?);";

    @Override
    public void insert(Retrait retrait) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(INSERT);
            ps.setInt(1, retrait.getNo_article());
            ps.setString(2, retrait.getRue());
            ps.setString(3, retrait.getCodePostal());
            ps.setString(4, retrait.getVille());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
