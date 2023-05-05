package bll;

import dal.DAOFactory;
import dal.EnchereDAO;

public class EnchereBLL {

    private EnchereBLL enchereBLL = new EnchereBLL();

    private EnchereDAO dao;

    public EnchereBLL() {
        dao = DAOFactory.getEnchereDAO();
    }
}
