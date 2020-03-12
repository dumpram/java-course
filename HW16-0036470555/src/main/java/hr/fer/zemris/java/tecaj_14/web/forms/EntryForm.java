package hr.fer.zemris.java.tecaj_14.web.forms;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * Obrazac za neki članak.
 * 
 * @author Ivan Pavić
 * 
 */
public class EntryForm {
	/**
	 * Id.
	 */
	private String id;
	/**
	 * Naslov unosa.
	 */
	private String title;
	/**
	 * Tekst članka.
	 */
	private String text;
	/**
	 * Mapa grešaka.
	 */
	private final Map<String, String> errors = new HashMap<>();
	/**
	 * Autor.
	 */
	private BlogUser author;
	/**
	 * Zastavica <code>true</code> ako je novi članak, <code>false</code> inače.
	 */
	private boolean isNew;

	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	/**
	 * Puni obrazac iz http requesta.
	 * 
	 * @param req
	 *            request
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		id = req.getParameter("id");
		title = prepare(req.getParameter("title"));
		text = prepare(req.getParameter("text"));
		author = (BlogUser) req.getSession().getAttribute("current.user");
		validate();
	}

	/**
	 * @return the author
	 */
	public BlogUser getAuthor() {
		return author;
	}
	/**
	 * Puni obrazac iz unosa.
	 * 
	 * @param entry
	 *            unos
	 */
	public void fromBlogEntry(BlogEntry entry) {
		title = entry.getTitle();
		text = entry.getText();
		author = entry.getAuthor();
		id = String.valueOf(entry.getId());
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(BlogUser author) {
		this.author = author;
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
		if (id == null) {
			isNew = true;
		}
		if (title.isEmpty()) {
			errors.put("title", "Title is required!");
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
	 * @param error
	 *            ime parametra za koji hoćemo dohvatiti grešku.
	 * @return vraća {@link String} grešu ako postoji.
	 */
	public String getError(String error) {
		return errors.get(error);
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * Puni predani entry iz obrasca.
	 * 
	 * @param entry
	 *            predani {@link BlogEntry}
	 */
	public void fillEntry(BlogEntry entry) {
		if (isNew) {
			fillNewEntry(entry);
			return;
		}
		fillOldEntry(entry);
	}
	/**
	 * Puni stari {@link BlogEntry}.
	 * 
	 * @param entry
	 *            predani entry.
	 */
	private void fillOldEntry(BlogEntry entry) {
		BlogEntry old = DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));
		entry.setId(Long.parseLong(id));
		entry.setText(text);
		entry.setTitle(title);
		entry.setAuthor(old.getAuthor());
		entry.setComments(old.getComments());
		entry.setCreatedAt(old.getCreatedAt());
		entry.setLastModifiedAt(new Date());
	}

	/**
	 * Puni novi entry.
	 * 
	 * @param predani
	 *            {@link BlogEntry}
	 */
	private void fillNewEntry(BlogEntry entry) {
		entry.setTitle(title);
		entry.setText(text);
		entry.setAuthor(author);
		entry.setCreatedAt(new Date());
		entry.setLastModifiedAt(entry.getCreatedAt());
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
