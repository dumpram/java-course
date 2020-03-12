package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 * 
 */
public interface DAO {

	/**
	 * Dohvaća sve postojeće unose u bazi, ali puni samo dva podatka: id i
	 * title.
	 * 
	 * @return listu unosa
	 * @throws DAOException
	 *             u slučaju pogreške
	 */
	public List<PollOption> dohvatiOsnovniPopisUnosa() throws DAOException;

	/**
	 * Dohvaća Unos za zadani id. Ako unos ne postoji, vraća <code>null</code>.
	 * 
	 * @param id
	 *            identifikator
	 * @return unos dani unos
	 * @throws DAOException
	 *             ako ode u krivo
	 */
	public PollOption dohvatiUnos(long id) throws DAOException;
	/**
	 * Dohvaća opcije iz baze podataka za dani id datoteke.
	 * 
	 * @param pollID
	 *            id polla
	 * @return lista opcija
	 */
	public List<PollOption> dohvatiUnose(long pollID);
	/**
	 * Dohvaća ankete iz baze podataka.
	 * 
	 * @param title
	 *            polla
	 * @return anketa.
	 */
	public Poll dohvatiAnketu(String title);
	/**
	 * Dohvaća anketu s utim idijem.
	 * 
	 * @param pollID
	 *            utim id
	 * @return anketa
	 */
	public Poll dohvatiAnketu(Long pollID);
	/**
	 * Povećava votest count s danim idiem.
	 * 
	 * @param ident
	 *            danim idiem
	 */
	public void povecajVotesCount(long ident);
	/**
	 * Dohvaća ankete iz baze podataka.
	 * 
	 * @return lista anketa.
	 */
	public List<Poll> dohvatiAnkete();

}