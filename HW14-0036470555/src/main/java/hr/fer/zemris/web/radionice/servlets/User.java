package hr.fer.zemris.web.radionice.servlets;

import java.io.Serializable;

/**
 * Razred predstavlja korisnika web aplikacije.
 * 
 * @author Ivan Pavić
 * 
 */
public class User implements Serializable {
	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * User name.
	 */
	private String login;
	/**
	 * Ime korisnika.
	 */
	private String ime;
	/**
	 * Prezime korisnika.
	 */
	private String prezime;
	/**
	 * Zaporka korisinika.
	 */
	private String zaporka;

	/**
	 * Konstruktor prima 4 parametra.
	 * 
	 * @param login
	 *            login name
	 * @param ime
	 *            ime
	 * @param prezime
	 *            prezime
	 * @param zaporka
	 *            lozinka
	 */
	public User(String login, String ime, String prezime, String zaporka) {
		this.login = login;
		this.ime = ime;
		this.prezime = prezime;
		this.zaporka = zaporka;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * @param ime
	 *            the ime to set
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * @return the prezime
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * @param prezime
	 *            the prezime to set
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	/**
	 * @return the zaporka
	 */
	public String getZaporka() {
		return zaporka;
	}

	/**
	 * @param zaporka
	 *            the zaporka to set
	 */
	public void setZaporka(String zaporka) {
		this.zaporka = zaporka;
	}

	@Override
	public String toString() {
		return String.format("%s %s | %s", ime, prezime, login);
	}

}
