package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Creates results of voting. Dispatches its request to appropriate Java Server
 * Page. Additionally it can create and initialize file of results.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet(name = "Rezultati glasanja", urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
		String rezFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		// Napravi datoteku ako je potrebno; inače je samo pročitaj...
		Glasanje.napraviDatoteku(defFileName, rezFileName);
		// Pošalji ih JSP-u...
		List<BandInfo> bandList = Glasanje.ucitajIzDatoteke(true, defFileName, rezFileName);
		req.getSession().setAttribute("results", bandList);
		req.getSession().setAttribute("winners", getWinners(bandList));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	/**
	 * Getter for winner list.
	 * 
	 * @param bandList
	 *            given band list
	 * @return winners from list
	 */
	private List<BandInfo> getWinners(List<BandInfo> bandList) {
		if (bandList.isEmpty()) {
			return null;
		}
		List<BandInfo> winners = new ArrayList<>();
		int max = bandList.get(0).getVoteCount();
		for (BandInfo i : bandList) {
			if (i.getVoteCount() == max) {
				winners.add(i);
			} else {
				break;
			}
		}
		return winners;
	}

}
