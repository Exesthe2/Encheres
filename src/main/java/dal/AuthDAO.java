package dal;

import bo.Users;

public interface AuthDAO {
    Users login(String emailOrPseudo, String password);

    Users View(int id);
}
