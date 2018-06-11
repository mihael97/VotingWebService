package hr.fer.zemris.java.strcutures;

/**
 * Class represents structure for poll items
 * 
 * @author Mihael
 *
 */
public class PollOptionsStructure {
	/**
	 * Poll option id
	 */
	private Integer id;
	/**
	 * Poll option title
	 */
	private String optionTitle;
	/**
	 * reference to more informations about poll option
	 */
	private String optionLink;
	/**
	 * ID of poll {@link PollOptionsStructure} part is
	 */
	private Integer pollID;
	/**
	 * Number of votes
	 */
	private Integer votes;

	/**
	 * Constructor creates new poll options
	 * 
	 * @param id
	 *            - option id
	 * @param optionTitle
	 *            - option title
	 * @param optionLink
	 *            - option link
	 * @param pollID
	 *            - poll id
	 * @param votes
	 *            - number of votes
	 */
	public PollOptionsStructure(Integer id, String optionTitle, String optionLink, Integer pollID, Integer votes) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votes = votes;
	}

	/**
	 * Method returns option's id
	 * 
	 * @return option's id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Method sets option's id
	 * 
	 * @param id
	 *            - new option id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Method returns option's title
	 * 
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Method sets option's title
	 * 
	 * @param optionTitle
	 *            - title of option
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Method returns link to additional info about poll option
	 * 
	 * @return
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Method sets link to additional info about option
	 * 
	 * @param optionLink
	 *            - link
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Method returns id of current poll owner
	 * 
	 * @return id of current poll owner
	 */
	public Integer getPollID() {
		return pollID;
	}

	/**
	 * Method sets pollID of poll option
	 * 
	 * @param pollID
	 *            - ID of new poll owner
	 */
	public void setPollID(Integer pollID) {
		this.pollID = pollID;
	}

	/**
	 * Method returns options's votes
	 * 
	 * @return option's votes
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * Method sets poll option votes
	 * 
	 * @param votes
	 *            - new votes amount
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}
}
