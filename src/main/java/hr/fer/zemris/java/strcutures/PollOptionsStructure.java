package hr.fer.zemris.java.strcutures;

public class PollOptionsStructure {
	private Integer id;
	private String optionTitle;
	private String optionLink;
	private Integer pollID;
	private Integer votes;

	public PollOptionsStructure(Integer id, String optionTitle, String optionLink, Integer pollID, Integer votes) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollID = pollID;
		this.votes = votes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptionTitle() {
		return optionTitle;
	}

	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	public String getOptionLink() {
		return optionLink;
	}

	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	public Integer getPollID() {
		return pollID;
	}

	public void setPollID(Integer pollID) {
		this.pollID = pollID;
	}

	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

}
