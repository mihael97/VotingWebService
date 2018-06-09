package hr.fer.zemris.java.strcutures;

public class PollsStructure {
	private int id;
	private String title;
	private String message;

	public PollsStructure(int id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
