package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.dao.DAOProvider;
import hr.fer.zemris.java.dao.sql.SQLData;
import hr.fer.zemris.java.strcutures.PollsStructure;

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
	private boolean created;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties properties = new Properties();
		String connectionURL = null;
		try {
			properties.load(new FileInputStream(
					Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")).toFile()));

			System.out.println(properties.getProperty("host"));
			System.out.println(properties.getProperty("port"));
			System.out.println(properties.getProperty("name"));
			System.out.println(properties.getProperty("user"));
			System.out.println(properties.getProperty("password"));

			connectionURL = "jdbc:derby://" + properties.getProperty("host") + ":" + properties.getProperty("port")
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
		Connection connection = null;
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			connection = cpds.getConnection();
			createTables(connection);

			PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM Polls");

			ResultSet set = statement.executeQuery();
			set.next();

			if (set.getInt(1) == 0 || created) {
				fillTables(connection);
			}

			created = false;

			statement.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void createTables(Connection connection) {

		try {
			PreparedStatement statement = connection
					.prepareStatement("CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
							+ " title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)");
			statement.execute();

			try {
				statement.close();
			} catch (Exception e) {
			}

			statement = connection
					.prepareStatement("CREATE TABLE PollOptions(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
							+ "optionTitle VARCHAR(100) NOT NULL,optionLink VARCHAR(150) NOT NULL,pollID BIGINT,"
							+ "votesCount BIGINT,FOREIGN KEY (pollID) REFERENCES Polls(id));");
			statement.execute();
			try {
				statement.close();
			} catch (Exception e) {
			}

			created = true;
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				e.printStackTrace();
			}
		}
	}

	private void fillTables(Connection connection) {
		try {
			String polls = "INSERT INTO Polls(title,message) VALUES(";
			String pollsOptions = "INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES(";

			for (String string : SQLData.POLLS) {
				PreparedStatement statement = connection.prepareStatement(polls + string + ");");
				statement.executeUpdate();
				statement.close();
			}

			List<PollsStructure> list = DAOProvider.getDao().getPolls();

			fillItems(connection, SQLData.POLLSOPTIONS_BANDS, getID(list, "Voting for favourite band:"), pollsOptions);
			fillItems(connection, SQLData.POLLSOPTIONS_CARS, getID(list, "Voting for best car manufacturer:"),
					pollsOptions);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillItems(Connection connection, List<String> list, int id, String options) throws SQLException {
		for (String string : list) {
			PreparedStatement statement = connection.prepareStatement(options + string + ");");
			statement.setInt(1, id);
			statement.execute();
			statement.close();
		}

	}

	private int getID(List<PollsStructure> list, String string) {
		for (PollsStructure struc : list) {
			if (struc.getTitle().contains(string)) {
				return struc.getId();
			}
		}

		throw new IllegalArgumentException("Poll with title \'" + string + "\' doesn't exist!");
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