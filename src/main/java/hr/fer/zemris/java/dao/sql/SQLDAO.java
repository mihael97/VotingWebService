package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.dao.DAO;
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

	@Override
	public List<PollsStructure> getPolls() {
		Connection connection = SQLConnectionProvider.getConnection();

		List<PollsStructure> forReturn = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Polls;");
			ResultSet set = statement.executeQuery();

			while (set.next()) {
				forReturn.add(new PollsStructure(set.getInt("id"), set.getString("title"), set.getString("message")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return forReturn;
	}

	@Override
	public void incrementVote(int module, int id) {
		Connection connection = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"UPDATE PollOptions SET votesCount=votesCount+1 WHERE pollID=" + module + "AND id=" + id + ";)");
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PollOptionsStructure> loadItems(int module) {
		List<PollOptionsStructure> forReturn = new ArrayList<>();

		Connection connection = SQLConnectionProvider.getConnection();
		try {
			PreparedStatement statement = connection
					.prepareStatement("SELECT id,optionTitle,optionLink FROM PollOption WHERE pollID=" + module + ";");

			ResultSet set = statement.executeQuery();

			while (set.next()) {
				forReturn.add(new PollOptionsStructure(set.getInt("id"), set.getString("optionTitle"),
						set.getString("optionTitle"), set.getInt("pollID"), set.getInt("votesCount")));
			}
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}

		return forReturn;
	}

}