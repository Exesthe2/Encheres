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

    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, no_utilisateur, no_categorie, image) VALUES (?,?,?,?,?,?,?,?);";
    private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article=?, description=?, date_debut_enchere=?, date_fin_enchere=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=?, etat_vente=?, image=? WHERE id=?;";
    private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE id=?;";

    private static final String SELECTALLARTICLESINPROGRESS = "SELECT * FROM ARTICLES_VENDUS WHERE (nom_article LIKE ? and no_categorie like ?) AND etat_vente = 'EC';";

    private static final String GETALLARTICLEWITHCONNECTEDFILTERS = "SELECT * FROM ARTICLES_VENDUS WHERE (nom_article LIKE ? and no_categorie like ?) AND etat_vente IN (?, ?, ?);";
    private static final String GETENCHEREBYUSERIDANDARTICLEID = "SELECT no_enchere, no_utilisateur, no_article, date_enchere, MAX(montant_enchere) FROM ENCHERES WHERE no_article = ? AND no_utilisateur = ? group by no_enchere;";

    private static final String articleByUserIdAndState = "SELECT * FROM ARTICLES_VENDUS WHERE (nom_article LIKE ? and no_categorie like ? and no_utilisateur = ?) AND etat_vente IN (?, ?, ?);";
    private static final String enchereByArticleId = "SELECT no_enchere, no_utilisateur, no_article, date_enchere, MAX(montant_enchere) FROM ENCHERES WHERE no_article = ? group by no_enchere;";

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
                        rs.getString("etat_vente"),
                        rs.getString("image")
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
    public List<Article> getAllArticlesWithConnectedFilters(String articleName, String categorie, String openAuctions, String closeAuctions, String createAuctions, String myAuctions, String myAuctionsWin) {
        List<Article> articles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(GETALLARTICLEWITHCONNECTEDFILTERS);
            ps.setString(1, "%" + articleName + "%");
            ps.setString(2, "%" + categorie + "%");
            ps.setString(3, openAuctions);
            ps.setString(4, closeAuctions);
            ps.setString(5, createAuctions);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int no_article = rs.getInt("no_article");
                if (myAuctions != null || myAuctionsWin != null) {
                    try (Connection cnx2 = ConnectionProvider.getConnection()) {
                        PreparedStatement ps2 = cnx2.prepareStatement(GETENCHEREBYUSERIDANDARTICLEID);
                        ps2.setInt(1, no_article);
                        ps2.setInt(2, Integer.parseInt(myAuctions));

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            while (rs2.next()) {
                                enchere = new Enchere(
                                        rs2.getInt("no_enchere"),
                                        rs2.getInt("no_utilisateur"),
                                        rs2.getInt("no_article"),
                                        LocalDateTime.of((rs2.getDate("date_enchere").toLocalDate()), rs2.getTime("date_enchere").toLocalTime()),
                                        rs2.getInt("MAX(montant_enchere)")
                                );
                            }
                        }
                    }
                }
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
                        rs.getString("etat_vente"),
                        rs.getString("image"));
                article.setEnchere(enchere);
                articles.add(article);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return articles;
    }

    @Override
    public List<Article> getAllArticlesForConnectedUser(String articleName, String categorie, int no_utilisateur, String myCurrentSales, String mySalesNotStart, String mySalesEnd) {
        List<Article> articles = new ArrayList<>();

        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(articleByUserIdAndState);
            ps.setString(1, "%" + articleName + "%");
            ps.setString(2, "%" + categorie + "%");
            ps.setInt(3, no_utilisateur);
            ps.setString(4, myCurrentSales);
            ps.setString(5, mySalesNotStart);
            ps.setString(6, mySalesEnd);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int no_article = rs.getInt("no_article");
                try (Connection cnx2 = ConnectionProvider.getConnection()) {
                    PreparedStatement ps2 = cnx2.prepareStatement(enchereByArticleId);
                    ps2.setInt(1, no_article);

                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        while (rs2.next()) {
                            enchere = new Enchere(
                                    rs2.getInt("no_enchere"),
                                    rs2.getInt("no_utilisateur"),
                                    rs2.getInt("no_article"),
                                    LocalDateTime.of((rs2.getDate("date_enchere").toLocalDate()), rs2.getTime("date_enchere").toLocalTime()),
                                    rs2.getInt("MAX(montant_enchere)")
                            );
                        }
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
                                rs.getString("etat_vente"),
                                rs.getString("image")
                        );
                        article.setEnchere(enchere);
                        articles.add(article);
                    }
                }
            }
        } catch (SQLException e) {
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
            ps.setString(8, article.getImage());
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
