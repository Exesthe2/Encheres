package bll;

import bo.Image;
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

    public void update(Image image) throws BLLException {
        dao.update(image);
    }

    public void selectById(int id) throws BLLException {
        dao.selectById(id);
    }
}
