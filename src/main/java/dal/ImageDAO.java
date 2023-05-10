package dal;

import bo.Image;

import java.util.List;

public interface ImageDAO {

    void insert(Image image);

    void update(Image image);

    Image selectById(int id);

    List<Image> selectAll();
}
