package hr.fer.zemris.web.radionice.servlets;

import hr.fer.zemris.web.radionice.Radionica;
import hr.fer.zemris.web.radionice.RadioniceBaza;

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
 * Servlet služi za dohvat podataka za izlistavanje iz baze. Podaci su sortirani
 * po datumu pa unutar toga po imenu.
 * 
 * @author Ivan Pavić
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/listaj")
public class Listaj extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RadioniceBaza baza = RadioniceBaza.ucitaj(req.getServletContext().getRealPath("/WEB-INF/baza"));
		List<Radionica> radionice = baza.getRadionice();
		List<IspisRadionica> ispis = new ArrayList<>();
		for (Radionica r : radionice) {
			ispis.add(new IspisRadionica(r));
		}
		Collections.sort(ispis);
		req.setAttribute("radionice", ispis);
		req.getRequestDispatcher("/WEB-INF/pages/Listaj.jsp").forward(req, resp);
	}

}
