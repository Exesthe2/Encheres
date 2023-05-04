package dal;

import bo.Categorie;

import java.util.List;

public interface CategorieDAO {

    Categorie selectById(int id);

    List<Categorie> selectAll();
}
