package hr.fer.zemris.java.tecaj_14.web.forms;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Obrazac za unos komentara.
 * 
 * @author Ivan Pavić
 * 
 */
public class CommentForm {
	/**
	 * Poruka u komentaru.
	 */
	private String message;
	/**
	 * Entry di se komentar unosi.
	 */
	private BlogEntry entry;
	/**
	 * Autor komentara.
	 */
	private BlogUser author;
	/**
	 * Mapa grešaka.
	 */
	private final Map<String, String> errors = new HashMap<>();
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
	 * @return vraća <code>true</code> ako ima grešaka, <code>false</code>
	 *         inače.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	/**
	 * Popunjava obrazac iz predanog http requesta.
	 * 
	 * @param req
	 *            predani http request
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		message = prepare(req.getParameter("message"));
		author = (BlogUser) req.getSession().getAttribute("current.user");
		entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(req.getParameter("id")));
		validate();
	}
	/**
	 * Puni predani {@link BlogComment} iz obrasca.
	 * 
	 * @param comment
	 *            preadni {@link BlogComment}
	 */
	public void fillComment(BlogComment comment) {
		comment.setMessage(message);
		comment.setAuthor(author);
		comment.setPostedOn(new Date());
		comment.setBlogEntry(entry);
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
	public void validate() {
		if (message.isEmpty()) {
			errors.put("message", "Can't post empty comment!");
		}
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the entry
	 */
	public BlogEntry getEntry() {
		return entry;
	}

	/**
	 * @param entry
	 *            the entry to set
	 */
	public void setEntry(BlogEntry entry) {
		this.entry = entry;
	}

	/**
	 * @return the author
	 */
	public BlogUser getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
	}

}
