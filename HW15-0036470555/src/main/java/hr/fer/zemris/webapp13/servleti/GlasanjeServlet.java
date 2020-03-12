package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.Poll;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Initiates voting. Dispatches its request to appropriate Java Server Page.
 * Additionally it can create and initialize file of results.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet(name = "Glasanje", urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String param = req.getParameter("pollID");
		Long pollID = null;
		try {
			pollID = Long.parseLong(param);
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("error", "Pogrešan argument!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		Poll poll = DAOProvider.getDao().dohvatiAnketu(pollID);
		if (poll == null) {
			req.getSession().setAttribute("error", "Pogrešan argument!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		List<PollOption> optionList = DAOProvider.getDao().dohvatiUnose(pollID);
		req.getSession().setAttribute("options", optionList);
		req.getSession().setAttribute("poll", poll);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
