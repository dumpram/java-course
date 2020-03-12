package hr.fer.zemris.java.tecaj_14.dao;
/**
 * Iznimka koja se baca u slučaju pogreške u radu s bazom podataka.
 * 
 * @author Ivan Pavić
 * 
 */
public class DAOException extends RuntimeException {
	/**
	 * Standardni serijska verzija.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Konstruktor prihvaća 2 argumenta. Poruku i uzrok.
	 * 
	 * @param message
	 *            poruka
	 * @param cause
	 *            uzrok
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * Konstruktor prihvaća 1 argument poruku.
	 * 
	 * @param message
	 *            poruka.
	 */
	public DAOException(String message) {
		super(message);
	}
}