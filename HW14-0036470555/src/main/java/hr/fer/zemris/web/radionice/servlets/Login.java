package hr.fer.zemris.web.radionice.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet služi za logiranje korisnika u web-aplikaciju.
 * 
 * @author Ivan Pavić
 * 
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	/**
	 * Serijski broj.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metoda = req.getParameter("metoda");
		if (metoda.equals("Odustani")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		}
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if (username == null) {
			req.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(req, resp);
			return;
		}
		User korisnik = provjeri(username, password);
		if (korisnik != null) {
			req.getSession().setAttribute("current.user", korisnik);
			resp.sendRedirect(req.getServletContext().getContextPath() + "/listaj");
			return;
		} else {
			req.setAttribute("error", "Pogrešno korisničko ime ili lozinka!");
			req.getRequestDispatcher("WEB-INF/pages/Login.jsp").forward(req, resp);
		}
	}

	/**
	 * Metoda provjerava korisinika.
	 * 
	 * @param username
	 *            korisničko ime
	 * @param password
	 *            lozinka
	 * @return {@link User} ako postoji, inače null
	 */
	public User provjeri(String username, String password) {
		if (username.equals("aante") && password.equals("tajna")) {
			return new User(username, "Ante", "Anić", password);
		}
		return null;
	}
}
