package hr.fer.zemris.java.hw13.servlets;

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
		String rezFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Glasanje.napraviDatoteku(defFileName, rezFileName);
		List<BandInfo> bandList = Glasanje.ucitajIzDatoteke(false, defFileName, rezFileName);
		req.getSession().setAttribute("bands", bandList);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
