package dal;

import bo.Enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EnchereJdbcImpl implements EnchereDAO {

    private static final String SELECTBYID = "SELECT * FROM ENCHERES WHERE no_enchere = ?;";

    @Override
    public Enchere selectById(int id) {
        Enchere enchere = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                enchere = new Enchere(
                        rs.getInt("no_enchere"),
                        rs.getInt("no_utilisateur"),
                        rs.getInt("no_article"),
                        LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()), rs.getTime("date_enchere").toLocalTime()),
                        rs.getInt("montant_enchere"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enchere;
    }
}
