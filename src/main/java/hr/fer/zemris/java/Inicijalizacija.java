package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class represents starting point to server and implements
 * {@link ServletContextListener}<br>
 * In <code>contextInitialized</code>,method reads file with properties from
 * project location <code>src/main/webapp/WEB-INF/</code>. If file doesn't
 * exist,exception is thrown. Otherwise method creates connection with database
 * and stores in servlet context with attribute key
 * <code>hr.fer.zemris.dbpool</code>
 * 
 * @author Mihael
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties properties = new Properties();
		String connectionURL = null;
		try {
			properties.load(Files
					.newInputStream(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"))));

			if (!(properties.contains("host") && properties.contains("port") && properties.contains("name")
					&& properties.contains("user") && properties.contains("password"))) {
				throw new IOException("Properties file doesn't contain all requested items!");
			}

			connectionURL = "jdbc:derby://+" + properties.getProperty("host") + ":+" + properties.getProperty("port")
					+ "/" + properties.getProperty("name") + ";user=" + properties.getProperty("user") + ";password="
					+ properties.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		try {
			createTables(cpds.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cpds.close();
	}

	private void createTables(Connection connection) {

		try {

			createTable(connection, "CREATE TABLE PollOptions" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ "optionTitle VARCHAR(100) NOT NULL," + "optionLink VARCHAR(150) NOT NULL," + "pollID BIGINT,"
					+ "votesCount BIGINT," + "FOREIGN KEY (pollID) REFERENCES Polls(id)" + ");");

			createTable(connection, "CREATE TABLE Polls" + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ "title VARCHAR(150) NOT NULL," + "message CLOB(2048) NOT NULL);");

			PreparedStatement statement = connection.prepareStatement("SELECT * FROM pools");
			ResultSet set = statement.executeQuery();

			if (!set.next()) {
				fillTables(connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTable(Connection connection, String string) {
		try {
			connection.prepareStatement(string).execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillTables(Connection connection) {

		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO Pool(id,title,message) VALUES({1,\"Voting for favourite band:\","
							+ "\"From given bands,which one is your favoutite. Please click on link for voting\"},"
							+ "{2,\"Voting for best car manufacturer:\",\"From given manufactures,which one is your "
							+ "favourite?\"});");
			statement.executeUpdate();

			statement = connection
					.prepareStatement("INSERT INTO PollOptions(id,optionTitle,optionLink,pollID,votesCount) VALUES("
							+ "{1,\"The Beatles\",\"https://www.youtube.com/watch?v=z9ypq6_5bsg\",1,0},"
							+ "{2,\"The Pletters\",\"https://www.youtube.com/watch?v=H2di83WAOhU\",1,0},"
							+ "{3,\"The Beach Boys\",\"https://www.youtube.com/watch?v=2s4slliAtQU\",1,0}"
							+ "{4,\"The Four Seasons\",\"https://www.youtube.com/watch?v=y8yvnqHmFds\",1,0}"
							+ "{5,\"The Marcles\",\"https://www.youtube.com/watch?v=qoi3TH59ZEs\",1,0}"
							+ "{6,\"The Everly Brothers\",\"https://www.youtube.com/watch?v=tbU3zdAgiX8\",1,0}"
							+ "{7,\"The Mamas And The Papas\",\"https://www.youtube.com/watch?v=N-aK6JnyFmk\",1,0}"
							+ "{1,\"Subaru\",\"https://www.youtube.com/watch?v=6yWFacB_ayo\",2,0}"
							+ "{2,\"Fiat\",\"https://www.youtube.com/watch?v=WmpTqJ5i07U\",2,0}"
							+ "{3,\"VW\",\"https://www.youtube.com/watch?v=-UQ4SyKuVR4\",2,0});");
			statement.execute();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}