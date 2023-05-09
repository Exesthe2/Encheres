package dal;

import bo.Image;

public interface ImageDAO {

    void insert(Image image);

    void update(Image image);

    Image selectById(int id);
}
