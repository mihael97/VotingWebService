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
	/**
	 * Method returns all supported polls
	 * 
	 * @return supported polls
	 */
	public List<PollsStructure> getPolls();

	/**
	 * Method returns poll with specific id
	 * 
	 * @param pollID
	 *            - poll ID
	 * @return {@link PollsStructure}
	 */
	public PollsStructure getPollByID(int pollID);

	/**
	 * Method increments vote to data with given id
	 * 
	 * @param id
	 *            - data id
	 * @param pollID
	 */
	public void incrementVote(int id, Integer pollID);

	/**
	 * Method loads all poll options from table
	 * 
	 * @param module
	 *            - poll module
	 * @return - poll options for given id
	 */
	public List<PollOptionsStructure> loadItems(int module);

	/**
	 * Method returns id of poll where data is stored
	 * 
	 * @param idVote
	 *            - id
	 * @return pollID of given id
	 */
	public int getPollID(Integer idVote);
}