package dal;

import bo.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthJdbcImpl implements AuthDAO {

    private static final String LOGIN = "SELECT * FROM UTILISATEURS WHERE (email = ? OR pseudo= ?) AND mot_de_passe = ?;";
    private static final String SELECTBYID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?;";
    private static final String DELETEUSER = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?;";
    private static final String VERIFPASSWORD = "SELECT mot_de_passe FROM UTILISATEURS WHERE no_utilisateur = ?;";
    private static final String UPDATEPROFILE = "UPDATE UTILISATEURS SET nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ? WHERE no_utilisateur=?;";

    private static final String UPDATEPROFILEWITHPASSWORD = "UPDATE UTILISATEURS SET nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur=?;";
    private static final String GETALLPSEUDOS = "SELECT pseudo, email FROM UTILISATEURS;";
    private static final String INSERT = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit, administrateur) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";


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
                        rs.getInt("credit"),
                        rs.getInt("administrateur"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void DeleteUser(int id) {
        try(Connection cnx = ConnectionProvider.getConnection();){
            PreparedStatement ps = cnx.prepareStatement(DELETEUSER);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String VerifPassword(int id) {
        String password = null;
        try(Connection cnx = ConnectionProvider.getConnection();){
            PreparedStatement ps = cnx.prepareStatement(VERIFPASSWORD);
            ps.setInt(1, id);
           ResultSet rs =  ps.executeQuery();
            while (rs.next()) {
                password = rs.getString("mot_de_passe");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }

    @Override
    public void UpdateProfile(Users user) throws SQLException {
        try(Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(UPDATEPROFILE);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelephone());
            ps.setString(5, user.getRue());
            ps.setString(6, user.getCode_postal());
            ps.setString(7, user.getVille());
            ps.setInt(8, user.getNo_utilisateur());
            ps.executeUpdate();

    }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void UpdateProfileWithPassWord(Users user) {
        try(Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(UPDATEPROFILEWITHPASSWORD);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelephone());
            ps.setString(5, user.getRue());
            ps.setString(6, user.getCode_postal());
            ps.setString(7, user.getVille());
            ps.setString(8, user.getMot_de_passe());
            ps.setInt(9, user.getNo_utilisateur());
            ps.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> pseudosAndEmails() {
        List<String> pseudos = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(GETALLPSEUDOS);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pseudos.add(rs.getString("pseudo"));
                pseudos.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pseudos;
    }

    @Override
    public Users registerUser(String pseudo, String firstname, String lastname, String phoneNumber, String postalCode, String street, String town, String email, String password, String confirmPassword) {
        Users user = null;

        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, pseudo);
            ps.setString(2, lastname);
            ps.setString(3, firstname);
            ps.setString(4, email);
            ps.setString(5, phoneNumber);
            ps.setString(6, street);
            ps.setString(7, postalCode);
            ps.setString(8, town);
            ps.setString(9, password);
            ps.setInt(10, 100);
            ps.setInt(11, 0);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            user = new Users(
                    id,
                    pseudo,
                    lastname,
                    firstname,
                    email,
                    phoneNumber,
                    street,
                    postalCode,
                    town,
                    100,
                    0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
