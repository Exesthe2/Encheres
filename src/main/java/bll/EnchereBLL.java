package bll;

import bo.Enchere;
import dal.DAOFactory;
import dal.EnchereDAO;

import java.sql.SQLException;

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

    public int makeEnchere(int no_utilisateur, int no_article, int montant) throws SQLException{
        return dao.newEnchere(no_utilisateur,no_article, montant);
    }
}
