package dal;

import bo.Image;

import java.sql.*;

public class ImageJdbcImpl implements ImageDAO {

    private static final String INSERT = "INSERT INTO IMAGES (no_article, image) VALUES (?,?);";
    private static final String UPDATE = "UPDATE IMAGES SET image=? WHERE no_article=?;";
    private static final String SELECTBYID = "SELECT * FROM IMAGES WHERE no_article=?";
    @Override
    public void insert(Image image) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, image.getNo_article());
            ps.setString(2, image.getImage());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                image.setNo_image(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Image image) {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(UPDATE);
            ps.setString(1, image.getImage());
            ps.setInt(2, image.getNo_article());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Image selectById(int id) {
        Image image = null;
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement ps = cnx.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                image= new Image(
                        rs.getInt("no_article"),
                        rs.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return image;
    }


}
