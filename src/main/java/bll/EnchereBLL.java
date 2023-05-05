package bll;

import bo.Enchere;
import dal.DAOFactory;
import dal.EnchereDAO;

public class EnchereBLL {

    private EnchereDAO dao;

    public EnchereBLL() {
        dao = DAOFactory.getEnchereDAO();
    }

    public Enchere selectByArticleId(int id) throws  BLLException{
        Enchere enchere;
        enchere = dao.selectByArticleId(id);
        return enchere;
    }
}
