package dal;

import bll.BLLException;
import bll.EnchereBLL;
import bo.Article;
import bo.Enchere;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleJdbcImpl implements ArticleDAO {

    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, no_utilisateur, no_categorie) VALUES (?,?,?,?,?,?,?);";
    private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_enchere=?, date_fin_enchere=?, prix_initial=?, no_utilisateur=?, no_categorie=? WHERE no_article=?;";
    private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE id=?;";
    private static final String SELECTALLARTICLESINPROGRESS = "SELECT * FROM ARTICLES_VENDUS WHERE (nom_article LIKE ? and no_categorie like ?) AND etat_vente = 'EC'";
    private static final String SELECTBYID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = ?;";
    private static final String CANMODIFY = "SELECT no_utilisateur FROM ARTICLES_VENDUS WHERE no_article=?;";

    private EnchereBLL enchereBLL = new EnchereBLL();
    private Enchere enchere;

    @Override
    public List<Article> getAllArticlesInProgress(String articleName, String categorie) {

        List<Article> articles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(SELECTALLARTICLESINPROGRESS);
            ps.setString(1, "%" + articleName + "%");
            ps.setString(2, "%" + categorie + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("no_article"),
                        rs.getString("nom_article"),
                        rs.getString("description"),
                        LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()), rs.getTime("date_debut_enchere").toLocalTime()),
                        LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()), rs.getTime("date_fin_enchere").toLocalTime()),
                        rs.getInt("prix_initial"),
                        rs.getInt("prix_vente"),
                        rs.getInt("no_utilisateur"),
                        rs.getInt("no_categorie"),
                        rs.getString("etat_vente")
                );

                int no_article = rs.getInt("no_article");
                enchere = enchereBLL.selectByArticleId(no_article);
                article.setEnchere(enchere);
                articles.add(article);
            }
        } catch (SQLException | BLLException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }

    @Override
    public void insert(Article article) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, article.getNom());
            ps.setString(2, article.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(article.getDateDebut()));
            ps.setTimestamp(4, Timestamp.valueOf(article.getDateFin()));
            ps.setInt(5, article.getPrixInitial());
            ps.setInt(6, article.getNo_utilisateur());
            ps.setInt(7, article.getNo_categorie());
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
            ps.setInt(6, article.getNo_utilisateur());
            ps.setInt(7, article.getNo_categorie());
            ps.setInt(8, article.getNo_article());
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

    @Override
    public Article selectById(int id) throws BLLException {
        Article article = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                article = new Article(
                        rs.getInt("no_article"),
                        rs.getString("nom_article"),
                        rs.getString("description"),
                        LocalDateTime.of((rs.getDate("date_debut_enchere").toLocalDate()), rs.getTime("date_debut_enchere").toLocalTime()),
                        LocalDateTime.of((rs.getDate("date_fin_enchere").toLocalDate()), rs.getTime("date_fin_enchere").toLocalTime()),
                        rs.getInt("prix_initial"),
                        rs.getInt("prix_vente"),
                        rs.getInt("no_utilisateur"),
                        rs.getInt("no_categorie"),
                        rs.getString("etat_vente")
                );
            }
            return article;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String canModify(int idArticle) {
        String id = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, idArticle);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getString("no_utilisateur");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
