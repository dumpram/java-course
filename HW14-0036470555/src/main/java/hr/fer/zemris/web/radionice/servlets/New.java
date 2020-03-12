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
 * Servlet namijenjen stvaranju novih radionica.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/new")
public class New extends HttpServlet {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User korisnik = (User) req.getSession().getAttribute("current.user");
		if (korisnik == null) {
			req.setAttribute("poruka", "Dragi korisniče, nisi ovlašten za stvaranje novih radionica!");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		Radionica r = new Radionica();
		RadionicaForm f = new RadionicaForm();
		f.popuniIzRadionice(r);

		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext().getRealPath("/WEB-INF/baza"));

		req.setAttribute("radionica", f);

		req.setAttribute("oprema", baza.getOprema());

		req.setAttribute("trajanje", baza.getTrajanje());

		req.setAttribute("publika", baza.getPublika());

		req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
	}

}
