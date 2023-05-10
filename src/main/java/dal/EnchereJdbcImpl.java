package dal;

import bo.Enchere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EnchereJdbcImpl implements EnchereDAO {

    private static final String SELECTBYID = "SELECT * FROM ENCHERES WHERE no_enchere = ?;";
    private static final String SELECTBYARTICLEID = "SELECT * FROM ENCHERES WHERE no_article = ? ORDER BY montant_enchere;";
    private static final String SELECTDERNIEREENCHERE = "SELECT no_utilisateur, montant_enchere FROM ENCHERES WHERE no_article = ? and montant_enchere = (SELECT MAX(montant_enchere) FROM ENCHERES WHERE no_article = ? GROUP BY no_article)";
    private static final String SELECTCREDITFORDUSER ="SELECT credit FROM utilisateurs WHERE no_utilisateur = ?;";
    private static final String REMBOURSEMENT = "UPDATE utilisateurs SET  credit = ? WHERE no_utilisateur = ? and credit = ?;";
    private static final String NEWENCHERE = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (?, ?, NOW(), ?);";
    private static final String PAYERENCHERE =" UPDATE utilisateurs SET credit = ? WHERE no_utilisateur = ? and credit = ?;";

    @Override
    public Enchere selectById(int id) {
        Enchere enchere = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                enchere = new Enchere(
                        rs.getInt("no_enchere"),
                        rs.getInt("no_utilisateur"),
                        rs.getInt("no_article"),
                        LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()), rs.getTime("date_enchere").toLocalTime()),
                        rs.getInt("montant_enchere"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enchere;
    }

    @Override
    public Enchere selectByArticleId(int id) {
        Enchere enchere = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYARTICLEID);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                enchere = new Enchere(
                        rs.getInt("no_enchere"),
                        rs.getInt("no_utilisateur"),
                        rs.getInt("no_article"),
                        LocalDateTime.of((rs.getDate("date_enchere").toLocalDate()), rs.getTime("date_enchere").toLocalTime()),
                        rs.getInt("montant_enchere"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enchere;
    }

    @Override
    public int newEnchere(int no_utilisateur, int no_article, int montant) throws SQLException {
        int portefeuille = 0;
        int old_enchere = 0;
        int old_user = 0;
        int new_portefeuille = 0;
        int portefeuille_user = 0;
        int new_portefeuille_user = 0;
        Connection cnx = ConnectionProvider.getConnection();
        try ( cnx ) {
            cnx.setAutoCommit(false);
            // Selection de la derniere encheres
            PreparedStatement ps1 = cnx.prepareStatement(SELECTDERNIEREENCHERE);
            ps1.setInt(1, no_article);
            ps1.setInt(2, no_article);
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                old_user = Integer.parseInt(rs1.getString("no_utilisateur"));
                old_enchere = Integer.parseInt(rs1.getString("montant_enchere"));
            }
            if(montant <= old_enchere){
                cnx.rollback();
                throw new SQLException("l'enchere n'est pas assez haute");

            }
            // Selection du nombre de crédit actuel de old_user
            PreparedStatement ps2 = cnx.prepareStatement(SELECTCREDITFORDUSER);
            ps2.setInt(1 , old_user);
            ResultSet rs2 = ps2.executeQuery();
            while(rs2.next()){
                portefeuille = rs2.getInt("credit");
            }

            // Remboursement avec verification si pas de modification du nombre de credit
            new_portefeuille = portefeuille + old_enchere;
            PreparedStatement ps3 = cnx.prepareStatement(REMBOURSEMENT);
            ps3.setInt(1, new_portefeuille);
            ps3.setInt(2, old_user);
            ps3.setInt(3, portefeuille);
            ps3.executeUpdate();

            PreparedStatement ps4 = cnx.prepareStatement(SELECTCREDITFORDUSER);
            ps4.setInt(1, no_utilisateur);
            ResultSet rs4 = ps4.executeQuery();
            while(rs4.next()){
                portefeuille_user = rs4.getInt("credit");
            }
            // Retrait des crédit sur le compte user
            new_portefeuille_user = portefeuille_user - montant;
           if(new_portefeuille_user < 0){
               cnx.rollback();
               throw new SQLException("pas assez de credit");

           }
            PreparedStatement ps5 =cnx.prepareStatement(PAYERENCHERE);
            ps5.setInt(1, new_portefeuille_user);
            ps5.setInt(2, no_utilisateur);
            ps5.setInt(3, portefeuille_user);
            ps5.executeUpdate();
            // Création de l'enchere

            PreparedStatement ps6 = cnx.prepareStatement(NEWENCHERE);
            ps6.setInt(1,no_utilisateur);
            ps6.setInt(2, no_article);
            ps6.setInt(3, montant);

            ps6.executeUpdate();

            PreparedStatement ps7 = cnx.prepareStatement(SELECTCREDITFORDUSER);
            ps7.setInt(1, no_utilisateur);
            ResultSet rs7 = ps7.executeQuery();
            while ((rs7.next())){
                portefeuille_user = rs7.getInt("credit");
            }

            cnx.commit();

        }catch(SQLException e){
            e.printStackTrace();
            cnx.rollback();

        }
        return portefeuille_user;
    }
}
