package dal;

import bo.Enchere;

import java.sql.SQLException;

public interface EnchereDAO {

    Enchere selectById(int id);

    Enchere selectByArticleId(int id);

    int newEnchere(int no_utilisateur, int no_article, int montant) throws SQLException;
}
