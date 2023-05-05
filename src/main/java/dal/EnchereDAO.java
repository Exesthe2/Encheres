package dal;

import bo.Enchere;

public interface EnchereDAO {

    Enchere selectById(int id);

    Enchere selectByArticleId(int id);
}
