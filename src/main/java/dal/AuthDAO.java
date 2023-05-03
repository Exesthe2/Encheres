package dal;

import bo.Users;

import java.util.List;

public interface AuthDAO {
    Users login(String emailOrPseudo, String password);

    List<String> pseudosAndEmails();

    Users registerUser(String pseudo, String firstname, String lastname, String phoneNumber, String postalCode, String street, String town, String email, String password, String confirmPassword);
}
