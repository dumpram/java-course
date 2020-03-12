package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
		String rezFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String defFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Glasanje.napraviDatoteku(defFileName, rezFileName);
		List<BandInfo> listBand = Glasanje.ucitajIzDatoteke(true, defFileName, rezFileName);
		String id = req.getParameter("id");
		if (invalidArgument(id, listBand)) {
			req.getSession().setAttribute("error", "Nećeš razbojniče! Bend s danim ID-om ne postoji!");
			req.getRequestDispatcher("WEB-INF/pages/invalidArgument.jsp").forward(req, resp);
			return;
		}

		int ident = Integer.parseInt(id);

		List<String> lines = Files.readAllLines(Paths.get(rezFileName), StandardCharsets.UTF_8);
		String line = lines.get(ident - 1);
		String[] elements = line.split("\t");
		int count = Integer.parseInt(elements[1]);
		count++;
		lines.set(ident - 1, ident + "\t" + count);
		Files.write(Paths.get(rezFileName), lines, StandardCharsets.UTF_8);
		// Kad je gotovo, pošalji redirect pregledniku
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	/**
	 * Checks if given argument is invalid. Argument is considered invalid if
	 * none of the bands has identification as given argument n.
	 * 
	 * @param n
	 *            given argument
	 * @param bandList
	 *            given band list
	 */
	private boolean invalidArgument(String n, List<BandInfo> bandList) {
		int param;
		try {
			param = Integer.parseInt(n);
		} catch (NumberFormatException e) {
			return true;
		}
		for (BandInfo i : bandList) {
			if (i.getId() == param) {
				return false;
			}
		}
		return true;
	}
}
