package dal;

import bo.Retrait;

import java.sql.*;

public class RetraitJdbcImpl implements RetraitDAO {

    private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?);";
    private static final String SELECTBYID = "SELECT * FROM RETRAITS WHERE no_article = ?;";
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

    @Override
    public Retrait selectById(int id) {
        Retrait retrait = null;
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                retrait =new Retrait(
                        rs.getInt("no_article"),
                        rs.getString("rue"),
                        rs.getString("code_postal"),
                        rs.getString("ville")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return retrait;
    }
}
