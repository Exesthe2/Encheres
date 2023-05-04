package bll;

import bo.Article;
import bo.Retrait;
import dal.DAOFactory;
import dal.RetraitDAO;

public class RetraitBLL {

    private RetraitDAO dao;

    public RetraitBLL(){
        dao = DAOFactory.getRetraitDAO();
    }

    public void insert(Retrait retrait) {
        dao.insert(retrait);
    }
}
