package hr.fer.zemris.java.tecaj_14.dao.jpa;

import javax.persistence.EntityManagerFactory;
/**
 * Provider za {@link EntityManagerFactory}.
 * 
 * @author Ivan Pavić
 * 
 */
public class JPAEMFProvider {
	/**
	 * Varijabla koja pohranjuje {@link EntityManagerFactory}.
	 */
	public static EntityManagerFactory emf;
	/**
	 * Statička metoda za dohvaćanje {@link EntityManagerFactory}.
	 * 
	 * @return instanca {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	/**
	 * Postavlja {@link EntityManagerFactory}.
	 * 
	 * @param emf
	 *            instanca {@link EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}