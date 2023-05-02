import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestConnexionBDDServlet")
public class TestConnexionBDDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Context context = new InitialContext();
			DataSource ds =
					(DataSource) context.lookup("java:comp/env/jdbc/pool_cnx");
			Connection cnx = ds.getConnection();
			
			if (!cnx.isClosed()) {
				response.getWriter().append("La connexion est ouverte !");
				cnx
					.prepareStatement("CREATE TABLE test(id integer);")
					.executeUpdate();
			}
			
			cnx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}






