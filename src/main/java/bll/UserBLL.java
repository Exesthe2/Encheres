package bll;

import bo.Users;
import dal.AuthDAO;
import dal.DAOFactory;

import java.util.List;

public class UserBLL {

    private AuthDAO dao;

    public UserBLL() {
        dao = DAOFactory.getAuthDAO();
    }

    public Users loginBLL(String emailOrPseudo, String password) throws BLLException {
        return dao.login(emailOrPseudo, password);
    }

    public List<String> pseudosAndEmailsBLL() throws BLLException {
        return dao.pseudosAndEmails();
    }

    public Users newUserBLL(String pseudo, String firstname, String lastname, String phoneNumber, String postalCode, String street, String town, String email, String password, String confirmPassword) throws BLLException {
        return dao.registerUser(pseudo, firstname, lastname, phoneNumber, postalCode, street, town, email, password, confirmPassword);
    }
}
