package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.model.Crypto;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/servleti/login")
public class Login extends HttpServlet {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String metoda = req.getParameter("metoda");
		if (metoda.equals("Cancel")) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}
		String username = req.getParameter("nick");
		String password = req.getParameter("password");
		if (username == null || username.isEmpty()) {
			req.setAttribute("error", "Nick is required!");
			req.setAttribute("nick", "");
			req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
			return;
		}
		BlogUser user = check(username, password);
		if (user != null) {
			req.getSession().setAttribute("current.user", user);
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		} else {
			req.setAttribute("error", "Wrong username or password!");
			req.setAttribute("nick", username);
			req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
		}
	}

	private BlogUser check(String username, String password) {
		BlogUser user = DAOProvider.getDAO().getBlogUser(username);
		if (user.getPasswordHash().equals(Crypto.digest(password))) {
			return user;
		}
		return null;
	}

}
