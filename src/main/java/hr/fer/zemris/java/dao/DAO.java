package hr.fer.zemris.java.dao;

import java.util.List;
import hr.fer.zemris.java.strcutures.PollOptionsStructure;
import hr.fer.zemris.java.strcutures.PollsStructure;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
	public List<PollsStructure> getPolls();

	public void incrementVote(int module, int id);

	public List<PollOptionsStructure> loadItems(int module);
}