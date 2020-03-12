package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.forms.RegisterForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/servleti/save")
public class RegisterSave extends HttpServlet {
	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		req.setCharacterEncoding("UTF-8");
		String metoda = req.getParameter("metoda");

		if (!"Register".equals(metoda)) {
			resp.sendRedirect(req.getServletContext().getContextPath());
			return;
		}

		RegisterForm form = new RegisterForm();
		form.fromHttpRequest(req);

		if (form.hasErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
			return;
		}

		BlogUser user = new BlogUser();
		form.fillUser(user);
		DAOProvider.getDAO().saveBlogUser(user);
		req.getRequestDispatcher("/WEB-INF/pages/registerSuccess.jsp").forward(req, resp);
	}
}
