package dal;

import bo.Users;

import java.sql.SQLException;

public interface AuthDAO {
    Users login(String emailOrPseudo, String password);

    Users SelectById(int id);

    void DeleteUser(int id);

    String VerifPassword(int id);

    void UpdateProfile(Users user) throws SQLException;

    void UpdateProfileWithPassWord(Users user);
}
