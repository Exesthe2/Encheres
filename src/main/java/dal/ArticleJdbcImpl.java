package dal;

import bo.Article;

import java.sql.*;

public class ArticleJdbcImpl implements ArticleDAO {

    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente, image) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_enchere=?, date_fin_enchere=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, etat_vente=?, image=? WHERE id=?;";
    private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE id=?;";
    @Override
    public void insert(Article article) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDate(3, Date.valueOf(article.getDateDebut().toLocalDate()));
            ps.setDate(4, Date.valueOf(article.getDateFin().toLocalDate()));
            ps.setInt(5, article.getPrixInitial());
            ps.setInt(6, article.getPrixVente());
            ps.setInt(7, article.getNo_utilisateur());
            ps.setInt(8, article.getNo_categorie());
            ps.setString(9, article.getEtatVente());
            ps.setString(10, article.getImage());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                article.setNo_article(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Article article) {
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(UPDATE);
            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setDate(3, Date.valueOf(article.getDateDebut().toLocalDate()));
            ps.setDate(4, Date.valueOf(article.getDateFin().toLocalDate()));
            ps.setInt(5, article.getPrixInitial());
            ps.setInt(6, article.getPrixVente());
            ps.setInt(7, article.getNo_utilisateur());
            ps.setInt(8, article.getNo_categorie());
            ps.setString(9, article.getEtatVente());
            ps.setString(10, article.getImage());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(DELETE);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
