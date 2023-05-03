package bll;

import bo.Users;
import dal.AuthDAO;
import dal.DAOFactory;

public class UserBLL {

    private AuthDAO dao;

    public UserBLL() {
        dao = DAOFactory.getAuthDAO();
    }

    public Users loginBLL(String emailOrPseudo, String password) throws BLLException {
        return dao.login(emailOrPseudo, password);
    }
    public Users SelectById(int id) { return dao.SelectById(id); }

    public void DeleteUser(int id){dao.DeleteUser(id);}
}
