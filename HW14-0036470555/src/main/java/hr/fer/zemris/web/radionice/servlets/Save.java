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
 * Servlet je namijenjen spremanju konteksta.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/save")
public class Save extends HttpServlet {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}
	/**
	 * Metoda obrađuje zahtjev koji je predan servletu.
	 * 
	 * @param req
	 *            predani zahtjev
	 * @param resp
	 *            predani odgovor
	 * @throws ServletException
	 *             u slučaju greške
	 * @throws IOException
	 *             u slučaju greške pri čitanju iz datoteke
	 */
	private void obradi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		User korisnik = (User) req.getSession().getAttribute("current.user");
		if (korisnik == null) {
			req.setAttribute("poruka", "Dragi korisniče, nisi ovlašten za spremanje radionica!");
			req.getRequestDispatcher("/WEB-INF/pages/Greska.jsp").forward(req, resp);
			return;
		}
		String metoda = req.getParameter("metoda");
		if (!"Pohrani".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		}
		String fileName = req.getServletContext().getRealPath("/WEB-INF/baza");
		RadioniceBaza baza = RadioniceBaza.ucitaj(fileName);
		RadionicaForm f = new RadionicaForm();
		f.popuniIzHttpRequesta(req);
		f.validiraj();

		req.setAttribute("oprema", baza.getOprema());

		req.setAttribute("trajanje", baza.getTrajanje());

		req.setAttribute("publika", baza.getPublika());

		if (f.imaPogresaka()) {
			req.setAttribute("radionica", f);
			req.getRequestDispatcher("WEB-INF/pages/Formular.jsp").forward(req, resp);
			return;
		}

		Radionica r = new Radionica();
		f.popuniURadionicu(r, baza);

		baza.snimi(r);
		baza.snimi();
		resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		obradi(req, resp);
	}

}
