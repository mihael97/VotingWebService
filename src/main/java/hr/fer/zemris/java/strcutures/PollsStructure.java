package hr.fer.zemris.java.strcutures;

/**
 * Class represents poll structure
 * 
 * @author Mihael
 *
 */
public class PollsStructure {
	/**
	 * Poll id
	 */
	private int id;
	/**
	 * Poll title
	 */
	private String title;
	/**
	 * Poll message
	 */
	private String message;

	/**
	 * Constructor creates new poll
	 * 
	 * @param id
	 *            - poll id
	 * @param title
	 *            - poll title
	 * @param message
	 *            - poll message
	 */
	public PollsStructure(int id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	/**
	 * Method returns poll id
	 * 
	 * @return poll id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method sets poll id
	 * 
	 * @param id
	 *            - new poll id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Method returns poll title
	 * 
	 * @return poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Method sets poll title
	 * 
	 * @param title
	 *            - new poll title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Method returns poll message
	 * 
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Method sets poll message
	 * 
	 * @param message
	 *            - poll message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
