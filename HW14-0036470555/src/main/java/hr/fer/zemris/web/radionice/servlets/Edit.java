package hr.fer.zemris.web.radionice.servlets;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet namijenjen za uređivanje unosa.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/edit")
public class Edit extends HttpServlet {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		User korisnik = (User) req.getSession().getAttribute("current.user");
		if (korisnik == null) {
			req.setAttribute("poruka", "Dragi korisniče, nisi ovlašten za uređivanje radionica!");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (Exception e) {
			req.setAttribute("poruka", "Pogresan id!");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}

		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext().getRealPath("/WEB-INF/baza"));

		Radionica radionica = baza.dohvatiRadionicu(id);

		if (radionica == null) {
			req.setAttribute("poruka", "Traženi zapis ne postoji");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}

		RadionicaForm f = new RadionicaForm();

		f.popuniIzRadionice(radionica);

		req.setAttribute("radionica", f);

		req.setAttribute("oprema", baza.getOprema());

		req.setAttribute("trajanje", baza.getTrajanje());

		req.setAttribute("publika", baza.getPublika());

		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}
}
