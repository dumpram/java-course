package hr.fer.zemris.java.tecaj_13.model;
/**
 * Represents option in certain pool.
 * 
 * @author Ivan Pavić
 * 
 */
public class PollOption implements Comparable<PollOption> {
	/**
	 * ID for option.
	 */
	private Long id;
	/**
	 * Title of option.
	 */
	private String optionTitle;
	/**
	 * Link of option.
	 */
	private String optionLink;
	/**
	 * Poll ID.
	 */
	private long pollID;
	/**
	 * Number of votes.
	 */
	private long votesCount;
	/**
	 * Default constructor.
	 */
	public PollOption() {
	}
	/**
	 * @return the optionTitle
	 */
	public String getOptionTitle() {
		return optionTitle;
	}
	/**
	 * @param optionTitle
	 *            the optionTitle to set
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	/**
	 * @return the optionLink
	 */
	public String getOptionLink() {
		return optionLink;
	}
	/**
	 * @param optionLink
	 *            the optionLink to set
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	/**
	 * @return the pollID
	 */
	public long getPollID() {
		return pollID;
	}
	/**
	 * @param pollID
	 *            the pollID to set
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}
	/**
	 * @return the votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}
	/**
	 * @param votesCount
	 *            the votesCount to set
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
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
	@Override
	public int compareTo(PollOption o) {
		if (Long.compare(votesCount, o.getVotesCount()) == 0) {
			return optionTitle.compareTo(o.getOptionTitle());
		}
		return Long.compare(o.getVotesCount(), votesCount);
	}
}
