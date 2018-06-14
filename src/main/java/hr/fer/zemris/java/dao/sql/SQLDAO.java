package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.dao.DAO;
import hr.fer.zemris.java.dao.DAOException;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;
import hr.fer.zemris.java.strcutures.PollsStructure;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 */
public class SQLDAO implements DAO {

	/**
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.dao.DAO#getPolls()
	 */
	@Override
	public List<PollsStructure> getPolls() {
		Connection connection = SQLConnectionProvider.getConnection();

		List<PollsStructure> forReturn = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Polls");
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				forReturn.add(new PollsStructure(set.getInt("id"), set.getString("title"), set.getString("message")));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return forReturn;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.dao.DAO#incrementVote(int)
	 */
	@Override
	public void incrementVote(int id, Integer pollID) {
		Connection connection = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE PollOptions SET votesCount=votesCount+1 WHERE id=" + id + "AND pollID=" + pollID);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.dao.DAO#loadItems(int)
	 */
	@Override
	public List<PollOptionsStructure> loadItems(int module) {
		List<PollOptionsStructure> forReturn = new ArrayList<>();

		Connection connection = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT * FROM PollOptions WHERE pollID=" + module);

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				forReturn.add(new PollOptionsStructure(set.getInt("id"), set.getString("optionTitle"),
						set.getString("optionLink"), set.getInt("pollID"), set.getInt("votesCount")));
			}
		} catch (NumberFormatException | SQLException e) {
			throw new DAOException(e);
		}

		return forReturn;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.dao.DAO#getPollID(java.lang.Integer)
	 */
	@Override
	public int getPollID(Integer idVote) {
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT pollID FROM PollOptions WHERE id=" + idVote);
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				return set.getInt("pollID");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				new DAOException(e);
			}
		}

		throw new DAOException("Item with id=" + idVote + " doesn't exist!");
	}

	@Override
	public PollsStructure getPollByID(int pollID) {
		Connection connection = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Polls WHERE id=" + pollID);
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				return new PollsStructure(set.getInt("id"), set.getString("title"), set.getString("message"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return null;
	}

}