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

    public void insert(Retrait retrait) throws BLLException {
        String rue = retrait.getRue();
        String codePostal = retrait.getCodePostal();
        String ville = retrait.getVille();

        if ("".equals(rue) || "".equals(codePostal) || "".equals(ville)) {
            throw new BLLException("Un des champs obligatoires est vide");
        }
        if (rue.length() > 30) {
            throw new BLLException("le champ rue ne peux pas depasser 30 charactères");
        }
        if (codePostal.length() > 15) {
            throw new BLLException("le champ codePostal ne peux pas depasser 15 charactères");
        }
        if (ville.length() > 30) {
            throw new BLLException("le champ nom ne peux pas depasser 30 charactères");
        }

        dao.insert(retrait);
    }

    public void update(Retrait retrait) throws BLLException {
        String rue = retrait.getRue();
        String codePostal = retrait.getCodePostal();
        String ville = retrait.getVille();

        if ("".equals(rue) || "".equals(codePostal) || "".equals(ville)) {
            throw new BLLException("Un des champs obligatoires est vide");
        }
        if (rue.length() > 30) {
            throw new BLLException("le champ rue ne peux pas depasser 30 charactères");
        }
        if (codePostal.length() > 15) {
            throw new BLLException("le champ codePostal ne peux pas depasser 15 charactères");
        }
        if (ville.length() > 30) {
            throw new BLLException("le champ nom ne peux pas depasser 30 charactères");
        }

        dao.update(retrait);
    }

    public Retrait selectById(int id) throws BLLException{
        Retrait retrait = dao.selectById(id);
        return retrait;
    }
}
