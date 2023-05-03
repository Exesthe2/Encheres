package dal;

import bo.Users;

public interface AuthDAO {
    Users login(String emailOrPseudo, String password);

    Users SelectById(int id);

    void DeleteUser(int id);
}
