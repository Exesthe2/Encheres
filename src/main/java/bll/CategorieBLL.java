package bll;

import bo.Categorie;
import dal.CategorieDAO;
import dal.DAOFactory;

import java.util.List;

public class CategorieBLL {

    private CategorieDAO dao;

    public CategorieBLL() {
        dao = DAOFactory.getCategorieDAO();
    }

    public List<Categorie> selectAll() throws BLLException {
        return dao.selectAll();
    }
}
