package hr.fer.zemris.java.tecaj_14.web.forms;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.model.Crypto;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Obrazac za registraciju prilikom registriranja.
 * 
 * @author Ivan Pavić
 * 
 */
public class RegisterForm {
	/**
	 * Ime.
	 */
	private String firstName;
	/**
	 * Prezime.
	 */
	private String lastName;
	/**
	 * Email.
	 */
	private String email;
	/**
	 * Nickname.
	 */
	private String nick;
	/**
	 * Password hash.
	 */
	private String password;
	/**
	 * Mapa greški.
	 */
	private Map<String, String> errors = new HashMap<>();
	/**
	 * Konstruktor.
	 */
	public RegisterForm() {
	}
	/**
	 * Puni obrazac iz http requesta.
	 * 
	 * @param req
	 *            request
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		firstName = prepare(req.getParameter("firstName"));
		lastName = prepare(req.getParameter("lastName"));
		email = prepare(req.getParameter("email"));
		nick = prepare(req.getParameter("nick"));
		password = prepare(req.getParameter("password"));
		validate();
	}
	/**
	 * Preparira parametar. Ako je {@link String} <code>null</code> postaje "".
	 * Dodatno trima string.
	 * 
	 * @param parameter
	 *            string koji promatramo
	 * @return vraća kako je opisano gore
	 */
	private String prepare(String parameter) {
		if (parameter == null) {
			return "";
		}
		return parameter.trim();
	}
	/**
	 * Metoda za validiranje obrasca. Ispituje da li su svi obrasci pravilno
	 * uneseni.
	 */
	private void validate() {
		if (firstName.isEmpty()) {
			errors.put("firstName", "First name is required!");
		}
		if (lastName.isEmpty()) {
			errors.put("lastName", "Last name is required!");
		}
		if (email.isEmpty()) {
			errors.put("email", "Email is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "Wrong email format!");
			}
		}
		if (nick.isEmpty()) {
			errors.put("nick", "Nick is required!");
		} else {
			if (DAOProvider.getDAO().existsUserWithNick(nick)) {
				errors.put("nick", "User with nick: " + nick + " already exists!");
			}
		}
		if (password.isEmpty()) {
			errors.put("password", "Password is required!");
		}
	}
	/**
	 * @return vraća <code>true</code> ako ima grešaka, <code>false</code>
	 *         inače.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	/**
	 * Popunjava korisnika iz obrasca.
	 * 
	 * @param user
	 *            {@link BlogUser} instanca za popuniti.
	 */
	public void fillUser(BlogUser user) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setPasswordHash(Crypto.digest(password));
		user.setEmail(email);
	}
	/**
	 * @param error
	 *            ime parametra kojeg ispitujemo
	 * @return <code>true</code> ako postoji greška na tom parametreu,
	 *         <code>false</code> inače.
	 */
	public boolean hasError(String error) {
		return errors.containsKey(error);
	}
	/**
	 * @param error
	 *            ime parametra za koji hoćemo dohvatiti grešku.
	 * @return vraća {@link String} grešu ako postoji.
	 */
	public String getError(String error) {
		return errors.get(error);
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}
