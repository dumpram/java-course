package hr.infinum.fer.ip47055;
/**
 * Class represents contact.
 * 
 * @author Ivan Pavić
 * 
 */
public class Contact {
	/**
	 * Name of contact.
	 */
	private String name;
	/**
	 * Number.
	 */
	private String number;
	/**
	 * Email of contact.
	 */
	private String email;
	/**
	 * Facebook account.
	 */
	private String facebook;
	/**
	 * Crtica o osobi.
	 */
	private String note;
	/**
	 * Konstruktor koji prima sljedece parametre:
	 * 
	 * @param name
	 *            ime
	 * @param number
	 *            broj telefona
	 * @param email
	 *            email adresu
	 * @param note
	 *            crtica o osobi
	 * @param facebook
	 *            link facebook profila kontakta
	 */
	public Contact(String name, String number, String email, String note, String facebook) {
		super();
		this.name = name;
		this.number = number;
		this.email = email;
		this.note = note;
		this.facebook = facebook;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the facebook
	 */
	public String getFacebook() {
		return facebook;
	}
	/**
	 * @param facebook
	 *            the facebook to set
	 */
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
