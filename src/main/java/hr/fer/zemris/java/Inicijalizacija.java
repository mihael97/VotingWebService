package hr.fer.zemris.java;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.dao.sql.SQLData;

/**
 * Class represents starting point of server and implements
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
	/**
	 * Variable shows if tables are created because they didn't exist
	 */
	private boolean created;

	/**
	 * Method prepares connection functionality<br>
	 * Firstly,method loads properties from disc and initializes
	 * {@link ComboPooledDataSource} with loaded data. Secondly calls methods for
	 * checking if tables exist and eventually fills table with data
	 * 
	 * @param sce
	 *            - {@link ServletContextEvent} servlet event
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties properties = new Properties();
		String connectionURL = null;
		try {
			properties.load(new FileInputStream(
					Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")).toFile()));

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

	/**
	 * Method creates two tables,one for polls second for poll's data,if they don't
	 * exist
	 * 
	 * @param connection
	 *            - connection
	 * @throws SQLException
	 *             - if exception during table making appears
	 */
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
							+ "votesCount BIGINT,FOREIGN KEY (pollID) REFERENCES Polls(id))");
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

	/**
	 * Method fills every table(one with polls,other with pool's items) with data
	 * 
	 * @param connection
	 *            - connection
	 * @throws SQLException
	 *             - if exception during insertion appears
	 */
	private void fillTables(Connection connection) throws SQLException {

		fillTable(connection, SQLData.POLLSOPTIONS_BANDS);
		fillTable(connection, SQLData.POLLSOPTIONS_CARS);
	}

	/**
	 * Method fills created table with data
	 * 
	 * @param connection
	 *            - connection
	 * @param options
	 *            - data we want to insert
	 * @throws SQLException
	 *             - if exception during insertion appears
	 * @throws IllegalArgumentException
	 *             - if id(poll ID) is lower than 0
	 */
	private void fillTable(Connection connection, List<String> options) throws SQLException {
		String polls = "INSERT INTO Polls(title,message) VALUES(";
		String pollsOptions = "INSERT INTO PollOptions(optionTitle,optionLink,pollID,votesCount) VALUES(";

		for (int i = 0, id = -1; i < options.size(); i++) {
			if (i == 0) {
				Statement statement = connection.createStatement();
				statement.executeUpdate(polls + options.get(i) + ")", Statement.RETURN_GENERATED_KEYS);
				ResultSet set = statement.getGeneratedKeys();
				set.next();
				id = set.getInt(1);
				statement.close();
			} else {
				if (id < 0) {
					throw new IllegalArgumentException("Id is " + id + " but must be greather or equal to zero!");
				}

				PreparedStatement statement = connection.prepareStatement(pollsOptions + options.get(i) + ")");
				statement.setInt(1, id);
				statement.execute();
				statement.close();
			}
		}

	}

	/**
	 * Method is called when program wants to be closed<br>
	 * Before closing,method deletes current {@link ComboPooledDataSource}
	 * 
	 * @param sce
	 *            - servlet context event
	 */
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