package hr.fer.zemris.java.tecaj_13.model;
/**
 * Class represents poll for voting application.
 * 
 * @author Ivan Pavić
 * 
 */
public class Poll {
	/**
	 * Poll id.
	 */
	private long id;
	/**
	 * Poll title.
	 */
	private String pollTitle;
	/**
	 * Poll message.
	 */
	private String pollMessage;
	/**
	 * Default constructor.
	 */
	public Poll() {
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the pollTitle
	 */
	public String getPollTitle() {
		return pollTitle;
	}
	/**
	 * @param pollTitle
	 *            the pollTitle to set
	 */
	public void setPollTitle(String pollTitle) {
		this.pollTitle = pollTitle;
	}
	/**
	 * @return the pollMessage
	 */
	public String getPollMessage() {
		return pollMessage;
	}
	/**
	 * @param pollMessage
	 *            the pollMessage to set
	 */
	public void setPollMessage(String pollMessage) {
		this.pollMessage = pollMessage;
	}

}
