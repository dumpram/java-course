package hr.fer.zemris.java.tecaj_14.dao;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPADAOImpl;
/**
 * {@link DAOProvider} vraća neku od implementacija {@link DAO} sučelja.
 * 
 * @author Ivan Pavić
 * 
 */
public class DAOProvider {
	/**
	 * Statička varijabla. Singleton.
	 */
	private static DAO dao = new JPADAOImpl();
	/**
	 * Metoda vraća implementaciju {@link DAO} sučelja-
	 * 
	 * @return implementacija {@link DAO} sučelja.
	 */
	public static DAO getDAO() {
		return dao;
	}

}