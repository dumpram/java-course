package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
		Poll poll = (Poll) req.getSession().getAttribute("poll");
		long pollID = poll.getId();
		List<PollOption> list = DAOProvider.getDao().dohvatiUnose(pollID);
		Collections.sort(list);
		req.getSession().setAttribute("results", list);
		req.getSession().setAttribute("winners", getWinners(list));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
	/**
	 * Getter for winner list.
	 * 
	 * @param list
	 *            given band list
	 * @return winners from list
	 */
	private List<PollOption> getWinners(List<PollOption> list) {
		if (list.isEmpty()) {
			return null;
		}
		List<PollOption> winners = new ArrayList<>();
		long max = list.get(0).getVotesCount();
		for (PollOption i : list) {
			if (i.getVotesCount() == max) {
				winners.add(i);
			} else {
				break;
			}
		}
		return winners;
	}

}
