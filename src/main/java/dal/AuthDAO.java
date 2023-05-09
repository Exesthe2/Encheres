package dal;

import bo.Users;

import java.sql.SQLException;
import java.util.List;

public interface AuthDAO {
    Users login(String emailOrPseudo, String password);

    Users SelectById(int id);

    void DeleteUser(int id);

    String VerifPassword(int id);

    void UpdateProfile(Users user) throws SQLException;

    void UpdateProfileWithPassWord(Users user);

    List<String> pseudosAndEmails();

    Users registerUser(String pseudo, String firstname, String lastname, String phoneNumber, String postalCode, String street, String town, String email, String password, String confirmPassword);

    void auctionsTimer(String cancel);
}
