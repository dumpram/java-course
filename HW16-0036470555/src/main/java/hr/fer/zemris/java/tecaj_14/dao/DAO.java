package hr.fer.zemris.java.tecaj_14.dao;

import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.util.List;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	/**
	 * Dohvaća usera s zadanim nickom.
	 * 
	 * @param nick
	 *            zadani nick
	 * @return {@link BlogUser}
	 * @throws DAOException
	 *             u slučaju greške
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	/**
	 * Vraća <code>true</code> ako postoji user sa zadanim nickom
	 * 
	 * @param nick
	 *            zadani nick
	 * @return <code>true</code> ako postoji user sa zadanim nickom,
	 *         <code>false</code> inače
	 * @throws DAOException
	 *             u slučaju greške
	 */
	public boolean existsUserWithNick(String nick) throws DAOException;
	/**
	 * Sprema usera u bazu podataka.
	 * 
	 * @param blogUser
	 *            predani user
	 * @throws DAOException
	 *             u slučaju greške
	 */
	public void saveBlogUser(BlogUser blogUser) throws DAOException;
	/**
	 * Dohvaća listu registriranih usera.
	 * 
	 * @return lista {@link BlogUser}
	 */
	public List<BlogUser> getUsers();
	/**
	 * Sprema entry u bazu podataka.
	 * 
	 * @param entry
	 *            {@link BlogEntry} koji treba spremiti.
	 */
	public void saveBlogEntry(BlogEntry entry);
	/**
	 * Osvježava blog entry nakon promjena.
	 * 
	 * @param entry
	 *            predani entry
	 */
	public void updateBlogEntry(BlogEntry entry);
	/**
	 * Učitva komentar u bazu podataka.
	 * 
	 * @param comment
	 *            predani komentar
	 */
	public void updateBlogComment(BlogComment comment);

}