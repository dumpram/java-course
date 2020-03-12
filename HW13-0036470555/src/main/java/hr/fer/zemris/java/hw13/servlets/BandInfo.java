package hr.fer.zemris.java.hw13.servlets;

import java.io.Serializable;

/**
 * BandInfo class represents info for one band. Contains generic information
 * about rock bands and their songs. Class comes with optimized getters and
 * setters.
 * 
 * @author Ivan Pavić
 * 
 */
public class BandInfo implements Comparable<BandInfo>, Serializable {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Private id number.
	 */
	private int id;
	/**
	 * Private band name.
	 */
	private String bandName;
	/**
	 * Private vote count.
	 */
	private int voteCount;
	/**
	 * Link to song.
	 */
	private String song;
	/**
	 * BandInfo constructor takes 3 arguments.
	 * 
	 * @param id
	 *            identification number for band
	 * @param bandName
	 *            name of the band
	 * @param voteCount
	 *            vote count
	 * @param song
	 *            link to song
	 */
	public BandInfo(int id, String bandName, int voteCount, String song) {
		super();
		this.id = id;
		this.bandName = bandName;
		this.voteCount = voteCount;
		this.song = song;
	}
	@Override
	public int compareTo(BandInfo other) {
		if (voteCount < other.voteCount) {
			return 1;
		}
		if (voteCount > other.voteCount) {
			return -1;
		}
		return bandName.compareTo(other.bandName);
	}

	/**
	 * Getter for id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for bandName.
	 * 
	 * @return the bandName
	 */
	public String getBandName() {
		return bandName;
	}

	/**
	 * Setter for bandName.
	 * 
	 * @param bandName
	 *            the bandName to set
	 */
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	/**
	 * Getter for vote count.
	 * 
	 * @return the voteCount
	 */
	public int getVoteCount() {
		return voteCount;
	}

	/**
	 * Setter for vote count.
	 * 
	 * @param voteCount
	 *            the voteCount to set
	 */
	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	/**
	 * Getter for song.
	 * 
	 * @return the song
	 */
	public String getSong() {
		return song;
	}

	/**
	 * Setter for song.
	 * 
	 * @param song
	 *            the song to set
	 */
	public void setSong(String song) {
		this.song = song;
	}

}
