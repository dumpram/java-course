package hr.fer.zemris.web.radionice;
/**
 * Iznimka se baca u slučaju nekonzistentnosti opcija u bazi.
 * 
 * @author Ivan Pavić
 * 
 */
public class InconsistentDatabaseException extends RuntimeException {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Konstruktor prima poruku.
	 * 
	 * @param message
	 *            poruka iznimke
	 */
	public InconsistentDatabaseException(String message) {
		super(message);
	}

}
