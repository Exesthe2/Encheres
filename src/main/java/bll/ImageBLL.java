package bll;

import bo.Image;
import dal.ArticleDAO;
import dal.DAOFactory;
import dal.ImageDAO;

public class ImageBLL {
    private ImageDAO dao;

    public ImageBLL() {
        dao = DAOFactory.getImageDAO();
    }

    public void insert(Image image) throws BLLException {
        dao.insert(image);
    }
}
