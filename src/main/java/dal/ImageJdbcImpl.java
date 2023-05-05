package dal;

import bo.Image;

import java.sql.*;

public class ImageJdbcImpl implements ImageDAO {

    private static final String INSERT = "INSERT INTO IMAGES (no_article, image) VALUES (?,?);";
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
}
