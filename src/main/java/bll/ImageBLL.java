package bll;

import bo.Image;
import dal.DAOFactory;
import dal.ImageDAO;

import java.util.List;

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

    public Image selectById(int id) throws BLLException {
        return dao.selectById(id);
    }

    public List<Image> selectAll() throws BLLException {
        return dao.selectAll();
    }
}
