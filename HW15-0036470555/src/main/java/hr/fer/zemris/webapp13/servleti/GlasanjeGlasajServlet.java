package hr.fer.zemris.webapp13.servleti;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.PollOption;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet is used for voting in application. Stores new information in results
 * file. Furthermore, if result file doesn't exist it creates it.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet(name = "Glasaj", urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		long ident;
		try {
			ident = Long.parseLong(id);
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("error", "Pogrešan argument!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		PollOption poll = DAOProvider.getDao().dohvatiUnos(ident);
		if (poll == null) {
			req.getSession().setAttribute("error", "Pogrešan argument!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}
		DAOProvider.getDao().povecajVotesCount(ident);

		// Kad je gotovo, pošalji redirect pregledniku
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
}
