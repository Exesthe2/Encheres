package dal;

import bo.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieJdbcImpl implements CategorieDAO {

    private static final String SELECTBYID = "SELECT * FROM CATEGORIES WHERE id = ?;";
    private static final String SELECT_ALL = "SELECT * FROM CATEGORIES;";

    @Override
    public Categorie selectById(int id) {

        Categorie categorie = null;
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categorie = new Categorie(
                        rs.getInt("no_categorie"),
                        rs.getString("libelle"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return categorie;
    }

    @Override
    public List<Categorie> selectAll() {
        List<Categorie> resultat = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setNo_categorie(rs.getInt("no_categorie"));
                categorie.setLibelle(rs.getString("libelle"));
                resultat.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultat;
    }

}
