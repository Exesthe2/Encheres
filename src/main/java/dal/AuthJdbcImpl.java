package dal;

import bo.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthJdbcImpl implements AuthDAO {

    private static final String LOGIN = "SELECT * FROM UTILISATEURS WHERE (email = ? OR pseudo= ?) AND mot_de_passe = ?;";
    private static final String SELECTBYID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?;";

    @Override
    public Users login(String emailOrPseudo, String password) {

        Users user = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(LOGIN);
            ps.setString(1, emailOrPseudo);
            ps.setString(2, emailOrPseudo);
            ps.setString(3, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                user = new Users(
                        rs.getInt("no_utilisateur"),
                        rs.getString("pseudo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("rue"),
                        rs.getString("code_postal"),
                        rs.getString("ville"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("credit"),
                        rs.getInt("administrateur"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Users SelectById(int id) {
        Users user = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("View");
                user = new Users(
                        rs.getInt("no_utilisateur"),
                        rs.getString("pseudo"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("rue"),
                        rs.getString("code_postal"),
                        rs.getString("ville"),
                        rs.getString("mot_de_passe"),
                        rs.getInt("credit"),
                        rs.getInt("administrateur"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}